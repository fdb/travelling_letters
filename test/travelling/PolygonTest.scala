package travelling

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
  
  @Test
  def testSegments {
	// Create a polygon that looks like the letter "L"
    val p = new Polygon(List(Vec(0, 0), Vec(0, 100), Vec(100, 100)))
    assertEquals(2, p.segments.size)
	assertEquals((Vec(0, 0), Vec(0, 100)) , p.segments(0))
	assertEquals((Vec(0, 100), Vec(100, 100)) , p.segments(1))
  }
  
  @Test
  def testSegmentForPoint {
	// Create a polygon that looks like the letter "L"
    val p = new Polygon(List(Vec(0, 0), Vec(0, 100), Vec(100, 100)))
    assertEquals(-1, p.segmentForPoint(Vec(999, 999)))
    // Exactly on the point
    assertEquals(0, p.segmentForPoint(Vec(0, 0)))
    assertEquals(1, p.segmentForPoint(Vec(100, 100)))
    // The first matching segment is returned.
    assertEquals(0, p.segmentForPoint(Vec(0, 100)))
    // Exactly on the segment
    assertEquals(0, p.segmentForPoint(Vec(0, 20)))
    assertEquals(1, p.segmentForPoint(Vec(20, 100)))
    // Within the threshold for the segment
    assertEquals(0, p.segmentForPoint(Vec(3, 20)))
    assertEquals(1, p.segmentForPoint(Vec(102, 102)))
    // Outside of the threshold
    assertEquals(-1, p.segmentForPoint(Vec(10, 20)))
    assertEquals(-1, p.segmentForPoint(Vec(105, 105)))
  }

  @Test
  def testShortestDistance {
    val d = Polygon.shortestDistance(Vec(1, 1), Vec(2, 2), Vec(2, 0))
    assertEquals(1.414f, d, 0.001f)
  }

}