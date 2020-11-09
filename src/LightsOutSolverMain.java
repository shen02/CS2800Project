import com.microsoft.z3.Context;
import java.util.Scanner;

public class LightsOutSolverMain {

  /**
   * Main method of {@link LightsOutSolver}. Prompts two user inputs n and k representing the
   * dimension of the game's square board and the number of moves in which to win the game
   * respectively. Both inputs must be positive integers.
   * <p>
   *   Delegates printed output to {@link LightsOutSolver}.
   * </p>
   *
   * @param args Arguments from stdin. Never used.
   */
  public static void main(String[] args) {
    Scanner c = new Scanner(System.in);
    LightsOutSolver solver = new LightsOutSolver();
    String promptDimension = "Please enter the dimension of the square board, or 0 to quit: ";
    String promptNumTurns = "Please enter the number of turns to win in, or 0 to quit: ";

    System.out.println("Welcome to the Lights Out SAT Solver.");
    System.out.print(promptDimension);
    int n = c.nextInt();
    int k;

    while (n != 0) {
      System.out.print(promptNumTurns);

      k = c.nextInt();

      if (k == 0) {
        break;
      }

      try {
        solver.sat(new Context(), n, k + 1);
      } catch (IllegalArgumentException ie) {
        System.out.println(ie.getMessage());
      }

      System.out.print("Please enter a dimension or 0 to quit: ");
      n = c.nextInt();
    }

    System.out.print("Exited the Lights Out SAT Solver. Goodbye.");
  }
}
