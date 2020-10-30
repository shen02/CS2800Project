import com.microsoft.z3.Context;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;

import com.microsoft.z3.*;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.DimacsStringSolver;
//
public class ModelV1 {

  Context abc = new Context();


  // represents an n x n board
  Map<String, Boolean>[][] board;

  // A list of boards
  // Keep the coordinates of the changed tile
  boolean[][] game;

  // p0 = state 0 and q0 for state 1
  // p1 = q0, q1
  // (p )


  // Have a list(??) that includes all coordinates of tiles clicked -> display them on board

  DimacsStringSolver solver = new DimacsStringSolver();

  // key = before, value = after?
  Map<Boolean, Boolean> vale;

  //

  /**
   * [  f, f      -> transition (come up with some type of CNF that has all contraints
   *    f, f ]       e.g. only flip one tile at a time. 4 transitions, p1 = -q1, p2 = -q2... p4 = p4.
   *                 -p1 & (p1 & -q1) & (p4 ??=> p4).
   *                 ??? ^^
   *
   *                 k -> the maximum number of moves used to solve the grid
   *
   *                 Tiles that haven't flipped? n <= 5
   *
   *              -> n x n new states
   *              -> each new state will show the change after the transition
   * [  t, t    [ t, t,    [  t, f,    [ f, t,
   *    t, f  ]   f, t  ]     t, t  ]    t, t ]
   */


  /**
   * Type 1: p[row][col][state] e.g. p000 p010 etc...
   * p000 p010
   * p100 p110
   *
   * // indicates the tile that is to be flipped
   * Type 2: m[row][col][state]
   * m000 m010
   * m100 m110
   *
   * State0
   * boolean p000, p010, p100, p110 = false;
   * // All false because false = not-flipped, true = flipped.
   * false false
   * false false
   *
   * boolean m000, m010, m100, m110;
   * // Only flip one tile at a time
   * XOR(m000,m010,m100,m110) ^
   *
   * -p v q
   * // Declare variables for state 0 + 1 = state 1
   * boolean p001, p011, p101, p111;
   * m000 => (p001 <=> not(p000)) ^      false // check for adjacent tiles
   *         (p011 <=> not(p010)) ^
   *         (p101 <=> not(p100)) ^
   *         (p111 <=> p110)             // <- modify this (test for corner cases)
   *
   * // don't have to input into SAT solver
   * m010 => (p001 <=> not(p000)) ^
   *         (p011 <=> not(p010)) ^
   *         (p101 <=> not(p100)) ^
   *         (p111 <=> not(p110))
   *
   * m100 => (p001 <=> not(p000)) ^
   *         (p011 <=> not(p010)) ^
   *         (p101 <=> not(p100)) ^
   *         (p111 <=> not(p110))
   *
   * m110 => (p001 <=> not(p000)) ^
   *         (p011 <=> not(p010)) ^
   *         (p101 <=> not(p100)) ^
   *         (p111 <=> not(p110))
   * Case 1:
   * false false   ->  true  true     -> false
   * false false       true  false
   *
   *
   * (-m000 v p001 v not(p000)) v (-m000 v p000 v not(p001)) ^
   * (-m000 v p011 v not(p010)) ^ (-m000 v p11 v not(p010))) ^
   * (-m000 v p001 v not(p000)) ^ (-m000 v p000 v not(p001)) ^
   * (-m000 v p001 v not(p000)) ^ (-m000 v p000 v not(p001))
   *
   * SAT:
   *
   *
   * Stage1 (click on p001)
   *
   *
   *
   *
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
   *

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
/*
  /**
   *          col0    col1    col2
   * row 0     1        2       3
   * row 1     4        5       6
   * row 2     7        8       9
   *
   *
   * @param row
   * @param col
   * @return
   */
  // replace n
  String createSAT(int row, int col, String n){
    String result;

    result = n + "";
      // -q1 ^ (change state??) ^

    return "";
  }



  public static void main(String[] args) throws TimeoutException {
    ISolver satSolver = SolverFactory.newDefault();


    satSolver.setTimeout(3600); // 1 hour timeout
    Reader reader = new DimacsReader(satSolver);
    PrintWriter out = new PrintWriter(System.out, true);
    try {
      int[] row1 = new int[] {-0001, 101};
      int[] row2 = new int[] {-101, 0001};
      int[] row3 = new int[] {-001, -1001};  // p v (a ^ b)
      int[] row4 = new int[] {101, 0001};

      /*

       * (-m000 v p001 v not(p000)) v (-m000 v p000 v not(p001)) ^
       * (-m000 v p011 v not(p010)) ^ (-m000 v p11 v not(p010))) ^
       * (-m000 v p001 v not(p000)) ^ (-m000 v p000 v not(p001)) ^
       * (-m000 v p001 v not(p000)) ^ (-m000 v p000 v not(p001))
       */
      IVecInt literal1 = new VecInt(row1);
      IVecInt literal2 = new VecInt(row2);
      IVecInt literal3 = new VecInt(row3);
      IVecInt literal4 = new VecInt(row4);


      satSolver.addClause(literal1);
      satSolver.addClause(literal2);
      satSolver.addClause(literal3);
      satSolver.addClause(literal4);

      if (satSolver.isSatisfiable()) {
        System.out.println("Satisfiable !");
        reader.decode(satSolver.model(), out);
      } else {
        System.out.println("Unsatisfiable !");
      }
  } catch (ContradictionException e) {
      e.printStackTrace();
    }
  }
}

/**
 *
 *
 *
 *
 *
 *
 */
