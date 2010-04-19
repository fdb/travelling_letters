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

  def assertVectorEquals(v1:Vec, v2:Vec) {
    assertEquals(v1.x, v2.x, 0.00001f)
    assertEquals(v1.y, v2.y, 0.00001f)
  }

}