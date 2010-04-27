package travelling

import processing.core.PApplet
import java.awt.event.{KeyEvent, MouseEvent}

/**
 * A tool provides methods
 */

abstract class Tool(val p: ToolContainer) {
  def setup() {}

  def draw()

  def name(): String

  def mousePressed(e: MouseEvent) {}

  def mouseDragged(e: MouseEvent) {}

  def mouseReleased(e: MouseEvent) {}

  def keyPressed(e: KeyEvent) {}

  def toolActivated() {
    p.resetMatrix
    p.fill(255)
    p.noStroke
    p.strokeWeight(1)
  }

  def toolDeactivated() {}

}