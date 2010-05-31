package travelling

import java.util.Properties
import java.io._
import java.awt._

/**
 * Main application.
 */
object Letters extends ToolContainer {
  val fullscreen = Settings("fullscreen").toBoolean
  val environment = GraphicsEnvironment.getLocalGraphicsEnvironment()
  val displayDevice = environment.getDefaultScreenDevice()
  val mode = displayDevice.getDisplayMode
  var tmpWidth = 800
  var tmpHeight = 600
  if (fullscreen) {
    tmpWidth = mode.getWidth()
    tmpHeight = mode.getHeight()
  }
  val appletWidth = tmpWidth
  val appletHeight = tmpHeight


  val supportedCharacters = """ABCDEFGHIJKLMNOPQRSTUVWXYZa\u00E0\u00E1\u00E4bcde\u00E8\u00E9\u00EBfghi\u00EFjklmno\u00F6pqrstu\u00FCvwxyz,/.?()"""

  val version: String = {
    val properties = new Properties
    properties.load(new FileInputStream("version.properties"))
    properties.getProperty("app.version")
  }

  val lettersFile: File = {
    val homeDirectory = new File(System.getProperty("user.home"))
    new File(homeDirectory, "Library/Application Support/Travelling Letters/letters.txt")
  }

  if (!lettersFile.exists) {
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
  val TYPE_TOOL = new TypeTool(this)
  val LETTER_EDITOR_TOOL = new LetterEditorTool(this)
  tool = READER_TOOL

  override def setup {
    size(appletWidth, appletHeight, "processing.core.PGraphicsJava2D") // Should be JAVA2D, but the compiler can't find it.
    smooth()
    val pFont = createFont("Lucida Grande", 11)
    textFont(pFont)
    VIEWER_TOOL.setup()
    READER_TOOL.setup()
    TYPE_TOOL.setup()
    LETTER_EDITOR_TOOL.setup()
  }

  override def draw {
    super.draw()
    resetMatrix()
    noStroke()
    fill(160)
    if (!fullscreen) {
      drawHeader()
    }
  }

  def drawHeader() {
    noStroke
    fill(160)
    rect(0, 0, width, 20)
    textAlign(37 /* LEFT */ ) // Should be LEFT, but the compiler can't find the constant.
    val oldTool = tool
    if (appLabel("View", Rect(5, 0, 50, 20))) {
      tool = VIEWER_TOOL
    }
    if (appLabel("Read", Rect(55, 0, 50, 20))) {
      tool = READER_TOOL
    }
    if (appLabel("Type", Rect(105, 0, 50, 20))) {
      tool = TYPE_TOOL
    }
    if (appLabel("Edit", Rect(155, 0, 50, 20))) {
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
    applet.init()
    if (!fullscreen) {
      frame.getContentPane().add(applet)
      frame.setSize(800, 600)
      frame.setLocationRelativeTo(null)
    } else {

      while (applet.defaultSize && !applet.finished) {
        Thread.sleep(5)
      }

      frame.setUndecorated(true)
      frame.setBackground(new Color(0, 0, 0))
      System.setProperty("apple.awt.fullscreenhidecursor","true")

      displayDevice.setFullScreenWindow(frame)
      frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH)

      val fullScreenRect = frame.getBounds() // new Rectangle(0, 0, mode.getWidth(), mode.getHeight())
      //frame.setBounds(fullScreenRect)
      frame.getContentPane().setLayout(null)
      frame.getContentPane().add(applet)
      applet.setBounds(0, 0, appletWidth, appletHeight)
      frame.invalidate()
    }
    frame.setVisible(true)
    applet.requestFocus()
  }
}