package travelling

import scala.math.{sqrt,random,abs,pow}

object Vec {
  val ZERO = new Vec
  val UNIT_X = new Vec(1, 0)
  val UNIT_Y = new Vec(0, 1)

  def apply() = ZERO

  def apply(v: Float) = new Vec(v, v)

  def apply(x: Float, y: Float) = new Vec(x, y)

  def apply(v: Vec) = new Vec(v.x, v.y)

  def apply(s: String) = {
    val coords = s.split(",")
    val x = java.lang.Float.parseFloat(coords(0))
    val y = java.lang.Float.parseFloat(coords(1))
    new Vec(x, y)
  }

  def random(xRange: Float, yRange: Float) = new Vec(scala.math.random.toFloat * xRange, scala.math.random.toFloat * yRange)
  

  def lineLength(x0: Float, y0: Float, x1: Float, y1: Float) = {
    val x = pow(abs(x0 - x1), 2);
    val y = pow(abs(y0 - y1), 2);
    sqrt(x + y).toFloat
  }

  def linePoint(t: Float, x0: Float, y0: Float, x1: Float, y1: Float) = {
    Vec(
      x0 + t * (x1 - x0),
      y0 + t * (y1 - y0))
  }
    
  def distance(a: Vec, b: Vec) = {
    val t = b - a
    sqrt(pow(t.x,2)+pow(t.y,2)).toFloat
  }
    
  def dot(a: Vec, b: Vec) = {
    a.x  * b.x + a.y * b.y
  }
  
  def shortestDistance(a: Vec, b: Vec, pt: Vec) = {
    // Minimum distance between line segment ab and point pt
    // Calculate vector a/b
    val t = b - a
    // |b-a|^2 -- and avoid doing a square root.
    val lengthSquared = pow(t.x, 2) + pow(t.y, 2)
    // If a == b, return the distance from endpoint
    if (lengthSquared == 0f) {
  	  Vec.distance(a, pt)
    } else {
      // Line extending the segment, a + t (b - a)
      val tmp = (dot(pt - a, t) / lengthSquared).toFloat
      if (tmp < 0) {
        // Extending beyond a
        distance(pt, a)
      } else if (tmp > 1) {
    	  // Extending beyond b
    	  distance(pt, b)
      } else {
        // Project onto the line
    	val projection = a + t * tmp
    	distance(pt, projection)
      }
    }
  }
}

class Vec(val x: Float, val y: Float) {
  def this() = this (0, 0)

  final def +(v: Float) = new Vec(x + v, y + v)

  final def -(v: Float) = new Vec(x - v, y - v)

  final def *(v: Float) = new Vec(x * v, y * v)

  final def /(v: Float) = new Vec(x / v, y / v)

  final def +(v: Vec) = new Vec(this.x + v.x, this.y + v.y)

  final def -(v: Vec) = new Vec(this.x - v.x, this.y - v.y)

  final def *(v: Vec) = new Vec(this.x * v.x, this.y * v.y)

  final def /(v: Vec) = new Vec(this.x / v.x, this.y / v.y)

  final def length = sqrt(lengthSquared).toFloat

  final def lengthSquared = x * x + y * y

  final def distance(v: Vec) = sqrt(distanceSquared(v.x, v.y)).toFloat

  final def distance(x: Float, y: Float): Float = sqrt(distanceSquared(x, y)).toFloat

  final def distanceSquared(v: Vec): Float = distanceSquared(v.x, v.y)

  final def distanceSquared(x: Float, y: Float) = {
    val dx = this.x - x
    val dy = this.y - y
    dx * dx + dy * dy
  }

  final def clamp(max: Float): Vec = {
//    val l = length
//		if(l > max) {
//		  var v = this / l
//		  v *= max
//      v
//		} else {
//		this
//  }

    val absX = abs(x)
    val absY = abs(y)
    if (absX >= absY && absX > max) {
      val newX = x / absX * max
      val newY = max / absX * y
      Vec(newX, newY)
    } else if (absY > absX && absY > max) {
      val newX = max / absY * x
      val newY = y / absY * max
      Vec(newX, newY)
    } else {
      this
    }
  }

  final def normalize = {
    val l = length
    if (l != 0)
      this / l
    else
      this / 1
  }


  override def equals(obj: Any): Boolean = {
    obj match {
      case that: Vec => obj.isInstanceOf[Vec] && x == that.x && y == that.y
      case _ => false
    }
  }

  override def hashCode = {
    var result = if (x != +0.0f) java.lang.Float.floatToIntBits(x) else 0
    result += 31 * result + (if (y != +0.0f) java.lang.Float.floatToIntBits(y) else 0)
    result
  }


  override def toString = "%.0f,%.0f".format(x, y)
}
