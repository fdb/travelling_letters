package travelling

import processing.core._
import PConstants._
import scala.collection.mutable.Map
import io.{Codec, Source}

/**
 * Allows you to read a text by morphing the letters.
 */
class ReaderTool(override val p: ToolContainer) extends Tool(p) {
  val text = Source.fromPath("text.txt")(Codec.UTF8).mkString
  val initials = Letters.supportedCharacters
  val players = Map[String, LetterFlock]()
  val system = new ParticleSystem(p.width, p.height)
  val containBehavior = new Contain(system)
  containBehavior.max = Vec(1600, 1200)
  val attractorBehavior = new FastAttract(system)
  var cursor = -1
  var cursorCountdown = 0
  val startOffset = Vec(50, 240)
  var offset = startOffset
  var offsets = Map[String, Vec]()
  var trailBuffer: PGraphics = null
  var hueShift: Float = 0f
  val CURSOR_TIME = 1
  var resetting = false
  val globalScale = 0.5f

  override def name = "Reader"

  override def setup() {
    var shelfOffset = Vec(50, 60)
    for (c <- initials) {
      players.put(c.toString, createLetterFlock(c.toString, shelfOffset))
      offsets.put(c.toString, shelfOffset)
      shelfOffset += Vec(50, 0)
      if (shelfOffset.x > p.width / globalScale - 100) {
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
    p.rect(0, 0, p.width, 100)

    // Drawing style for letters
    p.noFill()
    p.stroke(255)
    p.strokeWeight(4f)

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
        offset = startOffset
        resetting = true
      } else {
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
      offset = startOffset
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
    // Reset the age when the letter is used. 
    flock.age = 0
    val letter = flock.letter
    for (i <- 0 until letter.shape.size) {
      val part = flock.particles(i)
      part.target = letter.shape.points(i) + offset
    }
    val previousOffset = offsets(letter.character)
    val delta = offset - previousOffset
    val mid = previousOffset + delta / 2
    val droop = 30f

    trailBuffer.beginDraw()
    trailBuffer.smooth()
    trailBuffer.fill(60, 20)
    trailBuffer.noStroke()
    trailBuffer.rect(0, 0, p.width, p.height)
    trailBuffer.noFill()
    val (r, g, b) = ColorUtils.HSBtoRGB(hueShift, 0.5f, 0.7f)
    trailBuffer.stroke(r, g, b)
    trailBuffer.strokeWeight(0.9f)
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
    offsets.put(letter.character, offset)
    // Shift the hue color
    hueShift += 0.1f
    if (hueShift > 1.0f) {
      hueShift = 0f
    }
  }

  def updateFlockAge(flock: Flock) {
    flock.age += 1
    if (flock.age > CURSOR_TIME * 50) {
      flock.asInstanceOf[LetterFlock].moveBack()
    }
  }

  def drawFlock(flock: Flock) {
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