import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.minisat.core.Solver;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.minisat.*;

public class Project {
  /*
  (x1 v x2 v x3) ^ (-x1 v x4)

  ->

  p 4 2
  1 2 3
  -1 4
   */

  public static void main(String[] args) {
    ISolver solver = SolverFactory.newDefault();
    solver.setTimeout(3600); // 1 hour timeout
    Reader reader = new DimacsReader(solver);
    PrintWriter out = new PrintWriter(System.out, true);
    // CNF filename is given on the command line
    try {
      Scanner c = new Scanner(System.in);
      c.nextLine();
      IProblem problem = reader.parseInstance(args[0]);
      if (problem.isSatisfiable()) {
        System.out.println("Satisfiable !");
        reader.decode(problem.model(), out);
      } else {
        System.out.println("Unsatisfiable !");
      }
    } catch (IllegalArgumentException | ParseFormatException
        | IOException | ContradictionException | TimeoutException e) {

    }
  }
}
