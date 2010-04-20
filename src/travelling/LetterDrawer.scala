package travelling

import processing.core._
import PConstants._
import java.awt.event.{KeyEvent, MouseEvent}

/**
 * Small app to draw letters
 */
object LetterDrawer extends processing.core.PApplet {
  val HANDLE_SIZE = 3
  val GRID_SIZE = 10

  Letter.parse
  val offset = Vec(100, 100)
  var currentLetter: Letter = Letter("L")
  var draggingIndex: Int = -1

  override def setup {
    size(800, 600, P2D)
    smooth
  }

  override def draw {
    background(50)
    drawGrid

    pushMatrix
    translate(offset.x, offset.y)
    drawCurrentLetter
    drawHandles
    popMatrix

    drawLetterDock
  }

  def reloadLetters {
    Letter.parse
    currentLetter = Letter(currentLetter.character)
  }

  def drawGrid {
    noFill
    stroke(64)
    for (y <- 0 until (height, GRID_SIZE)) {
      line(0, y, width, y)
    }
    for (x <- 0 until (width, GRID_SIZE)) {
      line(x, 0, x, height)
    }
  }

  def drawCurrentLetter {
    drawLetter(currentLetter)
  }

  def drawLetter(letter: Letter) {
    noFill
    stroke(255)
    beginShape
    for (v <- letter.shape.points)
      vertex(v.x, v.y)
    endShape(CLOSE)
  }

  def drawHandles {
    fill(0, 0, 180)
    noStroke
    beginShape(QUADS)
    for (v <- currentLetter.shape.points)
      drawHandleQuad(v)
    endShape
  }

  def drawHandleQuad(v: Vec) {
    vertex(v.x - HANDLE_SIZE, v.y - HANDLE_SIZE)
    vertex(v.x + HANDLE_SIZE, v.y - HANDLE_SIZE)
    vertex(v.x + HANDLE_SIZE, v.y + HANDLE_SIZE)
    vertex(v.x - HANDLE_SIZE, v.y + HANDLE_SIZE)
  }

  def drawLetterDock() {
    pushMatrix
    translate(0, 500)
    fill(20)
    rect(0, 0, width, 1)
    fill(30)
    rect(0, 1, width, 1)
    fill(40)
    rect(0, 2, width, 100)
    translate(10, 10)
    scale(0.5f)
    for ((c, letter) <- Letter.letters) {
      drawLetterLabel(c)
      drawLetter(letter)
      translate(100, 0)
    }
    popMatrix
  }

  def drawLetterLabel(c: String) {
    fill(255)
    textAlign(CENTER)
    text(c, 30, 120)
  }

  def handleRect(v: Vec) = new Rect(v.x - HANDLE_SIZE, v.y - HANDLE_SIZE, HANDLE_SIZE * 2, HANDLE_SIZE * 2)

  def snap(v: Vec) = Vec(
    (v.x / GRID_SIZE).round * GRID_SIZE,
    (v.y / GRID_SIZE).round * GRID_SIZE)

  override def mousePressed(e: MouseEvent) {
    if (e.getY() < 500) {
      val mouseVec = Vec(e.getX(), e.getY()) - offset
      draggingIndex = handleIndex(mouseVec)
      if (e.getClickCount == 2) {
        if (draggingIndex >= 0) {
          currentLetter.removePoint(draggingIndex)
        } else {
          //currentLetter.insertPoint()
        }
      }
    } else {
      val letterIndex = (e.getX() - 10) / 50
      currentLetter = Letter(Letter.letters.keys.toList(letterIndex))
    }
  }

  override def mouseDragged(e: MouseEvent) {
    if (draggingIndex < 0) return
    val mouseVec = snap(Vec(e.getX(), e.getY()) - offset)
    currentLetter.replacePoint(draggingIndex, mouseVec)
  }


  override def mouseReleased(e: MouseEvent) {
    if (draggingIndex < 0) return
    Letter.save
  }


  override def keyPressed(e: KeyEvent) {
    e.getKeyChar match {
      case 'r' => reloadLetters
    }
  }

  def handleIndex(mouseVec: Vec): Int = {
    var i = 0
    for (v <- currentLetter.shape.points) {
      if (handleRect(v).intersects(mouseVec)) {
        return i
      }
      i += 1
    }
    -1
  }

  def main(args: Array[String]): Unit = {
    var frame = new javax.swing.JFrame("Letter Drawer")
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
    var applet = LetterDrawer
    frame.getContentPane().add(applet)
    frame.setSize(800, 600)
    applet.init
    frame.setVisible(true)
  }

}