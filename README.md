# TFG_PC
## Aplicación de PC para EasyShop

EasyShop es un software ERP para tiendas de ropa de venta al por menor. Permite la
gestión de la base de datos de los artículos de la tienda y, además incluye una aplicación
para ser instalada en una tablet que estará situada dentro de la tienda y desde la que se
podrá consultar el catálogo y elegir artículos para su posterior compra. Gracias a la tablet, el
cliente podrá ver toda la lista de ropa de la tienda sin necesidad de pasearse por ella. Además
podrá encontrar productos que no se encuentren en la tienda en ese momento porque se
encuentren en el almacén.
La alta versatilidad del software permitirá su uso incluso en tiendas no especializadas que
pretendan realizar venta de productos de ropa pero que no dispongan de un expositor como
tal, como puede ser un hotel que quiera vender productos de su marca en el recibidor del
hotel.
El proyecto desarrolla dos aplicaciones: una para PC y otra para tablet.

La aplicación de PC cumple dos funciones:
+ La primera función es la de aceptar un pedido de un cliente recibiendo la lista de
productos deseados. Desde esta aplicación se podrá elegir si quiere los productos en el
mismo momento o prefiere recibirlos a domicilio, en cuyo caso se le pedirán los datos
personales y de domicilio. A continuación se procederá al pago del pedido realizado
por el TPV de la tienda.
+ La segunda función es la de gestionar los productos de los que dispone la tienda.
Manejará los productos en una base de datos. Desde ella se podrán añadir, modificar y
eliminar los productos que se deseen de una forma gráfica y sencilla.

## Proceso de instalación
### Windows

Antes de descargar la aplicación del repositorio de GitHub debemos instalar el servidor
Apache con base de datosMySQL donde irá nuestra base de datos. Para ello debemos
acceder a la página de AppServ: https://www.appserv.org/en/ y descargarnos la última
versión. Una vez descargado ejecutamos el archivo que hemos descargado para proceder a
su instalación.

En la primera ventana pulsamos siguiente y en la siquiente aceptamos los términos y
condiciones. En el tercer paso es muy importante que no se cambie el directorio de destino
de la instalación ya que de esa ruta depende que la aplicación Android sea capaz de encontrar
las imágenes de los artículos. Pulsamos en siguiente, dejamos todas las casillas por defecto y
volvemos a pulsar en siguiente.

En el siguiente paso nos pide un nombre para el servidor y una dirección de email
de administrador, podemos poner aquí lo que queramos, no afectará al funcionamiento
de EasyShop. En la siguiente nos pide una contraseña de administrador. Escribimos una
contraseña que consideremos segura y pulsamos en instalar.

Una vez instalado pulsamos en finalizar y se cerrará el asistente de instalación iniciando
automáticamente nuestro servidor.

Ahora vamos a proceder a crear nuestra base de datos en nuestro servidor. Para ello
primero debemos descargar el proyecto del repositorio de GitHub público en el siguiente enlace:
https://github.com/victormartor/TFG_PC. Entramos en el enlace y descargamos
el proyecto en la carpeta que queramos de nuestro equipo. Descomprimimos el archivo ZIP
de descarga y localizamos el archivo easyshop.sql, en este archivo se encuentra nuestra base
de datos que debemos importar en nuestro servidor.

Para entrar en nuestro servidor abrimos nuestro navegador y escribimos la siguiente
dirección en la barra de naegación: localhost/phpmyadmin. Iniciamos sesión como administrador
(el nombre de administrador por defecto es root) y accedemos a nuestro servidor.
Una vez dentro pulsamos en la pestaña IMPORTAR para acceder a las opciones de importación,
buscamos el botón examinar y elegimos el archivo easyshop.sql quemencionamos
antes. Una vez elegido hacemos scroll hacia abajo hasta encontrar el botón continuar en
nuestro navegador. Lo pulsamos y empezará la importación de la base de datos, el proceso
puede tardar un poco, una vez finalice ya podremos cerrar la ventana del navegador.

Volvemos a la carpeta donde descomprimimos el proyecto y buscamos la carpeta dist,
dentro de ella está el archivo EasyShop.jar el cual es el ejecutable de nuestra aplicación.
Podemos crear un acceso directo de este archivo para no tener que acceder a esta carpeta
cada vez que lo queramos ejecutar.

Si todo ha ido bien, haciendo doble click en nuestro acceso directo debería ejecutarse
correctamente nuestra aplicación de PC. Si aparece algún mensaje de error es debido a que
la instalación del servidor no se hizo correctamente, por tanto habría que volver a instalarlo
siguiendo los pasos.

### Linux

Para instalar en nuestro equipo linux un servidor Apache+MySQL podemos seguir el
tutorial alojado en la página web DigitalOcean realizado por el usuario Brennen Bearnes. Para
verlo sigue el siguiente enlace: https://www.digitalocean.com/community/tutorials/
como-instalar-linux-apache-mysql-php-lamp-en-ubuntu-16-04-es. Solo tenemos
que hacer hasta el paso 2, ya que no necesitamos instalar PHP.

Una vez instalado nuestro servidor nos situamos en el directorio donde queremos tener
nuestra aplicación, descargamos el repositorio de github e importamos la base de datos de la
siguiente manera:

    sudo apt-get install git
    git clone https://github.com/victormartor/TFG_PC
    cd TFG_PC
    sudo su
    mysql -u root -p < easyshop.sql

De esta forma ya tenemos configurada la base de datos. Ahora nos vamos al directorio
dist y ejecutamos el comando: sudo java -jar EasyShop.jar, para ejecutar la aplicación. La
aplicación necesita ser ejecutada como administrador para poder usar la base de datos.
