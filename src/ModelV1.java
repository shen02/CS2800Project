import java.util.Map;

//
public class ModelV1 {

  // represents an n x n board
  boolean[][] board;

  // A list of boards
  // Keep the coordinates of the changed tile
  boolean[][][] game;



  Map<Boolean, Boolean> value;

  /**
   * [  f, f      -> transition (come up with some type of CNF that has all contraints
   *    f, f ]       e.g. only flip one tile at a time. 4 transitions, p1 = -q1, p2 = -q2... p4 = p4.
   *                 -p1 & (p1 & -q1) & (p4 ??=> p4).
   *                 ??? ^^
   *
   *                 Tiles that haven't flipped?
   *
   *              -> n x n new states
   *              -> each new state will show the change after the transition
   * [  t, t    [ t, t,    [t, f,   [f, t,
   *    t, f  ]   f, t  ]   t, t  ]   t, t ]
   */


  //
  String result;



  // corner cases?


  //System.getProperty("java.class.path")
  // flip only unflipped tiles ->
  /*
  1 2 3
  4 5 6
  7 8 9
   */
  // if we click on t5 ->
  // -p5 & -p4 & -p2 & -p8 & p1 & p3 & p7 & p8


  // solve it in k moves
  private int moves;

  /**
   * Constructor. Sets all tiles in the board to value -1.
   *
   * @param size The size n of the n x n Lights-out board.
   */
  public ModelV1(int size, int moves) {
    if (size < 1 || moves < 0) {

    }
    board = new boolean[size][size];
    for (boolean[] row : board) {
      for (boolean tile : row) {
        tile = false;
      }
    }
  }

  //
  private String createSAT(int row, int col){
    return "";
  }
}
