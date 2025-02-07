package net.salesianos;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TicTacToeClient {
  private static final String SERVER_ADDRESS = "localhost";
  private static final int SERVER_PORT = 3000;

  public static void main(String[] args) {
    try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in)) {

      System.out.println("Conectado al servidor Tic Tac Toe.");

      // Hilo para escuchar mensajes del servidor
      Thread listener = new Thread(() -> {
        try {
          String serverMessage;
          while ((serverMessage = in.readLine()) != null) {
            System.out.println("Servidor: " + serverMessage);
          }
        } catch (IOException e) {
          System.out.println("Conexión cerrada.");
        }
      });
      listener.start();

      // Enviar comandos al servidor
      String command;
      System.out.println("Escribe comandos (CREATE <roomId>, JOIN <roomId>, MOVE <cell>, EXIT):");
      while (!(command = scanner.nextLine()).equalsIgnoreCase("EXIT")) {
        out.println(command);
      }

      // Cerrar la conexión
      out.println("EXIT");
      socket.close();
      System.out.println("Cliente desconectado.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
