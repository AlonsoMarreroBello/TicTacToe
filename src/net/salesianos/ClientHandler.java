package net.salesianos;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
  private final Socket socket;
  private final TicTacToeServer server;
  private PrintWriter out;
  private GameRoom currentRoom;

  public ClientHandler(Socket socket, TicTacToeServer server) {
    this.socket = socket;
    this.server = server;
  }

  @Override
  public void run() {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
      out = new PrintWriter(socket.getOutputStream(), true);

      out.println("Bienvenido al servidor de 3 en Raya.");
      String command;
      while ((command = in.readLine()) != null) {
        processCommand(command);
      }
    } catch (IOException e) {
      System.out.println("Conexión cerrada.");
    } finally {
      if (currentRoom != null) {
        server.leaveRoom(currentRoom, this);
      }
    }
  }

  private void processCommand(String command) {
    if (command.startsWith("CREATE ")) {
      if (currentRoom != null) {
        out.println("No puedes estar en dos salas a la vez");
      } else {
        String roomId = command.substring(7);
        currentRoom = server.createRoom(roomId, this);
        out.println("Sala creada: " + roomId);
      }
    } else if (command.startsWith("JOIN ")) {
      if (currentRoom != null) {
        out.println("No puedes estar en dos salas a la vez");
      } else {
        String roomId = command.substring(5);
        currentRoom = server.joinRoom(roomId, this);
        if (currentRoom != null) {
          out.println("Te uniste a la sala: " + roomId);
        } else {
          out.println("La sala no existe o está llena.");
        }
      }
    } else if (command.startsWith("MOVE ")) {
      if (currentRoom != null && currentRoom.isReady()) {
        String[] parts = command.substring(5).split(",");
        try {
          int row = Integer.parseInt(parts[0]);
          int col = Integer.parseInt(parts[1]);

          GameLogic gameLogic = currentRoom.getGameLogic();

          // Verificar si el jugador tiene el turno
          if (gameLogic.getCurrentPlayer() == (this == currentRoom.getPlayers().get(0) ? 'X' : 'O')) {
            if (gameLogic.makeMove(row, col)) {
              char winner = gameLogic.checkWinner();
              if (winner != ' ') {
                currentRoom.broadcast("GANADOR: " + winner + gameLogic.getBoard(), this);
              } else if (gameLogic.isBoardFull()) {
                currentRoom.broadcast("EMPATE", this);
                gameLogic.switchPlayer();
                gameLogic.resetBoard();
              } else {
                gameLogic.switchPlayer();
                currentRoom.broadcast("MOVIMIENTO: " + gameLogic.getBoard(), this);
              }
            } else {
              out.println("Movimiento inválido.");
            }
          } else {
            out.println("No es tu turno.");
          }
        } catch (Exception e) {
          out.println("Formato incorrecto. Usa: MOVE row,col");
        }
      } else {
        out.println("No estás en una sala o la partida no está lista.");
      }
    } else {
      out.println("Comando no reconocido.");
    }
  }

  public void sendMessage(String message) {
    out.println(message);
  }
}
