package net.salesianos;

import java.util.ArrayList;
import java.util.List;

public class GameRoom {
  private final String roomId;
  private final List<ClientHandler> players = new ArrayList<>();
  private final GameLogic gameLogic = new GameLogic();
  private boolean isGameStarted = false;

  public GameRoom(String roomId) {
    this.roomId = roomId;
  }

  public String getRoomId() {
    return roomId;
  }

  public synchronized boolean addPlayer(ClientHandler player) {
    if (players.size() < 2) {
      players.add(player);
      if (players.size() == 2) {
        isGameStarted = true;
      }
      return true;
    }
    return false;
  }

  public boolean isReady() {
    return isGameStarted;
  }

  public synchronized void broadcast(String message, ClientHandler sender) {
    for (ClientHandler player : players) {
      player.sendMessage(message);
    }
    // Enviar mensaje de quien tiene el turno
    if (isGameStarted) {
      char currentPlayer = gameLogic.getCurrentPlayer();
      for (ClientHandler player : players) {
        if (player != sender) {
          player.sendMessage("Es tu turno. Jugador: " + currentPlayer);
        } else {
          player.sendMessage("Es el turno del jugador: " + currentPlayer);
        }
      }
    }
  }

  public GameLogic getGameLogic() {
    return gameLogic;
  }

  public List<ClientHandler> getPlayers() {
    return this.players;
  }

}
