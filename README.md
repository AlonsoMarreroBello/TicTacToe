# TacTacToe

### Descripción

  Este proyecto es un servidor de un juego de 3 en raya desarrollado en Java. Utiliza sockets para la comunicación entre clientes y un sistema de hilos para gestionar múltiples partidas simultáneamente. Cada partida es gestionada por un hilo independiente que mantiene la sincronización entre dos jugadores.

### Tecnologías utilizadas

- Lenguaje: Java

- Comunicación: Sockets

- Concurrencia: Hilos

### Estructura del Proyecto

#### Clases principales

- ##### TicTacToeServer

  - Inicia el servidor en un puerto específico.

  - Acepta conexiones de clientes y los asigna a una sala de juego.

- ##### GameLogic & GameRoom

  - Gestiona la lógica de una partida de 3 en raya entre dos jugadores.

  - Controla los turnos y la validación de las jugadas.

  - Envía y recibe mensajes entre los clientes.

 - ##### ClienteHandler

  - Representa la conexión de un jugador con el servidor.

  - Envia las jugadas del cliente al servidor y recibe las respuestas.

### Flujo de ejecución

  El servidor se inicia y espera conexiones en un puerto determinado.

  Los jugadores se conectan al servidor mediante sockets.

  Una vez que dos jugadores se han conectado, se crea un hilo de ManejadorPartida para gestionar la partida.

#### Durante la partida:

  Se alternan los turnos.

  Se validan los movimientos.

  Se notifica a los jugadores el estado del tablero.

  Al finalizar la partida, los jugadores reciben el resultado y pueden desconectarse o iniciar una nueva partida.

### Configuración

  Para cambiar el puerto del servidor, modifique la variable correspondiente en la clase Servidor.

La comunicación se realiza mediante intercambio de mensajes en texto plano.

### Ejecución

#### Servidor

  Ejecute el siguiente comando para iniciar el servidor desde la carpeta del codigo fuente:

    cmd /C "java -XX:+ShowCodeDetailsInExceptionMessages -cp .\bin net.salesianos.TicTacToeServer"

#### Cliente

  Cada cliente debe conectarse usando un socket al servidor especificando la dirección IP y puerto configurados.

  Puede levantarse una instancia de la aplicacion cliente mediante el uso del siguiente comando desde el directorio del codigo fuente

    cmd /C ""C:\Program Files\Java\jdk-17\bin\java.exe" -XX:+ShowCodeDetailsInExceptionMessages -cp .\bin net.salesianos.TicTacToeClient"