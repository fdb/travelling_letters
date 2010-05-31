package travelling

import processing.core._
import PConstants._
import java.awt.event.{KeyEvent, MouseEvent}
import scala.collection.mutable.Map

/**
 * Type your own text and see the letters move.
 */
class TypeTool(override val p: ToolContainer) extends Tool(p) {
  val initials = Letters.supportedCharacters
  val players = Map[String, LetterFlock]()
  val system = new ParticleSystem(800, 600)
  val containBehavior = new Contain(system)
  containBehavior.max = Vec(1600, 1200)
  val attractorBehavior = new FastAttract(system)
  var cursor = false
  var cursorCountdown = 0
  var offset = Vec(50, 50)

  override def name = "Typer"

  override def setup() {
    for (c <- initials) {
      players.put(c.toString, createLetter(c.toString, Vec()))
    }
  }

  override def draw() {
    system.update(0.1f)
    updateCursor()

    p.background(90)
    p.noFill()
    p.stroke(255)
    p.strokeWeight(3f)

    p.pushMatrix()
    p.scale(0.5f)
    system.flocks.foreach(drawFlock)
    if (cursor) {
      p.fill(150)
      p.noStroke()
      p.rect(offset.x, offset.y, 1, 100)
    }
    p.popMatrix()
  }

  def updateCursor() {
    cursorCountdown -= 1
    if (cursorCountdown <= 0) {
      cursor = !cursor
      cursorCountdown = 30
    }
  }

  override def keyTyped(e: KeyEvent) {
    val cursorChar = e.getKeyChar.toString
    if (players.contains(cursorChar)) {
      val letter = players(cursorChar)
      targetFlock(letter, offset)
    }
    advanceOffset()
  }

  def advanceOffset() {
    offset += Vec(70, 0)
    if (offset.x > 1500) {
      offset = Vec(50, offset.y + 120)
    }
    if (offset.y > 1100) {
      offset = Vec(50, 50)
    }
  }

  def createLetter(character: String, offset: Vec) = {
    val letter = Letter(character)
    val poly = letter.shape
    val customLetter = new Letter(letter.character, poly)
    val flock = new LetterFlock(system, customLetter, offset)
    flock.behaviors = List(attractorBehavior, containBehavior)
    system.flocks = system.flocks.toList ::: List(flock)
    flock
  }

  def targetFlock(flock: LetterFlock, offset: Vec) {
    val letter = flock.letter
    for (i <- 0 until letter.shape.size) {
      val part = flock.particles(i)
      part.target = letter.shape.points(i) + offset
    }
  }

  def drawFlock(flock: Flock) {
    p.beginShape()
    for (prt <- flock.particles) {
      p.vertex(prt.pos.x, prt.pos.y)
    }
    p.endShape()
  }

}