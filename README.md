# AREP-PARCIAL2T

Es un protoripo de calculadora de microservicios que tiene un servicio de matematicas el cual cuenta con dos funciones, acos y sin, se encuentran implementadas y desplegadas dentro de dos instancias virtuales de EC2. Ademas implementa un servicio de proxy que recibe las solicitudes de servicios y se las desplega a las dos instancias usando un algoritmo de round robin. Tiene la configuracion de las dirrecciones y puertos usando variables de entorno.
![imagen](./img/1.PNG)

## Math Service 1

![](./img/5.PNG)
![](./img/6.PNG)

como podemos ver en las siguientes imagenes se puede apreciar que la direccion de donde se ecnuentra subida es, en este caso el puerto es 4568
``` 
ec2-54-210-250-199.compute-1.amazonaws.com
```

como se decia anteriormente este cuenta con dos funcione de matematicas, en este caso es sin y acos

## Math Service 2

![](./img/7.PNG)
![](./img/8.PNG)

como podemos ver en las siguientes imagenes se puede apreciar que la direccion de donde se ecnuentra subida es, en este caso el puerto es 4568
``` 
ec2-44-202-237-209.compute-1.amazonaws.com
```

como se decia anteriormente este cuenta con dos funcione de matematicas, en este caso es sin y acos

## Service proxy
En este caso el que esta consumiendo los servicios que la url de este es, y el puerto que esta usando es 4567
``` 
http://ec2-3-87-66-72.compute-1.amazonaws.com:4567/math
```
