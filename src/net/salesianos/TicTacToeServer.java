package net.salesianos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import java.util.HashMap;
import java.util.List;

public class TicTacToeServer {
  private final int port;
  private final Map<String, GameRoom> rooms = new HashMap<>();

  public TicTacToeServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) {
    TicTacToeServer server = new TicTacToeServer(3000);
    server.start();
  }

  public void start() {
    System.out.println("Servidor de 3 en Raya iniciado.");
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println(clientSocket.getLocalAddress());
        new Thread(new ClientHandler(clientSocket, this)).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public synchronized GameRoom createRoom(String roomId, ClientHandler player) {
    rooms.putIfAbsent(roomId, new GameRoom(roomId));
    GameRoom room = rooms.get(roomId);
    room.addPlayer(player);
    return room;
  }

  public synchronized GameRoom joinRoom(String roomId, ClientHandler player) {
    GameRoom room = rooms.get(roomId);
    if (room != null && room.addPlayer(player)) {
      return room;
    }
    return null;
  }

  public synchronized void removeRoom(GameRoom room) {
    rooms.remove(room.getRoomId());
  }

  public synchronized void leaveRoom(GameRoom room, ClientHandler player) {
    room.broadcast("Jugador desconectado.", player);
    if (room != null) {
      // Obtener al otro jugador
      List<ClientHandler> players = room.getPlayers();
      if (players.size() == 2) {
        ClientHandler otherPlayer = players.get(0) == player ? players.get(1) : players.get(0);

        // Notificar al otro jugador que ha ganado
        otherPlayer.sendMessage("El otro jugador se ha desconectado. ¡Eres el ganador!");

        // Cerrar la sala
        rooms.remove(room.getRoomId());
      }
    }
  }
}
