package travelling

import processing.core._
import PConstants._
import java.awt.event.{KeyEvent, MouseEvent}
import scala.collection.mutable.Map

/**
 * Small app to draw letters
 */
class LetterEditorTool(override val p: ToolContainer) extends Tool(p) {
  val HANDLE_SIZE = 2
  val GRID_SIZE = 10
  val LETTER_SIZE = 100f
  val DOCK_SCALE = 0.3f
  val DOCK_HORIZONTAL_MARGIN = 25
  val DOCK_VERTICAL_MARGIN = 100
  val DOCK_POSITION = 500
  val dockPositions: Map[Letter, Rect] = Map()

  Letter.parse
  val offset = Vec(100, 100)
  var currentLetter: Letter = Letter("L")
  var sortedKeys = Letter.letters.keys.toList.sorted  
  var draggingIndex: Int = -1

  override def name() = "Letter Editor"

  override def draw {
    p.background(50)
    drawGrid

    p.pushMatrix
    p.translate(offset.x, offset.y)
    drawGuides
    drawLetterBlock
    drawCurrentLetter
    drawHandles
    drawClosestSegment
    p.popMatrix

    drawLetterDock
    drawHelp
  }

  def reloadLetters {
    Letter.parse
    currentLetter = Letter(currentLetter.character)
    sortedKeys = Letter.letters.keys.toList.sorted
  }

  def drawGrid {
    p.noFill
    p.stroke(64)
    for (y <- 0 until (p.height, GRID_SIZE)) {
      p.line(0, y, p.width, y)
    }
    for (x <- 0 until (p.width, GRID_SIZE)) {
      p.line(x, 0, x, p.height)
    }
  }

  def drawGuides {
    p.noFill
    p.stroke(100)
    p.line(-offset.x, 30, p.width+offset.x, 30)
  }

  def drawLetterBlock {
    p.stroke(90)
    p.noFill
    p.rect(0, 0, LETTER_SIZE, LETTER_SIZE)
  }

  def drawCurrentLetter {
    drawLetter(currentLetter, true)
  }

  def drawLetter(letter: Letter, numbered: Boolean = false) {
    p.noFill
    p.stroke(255)
    p.beginShape
    for (v <- letter.shape.points)
      p.vertex(v.x, v.y)
    p.endShape()
    if (numbered) {
      p.noStroke
      p.fill(255)
      var i = 0
      for (v <- letter.shape.points) {
        p.text("" + i, v.x + 4, v.y - 4)
        i += 1
      }
    }
  }

  def drawHandles {
    p.fill(0, 0, 180)
    p.noStroke
    p.beginShape(QUADS)
    for (v <- currentLetter.shape.points)
      drawHandleQuad(v)
    p.endShape
  }

  def drawHandleQuad(v: Vec) {
    p.vertex(v.x - HANDLE_SIZE, v.y - HANDLE_SIZE)
    p.vertex(v.x + HANDLE_SIZE, v.y - HANDLE_SIZE)
    p.vertex(v.x + HANDLE_SIZE, v.y + HANDLE_SIZE)
    p.vertex(v.x - HANDLE_SIZE, v.y + HANDLE_SIZE)
  }

  def drawClosestSegment() {
    val mousePoint = Vec(p.mouseX, p.mouseY) - offset
    val hi = handleIndex(mousePoint)
    if (hi >= 0) {
      p.fill(255, 0, 0)
      p.noStroke
      val pt = currentLetter.shape.points(hi)
      p.rect(pt.x - HANDLE_SIZE, pt.y - HANDLE_SIZE, HANDLE_SIZE * 2, HANDLE_SIZE * 2)
    } else {
      val segmentIndex = currentLetter.shape.segmentForPoint(mousePoint)
      if (segmentIndex < 0) return
      p.noFill
      p.stroke(255, 0, 0)
      val (a, b) = currentLetter.shape.segments(segmentIndex)
      p.line(a.x, a.y, b.x, b.y)
    }
  }

  def drawLetterDock() {
    p.noStroke
    p.pushMatrix
    p.translate(DOCK_POSITION, 0)
    p.fill(20)
    p.rect(0, 0, 1, p.height)
    p.fill(30)
    p.rect(1, 0, 1, p.height)
    p.fill(40)
    p.rect(2, 0, p.width-DOCK_POSITION, p.height)
    p.translate(DOCK_HORIZONTAL_MARGIN, 30)
    p.scale(DOCK_SCALE)

    p.pushMatrix
    for (c <- sortedKeys) {
      val letter = Letter(c)
      if (letter == currentLetter) {
        p.fill(30, 30, 50)
        p.stroke(150)
        p.rect(-20, -20, 140, 170)
      }
      drawLetterLabel(c)
      drawLetter(letter)
      dockPositions.put(letter, Rect(p.screenX(0, 0), p.screenY(0, 0), 30, 50))
      p.translate(LETTER_SIZE + DOCK_HORIZONTAL_MARGIN, 0)
      if (p.screenX(0, 0) > p.width - DOCK_HORIZONTAL_MARGIN) {
        p.popMatrix
        p.translate(0, LETTER_SIZE + DOCK_VERTICAL_MARGIN)
        p.pushMatrix
      }
    }
    p.popMatrix

    p.popMatrix
  }

  def drawHelp() {
    p.pushMatrix
    p.translate(DOCK_POSITION - 210, 30)
    p.fill(0, 100)
    p.noStroke
    p.rect(0, 0, 200, 60)
    p.fill(255)
    p.textAlign(LEFT)
    p.text("Current letter: " + currentLetter.character, 10, 20)
    p.text("r: reload", 10, 40)
    p.popMatrix
  }

  def drawLetterLabel(c: String) {
    p.fill(255)
    p.textAlign(CENTER)
    p.pushMatrix
    p.translate(30, 140)
    p.scale(3)
    p.text(c, 0, 0)
    p.popMatrix
  }

  def handleRect(v: Vec) = new Rect(v.x - HANDLE_SIZE, v.y - HANDLE_SIZE, HANDLE_SIZE * 2, HANDLE_SIZE * 2)

  def snap(v: Vec) = Vec(
    (v.x / GRID_SIZE).round * GRID_SIZE,
    (v.y / GRID_SIZE).round * GRID_SIZE)

  override def mousePressed(e: MouseEvent) {
    if (e.getX() < DOCK_POSITION) {
      val mouseVec = Vec(e.getX(), e.getY()) - offset
      draggingIndex = handleIndex(mouseVec)
      if (e.getClickCount == 2) {
        if (draggingIndex >= 0) {
          currentLetter.removePoint(draggingIndex)
        } else {
          val segmentIndex = currentLetter.shape.segmentForPoint(mouseVec)
          if (segmentIndex >= 0)
            currentLetter.splitSegment(segmentIndex)
        }
      }
    } else {
      val mouseVec = Vec(e.getX(), e.getY())
      val clickedLetters = dockPositions.filter(item => item._2.intersects(mouseVec))
      if (!clickedLetters.isEmpty) {
        currentLetter = clickedLetters.keys.head
      }
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
      case _ => {}
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
}