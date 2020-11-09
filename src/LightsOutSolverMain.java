import com.microsoft.z3.Context;
import java.util.Scanner;

public class LightsOutSolverMain {

  /**
   * Main method of LightsOutSolver. Prompts for user inputs and returns the appropriate solution.
   *
   * @param args Unused argument.
   */
  public static void main(String[] args) {
    Scanner c = new Scanner(System.in);
    LightsOutSolver solver = new LightsOutSolver();
    String promptDim = "Please enter a positive dimension or 0 to quit: ";
    String promptTurn = "Please enter a positive # of turns or 0 to quit: ";

    System.out.println("Welcome to Lights-out SAT-Solver.");
    System.out.print(promptDim);
    int n = c.nextInt();
    int k;
    while (n != 0) {
      System.out.print(promptTurn);
      k = c.nextInt();
      if (k == 0) {
        break;
      }
      try {
        solver.sat(new Context(), n, k + 1);
      } catch (IllegalArgumentException e) {
        System.out.println("Please enter positive values only.");
      }
      System.out.print("Please enter a dimension or 0 to quit: ");
      n = c.nextInt();
    }
    System.out.print("Goodbye.");
  }
}
