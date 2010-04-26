package travelling

import junit.framework.TestCase
import org.junit.Test
import org.junit.Assert._

class VecTest {

  @Test
  def testClamp() {
    val v = Vec(100, 100)
    assertVectorEquals(Vec(1, 1), Vec(100, 100).clamp(1))
    assertVectorEquals(Vec(1, 0.5f), Vec(100, 50).clamp(1))
    assertVectorEquals(Vec(1, 1), Vec(1, 1).clamp(1))
    assertVectorEquals(Vec(-1, 0.5f), Vec(-100, 50).clamp(1))
  }
  
  @Test
  def testShortestDistance {
	  val a = Vec(0, 0)
	  val b = Vec(0, 100)
	  // Distance at the edges == 0
	  assertEquals(0, Vec.shortestDistance(a, b, Vec(0, 0)), 0.001f)
	  assertEquals(0, Vec.shortestDistance(a, b, Vec(0, 100)), 0.001f)
	  // Distance on the segment == 0
	  assertEquals(0, Vec.shortestDistance(a, b, Vec(0, 50)), 0.001f)
	  // Distance perpendicular from the segment
	  assertEquals(5, Vec.shortestDistance(a, b, Vec(5, 0)), 0.001f)
	  assertEquals(5, Vec.shortestDistance(a, b, Vec(5, 100)), 0.001f)
	  assertEquals(5, Vec.shortestDistance(a, b, Vec(5, 50)), 0.001f)
	  // Distance after the edges
	  assertEquals(100, Vec.shortestDistance(a, b, Vec(0, 200)), 0.001f)
	  assertEquals(75, Vec.shortestDistance(a, b, Vec(0, -75)), 0.001f)
  }

  def assertVectorEquals(v1:Vec, v2:Vec) {
    assertEquals(v1.x, v2.x, 0.00001f)
    assertEquals(v1.y, v2.y, 0.00001f)
  }

}