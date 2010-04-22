package travelling

import scala.math.{ceil,sqrt,pow,abs}

object Polygon {
  def lineLength(x0: Float, y0: Float, x1: Float, y1: Float) = {
    val x = pow(abs(x0 - x1), 2);
    val y = pow(abs(y0 - y1), 2);
    Math.sqrt(x + y).toFloat
  }

  def linePoint(t: Float, x0: Float, y0: Float, x1: Float, y1: Float) = {
    Vec(
      x0 + t * (x1 - x0),
      y0 + t * (y1 - y0))
  }

  def shortestDistance(a: Vec, b: Vec, pt: Vec) = {
    // Calculate vector a/b
    val t = b - a
    // Length of line segment a/b
    val length = sqrt(pow(t.x,2)+pow(t.y,2)).toFloat
    // Unit vector of ab
    val uv = t / length
    // Normal unit vector ab
    val normal = Vec(-uv.y, uv.x)
    // Vector ac
    val ac = pt - a
    // Projection of ac to n (the minimum distance)
    abs(ac.x * normal.x + ac.y * normal.y)
  }
}

class Polygon(val points: Seq[Vec]) {
  var segmentLengths: List[Float] = null
  var totalLength = 0f
  var closed = false

  def size() = points.size

  private def segmentIndexForPoint(t: Float): Int = {
    ensureSegmentLengths
    var absT = t * totalLength
    var resT = t
    var segmentIndex = -1
    for (length <- segmentLengths) {
      segmentIndex += 1
      if (absT <= length || segmentIndex == segmentLengths.size - 1)
        return segmentIndex
      absT -= length
    }
    -1
  }

  def pointAt(t: Float) = {
    ensureSegmentLengths
    var absT = t * totalLength
    var resT = t
    var segmentIndex = segmentIndexForPoint(t)
    var tmpLength = 0f
    for (i <- 0 until segmentIndex) {
      tmpLength += segmentLengths(i)
    }
    resT = absT - tmpLength
    resT /= segmentLengths(segmentIndex)

    val pt1 = pointForIndex(segmentIndex + 1)
    //    if (segmentIndex == 0) {
    //      segmentIndex = points.size
    //    }
    val pt0 = pointForIndex(segmentIndex)

    Polygon.linePoint(resT, pt0.x, pt0.y, pt1.x, pt1.y)
  }

  def pointForIndex(index: Int) = {
    if (index < points.size) {
      points(index)
    } else if (closed) {
      points(0)
    } else {
      points(points.size)
    }
  }

  def segments = {
    for (i <- 1 until points.size)
      yield (points(i-1), points(i))
  }

  def segmentForPoint(pt: Vec, threshold: Float = 5) : Int = {
    var i = 0
    for (segment <- segments) {
      if (Polygon.shortestDistance(segment._1, segment._2, pt) < threshold) return i
      i += 1
    }
    return -1
  }

  def length() = {
    ensureSegmentLengths
    totalLength
  }

  def resampledByLength(segmentLength: Float) = {
    val amount = ceil(length / segmentLength).toInt
    resampledByAmount(amount)
  }

  def resampledByAmount(amount: Int) = {
    val points = makePoints(amount)
    new Polygon(points)
  }

  def makePoints(amount: Int) = {
    val delta = 1f / amount
    (0 until amount).map((i: Int) => pointAt(i * delta))
  }


  private def ensureSegmentLengths() {
    if (segmentLengths != null) return
    segmentLengths = List()
    var i = 1
    while (i < points.size) {
      val pt = points(i)
      val pt0 = points(i - 1)
      val length = Polygon.lineLength(pt0.x, pt0.y, pt.x, pt.y)
      segmentLengths = segmentLengths ::: List(length)
      totalLength += length
      i += 1
    }
    if (closed) {
      val pt0 = points(points.size - 1)
      val pt1 = points(0)
      val length = Polygon.lineLength(pt0.x, pt0.y, pt1.x, pt1.y)
      segmentLengths = segmentLengths ::: List(length)
      totalLength += length
    }

  }


}