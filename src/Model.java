
public class Model {

  //
  private int[][] board;

  // list of boards of maximum size k where k is the number of rounds
  // List of board
  int[][][] boardStates;

  // input constraints to SAT solver
  // e.g. "flip" the value of one tile at once
  private enum status {}


  // change to booleans
  // (a,b,c,d,e) (a = (obj, (b,c,d,e)) (b = (obj, (a,c,d,e))
  // (board[i][j] = (1, i+1, i-1, )
  // Tile
  // fields: value; neighbor col; neighbor row;
  private boolean allFlipped = isAllFlipped();

  //
  // solve it in k moves
  private int moves;



  /**
   * @param row The row of the tile to be flipped
   * @param col
   */
  private void flip(int row, int col) {
    /*
    [0, 0, 0]
    [0, 0, 0]
    [0, 0, 0]

    (0,0)
    (size, 0)
    (0, size)
    (size, size)
     */
    if (row == 0 && col == 0) {
      board[row][col + 1] = (board[row][col] + 1) % 2;
      board[row + 1][col] = (board[row][col] + 1) % 2;
    }
    board[row][col] = (board[row][col] + 1) % 2;
    board[row][col] = (board[row][col] + 1) % 2;
    board[row][col] = (board[row][col] + 1) % 2;
  }

/*
  v flip(){

  }*/

  /**
   *
   */
  private boolean isAllFlipped() {
    for (int[] row : board) {
      for (int tile : row) {
        return tile != 1;
      }
    }
    return true;
  }
}
