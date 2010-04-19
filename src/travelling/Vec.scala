package travelling

import scala.math.sqrt
import scala.math.random
import scala.math.abs

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
