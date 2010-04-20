package travelling

import processing.core.PApplet
import java.awt.event.{MouseEvent, KeyEvent}

/**
 * TODO: Write class documentation here
 */

class ToolContainer() extends PApplet {
  var tool: Tool = null
  var mouseDown: Boolean = false

  override def setup {tool.setup}

  override def draw {tool.draw}

  override def mousePressed(e: MouseEvent) {
    this.mouseDown = true
    tool.mousePressed(e)
  }

  override def mouseDragged(e: MouseEvent) {tool.mouseDragged(e)}

  override def mouseReleased(e: MouseEvent) {
    this.mouseDown = false
    tool.mouseReleased(e)
  }

  override def keyPressed(e: KeyEvent) {tool.keyPressed(e)}


}

object ToolContainer {
  def toolFrame[T <: Tool](toolClass: Class[T]) = {
    val container = new ToolContainer
    val constructor = toolClass.getConstructor(classOf[PApplet])
    val tool = constructor.newInstance(container)
    container.tool = tool

    val frame = new javax.swing.JFrame(tool.name)
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
    frame.getContentPane().add(container)
    frame.setSize(800, 600)

    container.init

    frame
  }
}