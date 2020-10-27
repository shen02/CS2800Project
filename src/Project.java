import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class Project {
  /*
  (x1 v x2 v x3) ^ (-x1 v x4)
  ->
  p 4 2
  1 2 3
  -1 4
   */
  // starting state
  // ending state
  // win in exactly k moves
  //
  public static void main(String[] args) {
    ISolver solver = SolverFactory.newDefault();
    solver.setTimeout(3600); // 1 hour timeout
    Reader reader = new DimacsReader(solver);
    PrintWriter out = new PrintWriter(System.out, true);
    // CNF filename is given on the command line
    try {
      Scanner c = new Scanner(System.in);
      System.out.print("Enter a file name: ");
      IProblem problem = reader.parseInstance(c.nextLine());
      if (problem.isSatisfiable()) {
        System.out.println("Satisfiable !");
        reader.decode(problem.model(), out);
      } else {
        System.out.println("Unsatisfiable !");
      }
    } catch (FileNotFoundException e) {
        System.out.print("no");
    } catch (ContradictionException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseFormatException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
    //IllegalArgumentException | ParseFormatException
    //        | IOException | ContradictionException | TimeoutException e
  }
}
