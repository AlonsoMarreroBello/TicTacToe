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

  }

  public void sendMessage(String message) {
    out.println(message);
  }

}
