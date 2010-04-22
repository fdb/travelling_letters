package travelling

import org.junit.Test
import org.junit.Assert._


class LetterTest {

	@Test
	def splitSegment {
		val p = new Polygon(List(Vec(0, 0), Vec(0, 100)))
		val letter = new Letter("I", p)
		letter.splitSegment(0)
		assertEquals(3, letter.shape.points.size)
		assertEquals(2, letter.shape.segments.size)
		assertEquals((Vec(0, 0), Vec(0, 50)), letter.shape.segments(0))
		assertEquals((Vec(0, 50), Vec(0, 100)), letter.shape.segments(1))
	}
	
}