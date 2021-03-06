package travelling

import processing.core._
import PConstants._
import scala.collection.mutable.Map
import io.{Codec, Source}
import scala.math.{log, random, min}

/**
 * Allows you to read a text by morphing the letters.
 */
class ReaderTool(override val p: ToolContainer) extends Tool(p) {
  val text = Source.fromFile("text.txt")(Codec.UTF8).mkString
  val initials = Letters.supportedCharacters
  val players = Map[String, LetterFlock]()
  val system = new ParticleSystem(p.width, p.height)
  val attractorBehavior = new FastAttract(system)
  var cursor = -1
  val CURSOR_TIME = Settings("reader.cursorTime").toInt
  val RANDOM_OFFSET_OVER_TIME = Settings("reader.randomOffsetOverTime").toFloat
  val MOVE_BACK_TIME = Settings("reader.moveBackTime").toInt
  var cursorCountdown = CURSOR_TIME
  val startOffset = Vec(50, 240)
  var offset = Vec(startOffset)
  var offsets = Map[String, Vec]()
  var trailBuffer: PGraphics = null
  var trailImage: PImage = null
  var hueShift: Float = 0f
  var resetting = false
  val globalScale = 0.5f
  val frequencies = Map[String, Int]()
  var randomHueOffset = random.toFloat

  override def name = "Reader"

  override def setup() {
    var shelfOffset = Vec(50, 60)
    for (c <- initials) {
      players.put(c.toString, createLetterFlock(c.toString, Vec(shelfOffset)))
      shelfOffset += Vec(50, 0)
      if (shelfOffset.x > p.width / globalScale - 100) {
        shelfOffset = Vec(50, shelfOffset.y + 50)
      }
    }
    setOffsets()
    trailBuffer = p.createGraphics(p.width, p.height, JAVA2D)
    trailImage = trailBuffer.get
  }

  def setOffsets() {
    var shelfOffset = Vec(50, 60)
    for (c <- initials) {
      offsets.put(c.toString, Vec(shelfOffset))
      shelfOffset += Vec(50, 0)
      if (shelfOffset.x > p.width / globalScale - 100) {
        shelfOffset = Vec(50, shelfOffset.y + 50)
      }
    }
  }

  override def draw() {
    system.update(0.1f)

    // Draw trails
    p.image(trailImage, 0, 0)

    // Draw shelf
    p.fill(40)
    p.rect(0, 0, p.width, 100)

    // Drawing style for letters
    p.noFill()
    p.stroke(255)
    p.strokeWeight(5f)

    p.pushMatrix()
    p.scale(globalScale)
    system.flocks.foreach(updateFlockAge)
    system.flocks.foreach(drawFlock)
    p.popMatrix()

    if (advanceCursor) {
      val cursorChar = text(cursor).toString
      if (cursorChar == "\n") {
        system.flocks.foreach(flock => flock.asInstanceOf[LetterFlock].moveBack())
        cursorCountdown = CURSOR_TIME * 5
        offset.setTo(startOffset)
        randomHueOffset = random.toFloat
        setOffsets()
        resetting = true
      } else {
        if (resetting) {
          // Reset all frequencies
          frequencies.clear()
        }
        resetting = false
        if (players.contains(cursorChar)) {
          val letterFlock = players(cursorChar)
          //letterFlock.letter.shape = createLetterShape(cursorChar)
          targetFlock(letterFlock, offset)
        }
        advanceOffset()
      }
    }

    if (resetting) {
      trailBuffer.beginDraw()
      trailBuffer.smooth()
      trailBuffer.fill(60, 50)
      trailBuffer.noStroke()
      trailBuffer.rect(0, 0, p.width, p.height)
      trailBuffer.endDraw()
      trailImage = trailBuffer
    }
  }

  def advanceCursor = {
    cursorCountdown -= 1
    if (cursorCountdown <= 0) {
      cursorCountdown = CURSOR_TIME
      cursor += 1
      if (cursor >= text.length) {
        cursor = 0
      }
      true
    } else {
      false
    }
  }

  def advanceOffset() {
    offset += Vec(70, 0)
    if (offset.x > p.width / globalScale - 100) {
      offset = Vec(50, offset.y + 120)
    }
    if (offset.y > p.height / globalScale - 200) {
      offset = Vec(startOffset)
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
    flock.behaviors = List(attractorBehavior)
    system.flocks = system.flocks.toList ::: List(flock)
    flock
  }

  def targetFlock(flock: LetterFlock, offset: Vec) {
    // Reset the age when the letter is used.
    flock.age = 0
    val letter = flock.letter
    var frequency = frequencies.getOrElse(letter.character, 0)
    frequency += 1
    frequencies.put(letter.character, frequency)
    for (i <- 0 until letter.shape.size) {
      val part = flock.particles(i)
      val randomOffset = random.toFloat * frequency * RANDOM_OFFSET_OVER_TIME
      part.target = letter.shape.points(i) + offset + randomOffset
    }
    val previousOffset = offsets(letter.character)
    val delta = offset - previousOffset
    val mid = previousOffset + delta / 2
    val droop = 50f

    trailBuffer.beginDraw()
    trailBuffer.smooth()
    trailBuffer.noFill()

    val c = letter.character(0)
    var relativePosition = initials.indexOf(c) / initials.size.toFloat
    var hue = relativePosition + randomHueOffset
    if (hue > 1f) {
      hue -= 1f
    }
    val (r, g, b) = ColorUtils.HSBtoRGB(hue, 0.7f, 0.4f)
    trailBuffer.stroke(r, g, b)
    trailBuffer.strokeWeight(1.5f)
    trailBuffer.scale(0.5f)
    // The offsets start at the top left.
    // Most letters are 60 wide, so middle = 30.
    // The baseline is at y=100.
    trailBuffer.translate(30, 100)
    trailBuffer.beginShape()
    trailBuffer.vertex(previousOffset.x, previousOffset.y)
    trailBuffer.bezierVertex(previousOffset.x, mid.y + droop, offset.x, mid.y + droop, offset.x, offset.y)
    trailBuffer.endShape()
    trailBuffer.endDraw()
    trailImage = trailBuffer
    offsets.put(letter.character, Vec(offset))
  }

  def updateFlockAge(flock: Flock) {
    flock.age += 1
    if (flock.age > CURSOR_TIME * MOVE_BACK_TIME) {
      flock.asInstanceOf[LetterFlock].moveBack()
    }
  }

  def drawFlock(flock: Flock) {
    val letter = flock.asInstanceOf[LetterFlock].letter
    val frequency = min(frequencies.getOrElse(letter.character, 0), 150)
    p.stroke(255 - frequency)
    p.beginShape()
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
