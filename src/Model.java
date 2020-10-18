
public class Model {

  //
  private int[][] board;

  private enum status {}

  // (a,b,c,d,e) (a = (obj, (b,c,d,e)) (b = (obj, (a,c,d,e))
  // (board[i][j] = (1, i+1, i-1, )
  // Tile
  // fields: value; neighbor col; neighbor row;
  private boolean allFlipped = isAllFlipped();


  public static class Cell {
    public int value;
    public int row;
    public int col;
    public int prevCol = -1;
    public int prevRow = -1;
    public int nextCol = -1;
    public int nextRow = -1;

    public Cell(int value, int row, int col) {
      this.value = value;
      this.row = row;
      this.col = col;
      this.prevRow = row - 1;
      this.prevCol = col - 1;
      this.nextCol = col -1;
      this.nextRow = row -1;
    }
  }


  /**
   * Constructor. Sets all tiles in the board to value -1.
   *
   * @param size The size n of the n x n Lights-out board.
   */
  public Model(int size) {
    if (size < 1) {

    }
    board = new int[size][size];
    for (int[] row : board) {
      for (int tile : row) {
        tile = 0;
      }
    }
  }

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
