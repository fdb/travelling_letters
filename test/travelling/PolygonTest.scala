package travelling

import junit.framework.TestCase
import org.junit.Test
import org.junit.Assert._

class PolygonTest {

  @Test
  def testPointAt() {
    val p = new Polygon(List(Vec(0, 0), Vec(100, 0)))
    assertEquals(Vec(0, 0), p.pointAt(0f))
    assertEquals(Vec(100, 0), p.pointAt(1f))
    assertEquals(Vec(50, 0), p.pointAt(0.5f))
    assertEquals(Vec(75, 0), p.pointAt(0.75f))
    assertEquals(Vec(-100, 0), p.pointAt(-1f))
  }

}