import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

/**
 * The model representing a LightsOutSolver. Receives the user inputs of the dimensions of a Lights
 * Out board and the number of desired turns for the board to be solves. If the board is solvable
 * within the given number of turns, the model will output the solution; otherwise, it will print
 * the line "Unable to solve the board in [given value] moves."
 */
public class LightsOutSolver {

  /**
   * Creates and inputs constraints of a Lights Out board into the SAT solver. Please refer to Part
   * III of the report for more information on the constraint creations.
   *
   * @param ctx The Context object of a SAT solver. Responsible for creating constraints.
   * @param n   The dimensions of the Lights-Out game board.
   */
  public void sat(Context ctx, int n, int k) {

    if (n <= 0 || k <= 0) {
      throw new IllegalArgumentException("Please enter positive values only.");
    }

    Solver s = ctx.mkSolver();

    BoolExpr[][][] grid = new BoolExpr[k][n][n];
    BoolExpr[][][] move = new BoolExpr[k][n][n];

    for (int turn = 0; turn < k; turn++) {
      for (int row = 0; row < n; row++) {
        for (int col = 0; col < n; col++) {
          grid[(turn + 1) % k][row][col] = ctx.mkBoolConst("p" + (turn + 1) + row + col);
          grid[0][row][col] = ctx.mkFalse();
          move[turn][row][col] = ctx.mkBoolConst("m" + turn + row + col);
        }
      }
    }
    // Constraints to ensure that only one move variable per grid[] is true for each turn.
    BoolExpr oneFlipOnly = ctx.mkFalse();
    // Constraints representing the tiles to be flipped with a move
    BoolExpr flipped;
    // Constraints representing the tiles to remain the same with a move
    BoolExpr unflipped;
    for (int turn = 1; turn < k; turn++) {
      for (int row = 0; row < n; row++) {
        for (int col = 0; col < n; col++) {
          oneFlipOnly = ctx.mkXor(move[turn - 1][row][col], oneFlipOnly);
          unflipped = mkUnflipped(turn, row, col, ctx, grid);
          // The tile to be clicked.
          BoolExpr thisTile = ctx.mkXor(grid[turn][row][col], grid[turn - 1][row][col]);
          // The tile above thisTile.
          BoolExpr upTile = ctx.mkXor(grid[turn][(n + row - 1) % n][col],
              grid[turn - 1][(n + row - 1) % n][col]);
          // The tile to the left of thisTile.
          BoolExpr leftTile = ctx.mkXor(grid[turn][row][(n + col - 1) % n],
              grid[turn - 1][row][(n + col - 1) % n]);
          // The tile below thisTile.
          BoolExpr downTlie = ctx.mkXor(grid[turn][(row + 1) % n][col],
              grid[turn - 1][(row + 1) % n][col]);
          // The tile to the right of thisTile.
          BoolExpr rightTile = ctx.mkXor(grid[turn][row][(col + 1) % n],
              grid[turn - 1][row][(col + 1) % n]);
          // checks for corner and edge cases.
          if (row == 0 && col == 0) {
            flipped = ctx.mkAnd(thisTile, downTlie, rightTile);
          } else if (row == 0 && col == n - 1) {
            flipped = ctx.mkAnd(thisTile, downTlie, leftTile);
          } else if (row == n - 1 && col == 0) {
            flipped = ctx.mkAnd(thisTile, upTile, rightTile);
          } else if (row == n - 1 && col == n - 1) {
            flipped = ctx.mkAnd(thisTile, upTile, leftTile);
          } else if (row == 0) {
            flipped = ctx.mkAnd(thisTile, downTlie, leftTile, rightTile);
          } else if (col == 0) {
            flipped = ctx.mkAnd(thisTile, upTile, downTlie, rightTile);
          } else if (row == n - 1) {
            flipped = ctx.mkAnd(thisTile, upTile, leftTile, rightTile);
          } else if (col == n - 1) {
            flipped = ctx.mkAnd(thisTile, upTile, downTlie, leftTile);
          } else {
            flipped = ctx.mkAnd(thisTile, upTile, downTlie, leftTile, rightTile);
          }
          BoolExpr implication = ctx
              .mkImplies(move[turn - 1][row][col], ctx.mkAnd(flipped, unflipped));
          s.add(implication);
        }
      }
      s.add(oneFlipOnly);
      oneFlipOnly = ctx.mkFalse();
    }

    // Creates the contraint that all tiles after the last move should be true.
    BoolExpr allTrue = ctx.mkTrue();
    for (int turn = 0; turn < k; turn++) {
      for (int row = 0; row < n; row++) {
        for (int col = 0; col < n; col++) {
          allTrue = ctx.mkAnd(ctx.mkEq(grid[k - 1][row][col], ctx.mkTrue()), allTrue);
        }
      }
    }
    s.add(allTrue);

    if (s.check() == Status.SATISFIABLE) {
      Model m = s.getModel();
      int[][] result = new int[n][n];
      for (int row = 0; row < n; row++) {
        for (int col = 0; col < n; col++) {
          int count = 0;
          for (int turn = 0; turn < k; turn++) {
            if (m.evaluate(move[turn][row][col], true).isTrue()) {
              count++;
            }
          }
          result[row][col] = count % 2;
        }
      }

      System.out.println("Solution (click on the tiles marked \"1\"): ");
      for (int[] row : result) {
        for (int tile : row) {
          System.out.print(tile + "  ");
        }
        System.out.println();
      }
    } else {
      System.out.println("Unable to solve the board in " + (k - 1) + " moves.");
    }
  }

  /**
   * Creates a BoolExpr that represents all tiles that should not be flipped with a certain move.
   *
   * @param turn The turn the solution is at.
   * @param row  The row of the tile to be flipped.
   * @param col  The column of the tile to be flipped.
   * @param ctx  The Context of the SAT solver.
   * @param grid The array of boards of each turn.
   * @return A BoolExpr that represents all tiles that should not be flipped with a certain move.
   */
  public BoolExpr mkUnflipped(int turn, int row, int col, Context ctx,
      BoolExpr[][][] grid) {
    int size = grid[0].length;
    BoolExpr theRest = ctx.mkTrue();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        boolean oneOf = (i == row && j == col) || (i == row && j == col - 1) ||
            (i == row && j == col + 1) || (i == row - 1 && j == col) || (i == row + 1 && j == col);
        if (!oneOf) {
          theRest = ctx.mkAnd(ctx.mkIff(grid[turn][i][j], grid[turn - 1][i][j]), theRest);
        }
      }
    }
    return theRest;
  }
}