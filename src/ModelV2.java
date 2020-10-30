import com.microsoft.z3.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class ModelV2 {


  static List<int[]> tilesClicked = new ArrayList<int[]>();

  /**
   * [description here]
   *
   * @param ctx
   * @param n   The dimensions of the Lights-Out game board.
   */
  static void sat(Context ctx, int n) {

    Solver s = ctx.mkSolver();

    BoolExpr[][] grid = new BoolExpr[n][n];
    BoolExpr[][] move = new BoolExpr[n][n];
    BoolExpr[] moveExpanded = new BoolExpr[n * n];
    BoolExpr[][] next = new BoolExpr[n][n];

    int count = 0;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        grid[i][j] = ctx.mkFalse();
        move[i][j] = ctx.mkBoolConst("m" + i + j + count);
        next[i][j] = ctx.mkBoolConst("p" + i + j + count);
      }
    }

    int d = 0;
    while (d < n * n) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          moveExpanded[d] = move[i][j];
          d++;
        }
      }
    }

    BoolExpr[][] row = new BoolExpr[n][n];
    // while(s.check() == Status.UNSATISFIABLE) {
    for (int l = 0; l < 1; l++) {
      BoolExpr xor = ctx.mkAnd(ctx.mkAtLeast(moveExpanded, 1), ctx.mkAtMost(moveExpanded, 1));
      s.add(xor);
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {


          row[i][j] = ctx.mkIff(next[i][j], ctx.mkNot(grid[i][j]));
          s.add(ctx.mkImplies(move[i][j], row[i][j]));

          ArrayExpr a = ctx.mkArrayConst(i+"", ctx.mkBoolSort(),ctx.mkBoolSort());
          System.out.println(a);
          grid[i][j] = ctx.mkBool(next[i][j].isTrue());
          next[i][j] = ctx.mkBoolConst("p" + i + j + count);
          move[i][j] = ctx.mkBoolConst("m" + i + j + count);
          // System.out.print(moveExpanded[i].isTrue() + " ");

        }
      }
      count++;
    }

    System.out.println(s.toString());
    for (int i = 0; i < tilesClicked.size(); i++) {
      //System.out.println(tilesClicked.get(i)[0] + " " + tilesClicked.get(i)[1]);
    }

    if (s.check() == Status.SATISFIABLE) {
      System.out.print("Worked!");
    } else {
      System.out.print("Unable to solve the board.");
    }
  }


  /**
   * Main method. Add more.
   *
   * @param args
   */
  public static void main(String[] args) {
    Scanner c = new Scanner(System.in);

    System.out.println("Welcome to Lights-out SAT-Solver.");
    System.out.print("Please enter a dimension: ");
    int n = c.nextInt();

    sat(new Context(), n);
  }
}