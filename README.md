# Sistema de Registro de Usuarios con SpringSecurity y JWT

He realizado un sistema que permite el registro y login de un usuario a través de un email y una contraseña en una ruta pública.Una vez logeado como el 
administrador se generera un jwt que permite el acceso a la ruta privada para recuperar todos los usuarios de la base de datos.

## Tecnología usada:
* Lenguaje Java con Springboot framework
* SpringSecurity y jwt
* Clave privada y clave pública
* Autentificación robusta de registro de usuario

## Características de la aplicación:
* Registro e identificación de usuario a través de los endpoints POST la ruta pública /user.
* Guardado en base de datos mysql de los nuevos usuarios con contraseña cifrada por Hash.
* Recuperación de los usuarios registardos desde la ruta privada /auth a través del endpoint GET, siempre y cuando
el usuario sea el administrador (tenga el JWT que recibe este al identificarse en la ruta pública).

### Sistema realizado por Israel Fernández Agudo.


## -- Englis version:

# User register system using SpringSecurity and JWT

I created a system that allows the register and login of users through email and password in a public route. Once you login as Admin a JWT is generated that gives 
access to the private route, in order to get all users information from the data base 

## Tech used:
* Java programming language with Springboot framework
* SpringSecurity and jwt
* Private key and public key
* Strong user registration authentication 

## App features:
* Register and login of users through the POST endpoints in the public route /user.
* Saving user data into a MySQL database. The password is encrypted using a Hash.
* It gets information from registered users by the private route /auth using the GET endpoint. As long as the user has the JWT token that the admin receives from
public route. 

### System made by Israel Fernández Agudo.

