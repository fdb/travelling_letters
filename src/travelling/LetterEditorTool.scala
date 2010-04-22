package travelling

import processing.core._
import PConstants._
import java.awt.event.{KeyEvent, MouseEvent}

/**
 * Small app to draw letters
 */
class LetterEditorTool(override val p: ToolContainer) extends Tool(p) {
  val HANDLE_SIZE = 3
  val GRID_SIZE = 10
  val LETTER_SIZE = 100f
  val DOCK_SCALE = 0.3f
  val DOCK_LETTER_MARGIN = 10

  Letter.parse
  val offset = Vec(100, 100)
  var currentLetter: Letter = Letter("L")
  var draggingIndex: Int = -1

  override def name() = "Letter Editor"

  override def setup {
    p.size(800, 600, P2D)
    p.smooth
  }

  override def draw {
    p.background(50)
    drawGrid

    p.pushMatrix
    p.translate(offset.x, offset.y)
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
    	p.rect(pt.x - HANDLE_SIZE, pt.y-HANDLE_SIZE, HANDLE_SIZE * 2, HANDLE_SIZE * 2)
    } else {
    	val segmentIndex = currentLetter.shape.segmentForPoint(mousePoint)
    	if (segmentIndex < 0) return
    	p.noFill
    	p.stroke(255, 0, 0)
    	val (a,b) = currentLetter.shape.segments(segmentIndex)
    	p.line(a.x, a.y, b.x, b.y)
    }
  }

  def drawLetterDock() {
    p.noStroke
    p.pushMatrix
    p.translate(0, 500)
    p.fill(20)
    p.rect(0, 0, p.width, 1)
    p.fill(30)
    p.rect(0, 1, p.width, 1)
    p.fill(40)
    p.rect(0, 2, p.width, 100)
    p.translate(DOCK_LETTER_MARGIN, 5)
    p.scale(DOCK_SCALE)
    for ((c, letter) <- Letter.letters) {
      drawLetterLabel(c)
      drawLetter(letter)
      p.translate(100, 0)
    }
    p.popMatrix
  }

  def drawHelp() {
    p.pushMatrix
    p.translate(p.width-210, 30)
    p.fill(0, 100)
    p.noStroke
    p.rect(0, 0, 200, 40)
    p.fill(255)
    p.textAlign(LEFT)
    p.text("r: reload", 10, 20)
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
    if (e.getY() < 500) {
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
      val letterIndex =  ((e.getX() - DOCK_LETTER_MARGIN) / (LETTER_SIZE * DOCK_SCALE)).toInt
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