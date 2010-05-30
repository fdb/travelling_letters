package travelling

import java.awt.Color

/**
 * Utilities for manipulating colors: converting HSV to RGB etc.
 */

object ColorUtils {

  /**
   * Convert hsb colors in the 0-1 range to RGB colors in the 0-255 range.
   */
  def HSBtoRGB(hue: Float, saturation: Float, brightness: Float): (Float, Float, Float) = {
    val c = new Color(Color.HSBtoRGB(hue, saturation, brightness))
    (c.getRed, c.getGreen, c.getBlue)
  }

}