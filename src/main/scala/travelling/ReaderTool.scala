package travelling

import processing.core._
import PConstants._
import java.awt.event.{KeyEvent, MouseEvent}
import scala.collection.mutable.Map

/**
 * Allows you to read a text by morphing the letters.
 */
class ReaderTool(override val p: ToolContainer) extends Tool(p) {
  val text = """The Quick Brown Fox Jumps Over The Lazy Dog."""
  val initials = """ABCDEFGHIJKLMNOPQRSTUVWXYZ"""
  val players = Map[String, LetterFlock]()
  val system = new ParticleSystem(800, 600)
  val containBehavior = new Contain(system)
  containBehavior.max = Vec(1600, 1200)
  val attractorBehavior = new AttractToTarget(system)
  var cursor = -1
  var cursorCountdown = 0
    var offset = Vec(50, 50)

  override def name = "Reader"

  override def setup() {
    for (c <- initials) {
      players.put(c.toString, createLetter(c.toString, Vec()))
    }
  }

  override def draw() {
    system.update(0.1f)
    
    p.background(90)
    p.noFill
    p.stroke(255)

    p.pushMatrix
    p.scale(0.5f)
    system.flocks.foreach(drawFlock)
    p.popMatrix

    if (advanceCursor) {
    	val cursorChar = text(cursor).toString.toUpperCase
    	if (players.contains(cursorChar)) {
    		val letter = players(cursorChar)
    		targetFlock(letter, offset)
    	}
    	advanceOffset
    }
  }
  
  def advanceCursor = {
	  cursorCountdown -= 1
	  if (cursorCountdown <= 0) {
	 	  cursorCountdown = 10
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
    	if (offset.x > 1600) {
    		offset = Vec(50, offset.y + 120)
    	}
	   if (offset.y > 1100) {
	  	   offset = Vec(50, 50)
	   }
  }

  def createLetter(character: String, offset: Vec) = {
	  val letter = Letter(character)
	  val poly = letter.shape // letter.shape.resampledByAmount(100)
	  val customLetter = new Letter(letter.character, poly)
      val flock = new LetterFlock(system, customLetter, offset)
      flock.behaviors = List(attractorBehavior, containBehavior)
      system.flocks = system.flocks.toList ::: List(flock)
      flock
  }
  
  def targetFlock(flock: LetterFlock, offset:Vec) {
	  val letter = flock.letter
      for (i <- 0 until letter.shape.size) {
      val part = flock.particles(i)
      part.target = letter.shape.points(i) + offset
    }
  }
    
      def drawFlock(flock: Flock) {
        p.beginShape
        for (prt <- flock.particles) {
          p.vertex(prt.pos.x, prt.pos.y)
        }
        p.endShape()
      }

}