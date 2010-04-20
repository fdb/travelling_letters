package travelling

/**
 * A bounding rectangle
 */

class Rect(val x: Float, val y: Float, val width: Float, val height: Float) {
  def intersects(v: Vec) = {
    v.x >= x && v.y >= y && v.x <= x + width && v.y <= y + height
  }

}