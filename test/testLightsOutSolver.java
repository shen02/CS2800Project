import static org.junit.Assert.assertEquals;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import org.junit.Test;

/**
 * Tests for the class LightsOutSolver.
 */
public class testLightsOutSolver {

  LightsOutSolver solver = new LightsOutSolver();
  Context ctx = new Context();
  BoolExpr a = ctx.mkBoolConst("A");
  BoolExpr b = ctx.mkBoolConst("B");
  BoolExpr c = ctx.mkBoolConst("C");
  BoolExpr d = ctx.mkBoolConst("D");
  BoolExpr e = ctx.mkBoolConst("D");
  BoolExpr f = ctx.mkBoolConst("F");
  BoolExpr g = ctx.mkBoolConst("G");
  BoolExpr h = ctx.mkBoolConst("H");
  BoolExpr i = ctx.mkBoolConst("I");

  BoolExpr ff = ctx.mkFalse();
  BoolExpr tt = ctx.mkTrue();
  BoolExpr[][][] grid = {{{ff, ff, ff}, {ff, ff, ff}, {ff, ff, ff}},
      {{a, b, c}, {d, e, f}, {g, h, i}}};

  /**
   * Test that inputting a negative value for k into solver.SAT() will result in an
   * IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSATNegativeK() {
    solver.sat(ctx, 2, -1);
  }

  /**
   * Test that inputting a negative value for n into solver.SAT() will result in an
   * IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSATNegativeN() {
    solver.sat(ctx, -2, 1);
  }

  /**
   * Test that inputting zero for k into solver.SAT() will result in an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSAZeroK() {
    solver.sat(ctx, -2, 0);
  }

  /**
   * Test that inputting zero for n into solver.SAT() will result in an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSAZeroN() {
    solver.sat(ctx, 0, 3);
  }

  /**
   * Test for the method mkUnflipped.
   */
  @Test
  public void testMkUnflipped() {
    BoolExpr corner = solver.mkUnflipped(1, 0, 0, ctx, grid);
    assertEquals(corner, ctx.mkAnd(ctx.mkIff(i, ff),
        ctx.mkIff(h, ff),
        ctx.mkIff(g, ff),
        ctx.mkIff(f, ff),
        ctx.mkIff(d, ff),
        ctx.mkIff(c, ff), tt));
    BoolExpr edge = solver.mkUnflipped(1, 1, 0, ctx, grid);
    assertEquals(edge, ctx.mkAnd(ctx.mkIff(i, ff),
        ctx.mkIff(h, ff),
        ctx.mkIff(f, ff),
        ctx.mkIff(c, ff),
        ctx.mkIff(b, ff), tt));
    BoolExpr center = solver.mkUnflipped(1, 1, 1, ctx, grid);
    assertEquals(center, ctx.mkAnd(ctx.mkIff(i, ff),
        ctx.mkIff(g, ff),
        ctx.mkIff(c, ff),
        ctx.mkIff(a, ff), tt));
  }
}
