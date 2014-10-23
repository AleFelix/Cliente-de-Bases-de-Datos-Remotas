Cliente-de-Bases-de-Datos-Remotas
=================================
Conectarse desde la aplicación en modo cliente a bases de datos que se ejecutan en maquinas remotas, usando como intermediario a la aplicación en modo servidor

####Como ejecutarlo:

1. Descargar el archivo .jar desde [la sección releases](https://github.com/AleFelix/Cliente-de-Bases-de-Datos-Remotas/releases)
2. En la maquina servidor, ejecutar el archivo desde la consola con el parametro "s"
3. En la maquina cliente, ejecutar el archivo desde la consola con el parametro "c"
4. En ambos casos debe haber una carpeta llamada "XML" en el mismo directorio del .jar, en ella se deben crear los archivos de configuración de la conexión. Si la carpeta o los archivos no existen, se crearan con configuraciones por defecto.
5. Desde el cliente se debe especificar el nombre de la base de datos a consultar y la consulta a enviar
6. Para enviar la consulta, hay que presionar enter en la caja de texto inferior
