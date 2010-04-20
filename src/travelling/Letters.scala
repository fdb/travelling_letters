package travelling

import java.util.Properties
import java.io._
import processing.core._
import PConstants._
import java.awt.event.KeyEvent

/**
 * Main application.
 */
object Letters extends ToolContainer {
  val VIEWER_TOOL = new ViewerTool(this)
  val LETTER_EDITOR_TOOL = new LetterEditorTool(this)
  tool = VIEWER_TOOL

  val version: String = {
    val properties = new Properties
    properties.load(new FileInputStream("version.properties"))
    properties.getProperty("app.version")
  }


  override def setup {
    size(800, 600, P2D)
    smooth
    super.setup
  }

  override def draw {
    super.draw
    resetMatrix
    drawHeader
  }

  def drawHeader {
    noStroke
    fill(160)
    rect(0, 0, width, 20)
    textAlign(LEFT)
    val oldTool = tool
    if (appLabel("Main", Rect(5, 0, 100, 20))) {
      tool = VIEWER_TOOL
    }
    if (appLabel("Editor", Rect(55, 0, 100, 20))) {
      tool = LETTER_EDITOR_TOOL
    }
    drawVersionNumber
    if (oldTool != tool) {
      oldTool.toolDeactivated
      tool.toolActivated
    }
    fill(60)
    rect(0, 20, width, 1)
  }


  def appLabel(label: String, rect: Rect) = {
    shadowText(label, rect.x, rect.y + 15)
    mouseDown && rect.intersects(Vec(mouseX, mouseY))
  }

  def drawVersionNumber {
    shadowText(version, width - 50, 15)
  }

  def shadowText(txt: String, x: Float, y: Float) {
    fill(200)
    text(txt, x, y + 1)
    fill(40)
    text(txt, x, y)
  }

  def main(args: Array[String]): Unit = {
    var frame = new javax.swing.JFrame("Travelling Letters")
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
    var applet = Letters
    frame.getContentPane().add(applet)
    frame.setSize(800, 600)
    applet.init
    frame.setVisible(true)
  }
}