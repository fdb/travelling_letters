package travelling

import processing.core._
import PConstants._
import java.awt.event.{KeyEvent, MouseEvent}
import scala.collection.mutable.Map

/**
 * Allows you to read a text by morphing the letters.
 */
class ReaderTool(override val p: ToolContainer) extends Tool(p) {
  val text = """Travel is the movement of people between relatively distant geographical locations for any purpose and
  |any duration, with or without any means of transport. Travel also includes relatively short stays between successive
  |movements. Movements between locations requiring only a few minutes are not considered as travel. As an activity,
  |"travel" also covers all the activities performed during a travel. Travel is a wider concept than a trip. A round trip
  |is a particular type of travel whereby a person moves from his/her usual residence to one or several distant locations
  |and returns. A trip can also be part of a round trip. Travel is most commonly done for recreation or to visit friends
  |and family (generally as part of tourism), for business or for commuting; but may be for numerous other reasons, such
  |as health care, migration, fleeing war, etc. Travel may occur by walking or human-powered mode, or through mechanical
  |vehicles, either as private or public transport. Travel may be local, regional, national (domestic) or international.
  |In some countries, non-local internal travel may require an internal passport, while international travel typically
  |requires a passport and visa. """.stripMargin
  val initials = """ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,/.?()"""
  val players = Map[String, LetterFlock]()
  val system = new ParticleSystem(800, 600)
  val containBehavior = new Contain(system)
  containBehavior.max = Vec(1600, 1200)
  val attractorBehavior = new FastAttract(system)
  var cursor = -1
  var cursorCountdown = 0
  var offset = Vec(50, 180)
  var offsets = Map[String, Vec]()
  var trailBuffer: PGraphics = null

  override def name = "Reader"

  override def setup() {
    var shelfOffset = Vec(50, 60)
    for (c <- initials) {
      players.put(c.toString, createLetterFlock(c.toString, shelfOffset))
      offsets.put(c.toString, shelfOffset)
      shelfOffset += Vec(50, 0)
      if (shelfOffset.x > 1500) {
        shelfOffset = Vec(50, shelfOffset.y + 50)
      }
    }
    trailBuffer = p.createGraphics(p.width, p.height, JAVA2D)
  }

  override def draw() {
    system.update(0.1f)

    // Draw background
    p.fill(60, 200)
    p.rect(0, 0, p.width, p.height)

    // Draw trails
   p.image(trailBuffer.get(), 0, 0)

    // Draw shelf
    p.fill(40)
    p.rect(0, 0, p.width, 80)

    // Drawing style for letters
    p.noFill
    p.stroke(255)
    p.strokeWeight(5f)

    p.pushMatrix
    p.scale(0.5f)
    system.flocks.foreach(drawFlock)
    p.popMatrix

    if (advanceCursor) {
      val cursorChar = text(cursor).toString
      if (players.contains(cursorChar)) {
        val letterFlock = players(cursorChar)
        //letterFlock.letter.shape = createLetterShape(cursorChar)
        targetFlock(letterFlock, offset)
      }
      advanceOffset
    }
  }

  def advanceCursor = {
    cursorCountdown -= 1
    if (cursorCountdown <= 0) {
      cursorCountdown = 15
      cursor += 1
      if (cursor >= text.length) {
        cursor = 0
      }
      true
    } else {
      false
    }
  }

  def advanceOffset {
    offset += Vec(70, 0)
    if (offset.x > 1500) {
      offset = Vec(50, offset.y + 120)
    }
    if (offset.y > 1100) {
      offset = Vec(50, 150)
    }
  }

  def createLetterShape(character: String) = {
    Letter(character).shape
    //letter.shape.resampledByAmount(20)
  }

  def createLetterFlock(character: String, offset: Vec) = {
    val shape = createLetterShape(character)
    val letter = new Letter(character, shape)
    val flock = new LetterFlock(system, letter, offset)
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
    val previousOffset = offsets(letter.character)
    val delta = offset - previousOffset
    val mid = previousOffset + delta / 2
    val droop = 30f

    trailBuffer.beginDraw
    trailBuffer.smooth()
    trailBuffer.fill(60, 20)
    trailBuffer.noStroke
    trailBuffer.rect(0, 0, p.width, p.height)
    trailBuffer.noFill
    trailBuffer.stroke(170)
    trailBuffer.strokeWeight(0.9f)
    trailBuffer.scale(0.5f)
    // The offsets start at the top left.
    // Most letters are 60 wide, so middle = 30.
    // The baseline is at y=100. 
    trailBuffer.translate(30, 100)
    trailBuffer.beginShape
    trailBuffer.vertex(previousOffset.x, previousOffset.y)
    trailBuffer.bezierVertex(previousOffset.x, mid.y + droop, offset.x, mid.y + droop, offset.x, offset.y)
    trailBuffer.endShape
    trailBuffer.endDraw
    offsets.put(letter.character, offset)
  }

  def drawFlock(flock: Flock) {
    p.beginShape
    for (prt <- flock.particles) {
      p.vertex(prt.pos.x, prt.pos.y)
    }
    p.endShape()
  }

  def createLetterTrail(character: String, offset: Vec) = {
    val points: List[Vec] = List(offset)
    new Polygon(points)
  }

}