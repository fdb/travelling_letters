package travelling

/**
 * A bounding rectangle
 */
class Rect(val x: Float, val y: Float, val width: Float, val height: Float) {
  def intersects(v: Vec) = {
    v.x >= x && v.y >= y && v.x <= x + width && v.y <= y + height
  }

}

object Rect {
  def apply(x:Float, y:Float, width:Float, height:Float) = new Rect(x, y, width, height)
}