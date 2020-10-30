import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class ModelV5 {

  static class Posn {

    int x;
    int y;

    public Posn(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
      return ((Posn) obj).y == this.y && ((Posn) obj).x == this.x;
    }

    @Override
    public int hashCode() {
      return x * 11 + y;
    }
  }


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
    BoolExpr[] moveExpanded;
    BoolExpr[] nextExpanded = new BoolExpr[n*n];
    BoolExpr[][] next = new BoolExpr[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        grid[i][j] = ctx.mkFalse();
        next[i][j] = ctx.mkBoolConst("p" + i + j + state);
        move[i][j] = ctx.mkBoolConst("m" + i + j + state);
      }
    }
    moveExpanded = flatten(move);
    nextExpanded = flatten(next);

    System.out.print(makeAndAll(nextExpanded,ctx));


    // At most and at least one move at a time
    BoolExpr oneFlipOnly = ctx
        .mkAnd(ctx.mkAtLeast(moveExpanded, 1), ctx.mkAtMost(moveExpanded, 1));

    //
    BoolExpr[] implications = new BoolExpr[n * n];
    int index = 0;
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        //implications[index] = ctx.mkImplies(move[i][j])
      }
    }

    s.add(ctx.mkFalse());

    // edge cases

    if (s.check() == Status.SATISFIABLE) {
      Model m = s.getModel();

      //
      List<Posn> validMoves = new ArrayList<Posn>();
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          Expr value = m.evaluate(move[i][j], false);
          System.out.print(value + " ");
          if (value.isTrue()) {
            validMoves.add(new Posn(i, j));
          }
        }
      }

      //
      System.out.println("Solution: ");
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (validMoves.contains(new Posn(i, j))) {
            System.out.print("1   ");
          } else {
            System.out.print("0   ");
          }
        }
        System.out.println();
      }
    } else {
      System.out.println("Unable to solve the board. ");
      System.out.print("Enter 1 to view constrains anything else to quit: ");
      int response = new Scanner(System.in).nextInt();
      if (response == 0) {
        System.out.print(s.toString());
      }
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

  static BoolExpr makeAndAll(BoolExpr[] grid, Context ctx){

    if(grid.length == 0){
      return ctx.mkTrue();
    }
    return ctx.mkAnd(grid[0], makeAndAll(Arrays.copyOfRange(grid,1, grid.length), ctx));

  }



  /**
   * Main method. Add more.
   *
   * @param args
   */
  public static void main(String[] args) {
    Scanner c = new Scanner(System.in);

    System.out.println("Welcome to Lights-out SAT-Solver.");
    System.out.print("Please enter a dimension or 0 to quit: ");
    int n = c.nextInt();

    while (n > 0) {
      sat(new Context(), n);
      System.out.print("Please enter a dimension or 0 to quit: ");
      n = c.nextInt();
    }
    System.out.print("Goodbye.");
  }
}