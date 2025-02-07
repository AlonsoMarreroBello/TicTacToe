package net.salesianos;

public class GameLogic {
  private char[][] board;
  private char currentPlayer;

  public GameLogic() {
    this.board = new char[3][3];
    this.currentPlayer = 'X'; // El jugador 'X' comienza
    resetBoard();
  }

  // Reinicia el tablero
  public void resetBoard() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = ' ';
      }
    }
  }

  // Devuelve el tablero actual como String
  public String getBoard() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        sb.append(board[i][j]);
        if (j < 2)
          sb.append("|");
      }
      sb.append("\n");
      if (i < 2)
        sb.append("-----\n");
    }
    return sb.toString();
  }

  // Realiza un movimiento en el tablero
  public boolean makeMove(int row, int col) {
    if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != ' ') {
      return false; // Movimiento inválido
    }
    board[row][col] = currentPlayer;
    return true;
  }

  // Cambia de jugador
  public void switchPlayer() {
    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
  }

  // Devuelve el jugador actual
  public char getCurrentPlayer() {
    return currentPlayer;
  }

  // Verifica si alguien ha ganado
  public char checkWinner() {
    // Revisa filas, columnas y diagonales
    for (int i = 0; i < 3; i++) {
      if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ') {
        return board[i][0];
      }
      if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ') {
        return board[0][i];
      }
    }
    if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') {
      return board[0][0];
    }
    if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ') {
      return board[0][2];
    }
    return ' '; // No hay ganador
  }

  // Verifica si el tablero está lleno
  public boolean isBoardFull() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] == ' ') {
          return false;
        }
      }
    }
    return true;
  }
}
