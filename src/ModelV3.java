import com.microsoft.z3.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class ModelV3 {


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
    BoolExpr[] moveExpanded = new BoolExpr[n*n];
    BoolExpr[][] next = new BoolExpr[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        grid[i][j] = ctx.mkFalse();
        next[i][j] = ctx.mkFalse();
        move[i][j] = ctx.mkBoolConst("m" + i + j + state);
      }
    }
    //move[0][0] = ctx.mkTrue();
    moveExpanded = flatten(move);

    next[0][0] = ctx.mkTrue();
    next[0][1] = ctx.mkTrue();
    next[1][0] = ctx.mkTrue();
    BoolExpr xor = ctx.mkAnd(ctx.mkAtMost(moveExpanded,1), ctx.mkAtLeast(moveExpanded,1));
    BoolExpr one = ctx.mkIff(next[0][0], ctx.mkNot(grid[0][0]));
    BoolExpr two = ctx.mkIff(next[0][1], ctx.mkNot(grid[0][1]));
    BoolExpr three = ctx.mkIff(next[1][0], ctx.mkNot(grid[1][0]));
    BoolExpr four = ctx.mkIff(next[0][2], ctx.mkNot(grid[0][2]));
    BoolExpr five = ctx.mkIff(next[1][1], ctx.mkNot(grid[1][1]));
    BoolExpr six = ctx.mkIff(next[1][2], ctx.mkNot(grid[1][2]));
    BoolExpr seven = ctx.mkIff(next[2][0], ctx.mkNot(grid[2][0]));
    BoolExpr eight = ctx.mkIff(next[2][1], ctx.mkNot(grid[2][1]));
    BoolExpr nine = ctx.mkIff(next[2][2], ctx.mkNot(grid[2][2]));

    BoolExpr all = ctx.mkAnd(one,two,three,four,five,six,seven,eight,nine);
    BoolExpr imp1 = ctx.mkImplies(move[0][0],all);
    BoolExpr imp2 = ctx.mkImplies(move[0][1],all);
    BoolExpr imp3 = ctx.mkImplies(move[0][2],all);
    BoolExpr imp4 = ctx.mkImplies(move[1][0],all);
    BoolExpr imp5 = ctx.mkImplies(move[1][1],all);
    BoolExpr imp6 = ctx.mkImplies(move[1][2],all);
    BoolExpr imp7 = ctx.mkImplies(move[2][0],all);
    BoolExpr imp8 = ctx.mkImplies(move[2][1],all);
    BoolExpr imp9 = ctx.mkImplies(move[2][2],all);


    s.add(ctx.mkAnd(xor,imp1, imp2, imp3, imp4,imp5,imp6,imp7,imp8, imp9));

    System.out.print(s.toString());
    if (s.check() == Status.SATISFIABLE) {
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