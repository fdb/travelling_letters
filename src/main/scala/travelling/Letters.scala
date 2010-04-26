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

  val version: String = {
    val properties = new Properties
    properties.load(new FileInputStream("version.properties"))
    properties.getProperty("app.version")
  }
  
  val lettersFile: File = {
    val homeDirectory = new File(System.getProperty("user.home"))
	new File(homeDirectory, "Library/Application Support/Travelling Letters/letters.txt")
  }

  if (! lettersFile.exists) {
	  // Copy letters template from the application.
	  lettersFile.getParentFile.mkdirs
	  val sourceFile = new File("letters.txt")
	  lettersFile.createNewFile
	  val source = new FileInputStream(sourceFile).getChannel
	  val destination = new FileOutputStream(lettersFile).getChannel
	  destination.transferFrom(source, 0, source.size)
	  source.close
	  destination.close
  }

  Letter.parse

  val VIEWER_TOOL = new ViewerTool(this)
  val READER_TOOL = new ReaderTool(this)
  val LETTER_EDITOR_TOOL = new LetterEditorTool(this)
  tool = VIEWER_TOOL

  override def setup {
    size(800, 600, JAVA2D)
    smooth
    val pFont = createFont("Lucida Grande", 11)
    textFont(pFont)
    VIEWER_TOOL.setup
    READER_TOOL.setup
    LETTER_EDITOR_TOOL.setup
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
    if (appLabel("Main", Rect(5, 0, 50, 20))) {
      tool = VIEWER_TOOL
    }
    if (appLabel("Reader", Rect(55, 0, 50, 20))) {
      tool = READER_TOOL
    }
    if (appLabel("Editor", Rect(105, 0, 50, 20))) {
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
    shadowText(label, rect.x, rect.y + 13)
    mouseDown && rect.intersects(Vec(mouseX, mouseY))
  }

  def drawVersionNumber {
    shadowText(version, width - 50, 13)
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