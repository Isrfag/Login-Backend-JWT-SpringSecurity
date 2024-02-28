package com.isrfag.SpringSecurity.SpringSecurityProject.services.impl;

import com.isrfag.SpringSecurity.SpringSecurityProject.services.IJWTUtilityService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTUtilityServiceImpl implements IJWTUtilityService {

    @Value("classpath:jwtKeys/private_key.pem") //Ruta con notacion
    private Resource privateKeyResource;

    @Value("classpath:jwtKeys/public_key.pem")
    private Resource publicKeyResource;

    // 2 metodos publicos de la interfaz
    @Override
    public String generateJWT (Long userId) throws IOException,
            InvalidKeySpecException,
            NoSuchAlgorithmException,
            JOSEException {

        PrivateKey privateKey = loadPrivateKey(privateKeyResource);

        JWSSigner signer = new RSASSASigner(privateKey);

        Date now = new Date();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject((userId).toString()) //En que se basa
                .issueTime(now) //Cuando inicia
                .expirationTime(new Date(now.getTime() + 14400000)) //Cuando expira
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256),claimsSet);
        signedJWT.sign(signer); //Firmamos la firma de la private key

        return signedJWT.serialize(); //Esto saca el String

    }
    @Override
    public JWTClaimsSet parseJWT (String jwt) throws IOException,
            InvalidKeySpecException,
            NoSuchAlgorithmException,
            ParseException,
            JOSEException {

        PublicKey publicKey = loadPublicKey(publicKeyResource);

        SignedJWT signedJWT = SignedJWT.parse(jwt);
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
        if(!signedJWT.verify(verifier)) {
            throw new JOSEException("Invalid Signature");
        }

        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        if(claimsSet.getExpirationTime().before(new Date())) {
            //Le decimos que el token no este caducado
            throw new JOSEException("Expired token");
        }

        return claimsSet;
    }

    //2 metodos privados para leer las llaves tanto publica como privada

    private PrivateKey loadPrivateKey (Resource resource) throws IOException,
            InvalidKeySpecException,
            NoSuchAlgorithmException { //Spring io Resource que hay muchos

        byte [] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----END PRIVATE KEY-----","")
                .replaceAll("\\s","");
                //Lo que hemos hecho es quitar los dos comentarios y la basura de la clave encriptada
                //sustituyendolos por ""osea nada.

        byte[] decodeKey = Base64.getDecoder().decode(privateKeyPEM); //Decodificamos en bytes el string filtrado
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodeKey));
    }

    private PublicKey loadPublicKey ( Resource resource) throws IOException,
            InvalidKeySpecException,
            NoSuchAlgorithmException {
        byte [] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String publicKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","")
                .replaceAll("\\s","");

        byte[] decodedKey = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
    }
}
