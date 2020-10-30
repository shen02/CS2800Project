import com.microsoft.z3.*;
import com.microsoft.z3.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class ModelV4 {


  /**
   * [description here]
   *
   * @param ctx
   * @param n   The dimensions of the Lights-Out game board.
   */
  static void sat(Context ctx, int n) {

    Solver s = ctx.mkSolver();

    int state = 0;
    BoolExpr[][] grid = new BoolExpr[n][n];
    BoolExpr[][] move = new BoolExpr[n][n];
    BoolExpr[] moveExpanded = new BoolExpr[n * n];
    BoolExpr[][] next = new BoolExpr[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        grid[i][j] = ctx.mkFalse();
        next[i][j] = ctx.mkBoolConst("p" + i + j + state);
        move[i][j] = ctx.mkBoolConst("m" + i + j + state);
      }
    }

    //corner cases

    // edge cases

    System.out.print(s.toString());
    if (s.check() == Status.SATISFIABLE) {
      Model m = s.getModel();
      System.out.print("Worked!");


    } else {
      System.out.print("Unable to solve the board.");
    }
  }

  static BoolExpr[] flatten(BoolExpr[][] ar) {
    BoolExpr[] newAr = new BoolExpr[ar.length * ar[0].length];
    int index = 0;
    for (int i = 0; i < ar.length; i++) {
      for (int j = 0; j < ar[0].length; j++) {
        newAr[index] = ar[i][j];
        index++;
      }
    }
    return newAr;
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