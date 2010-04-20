package travelling

import scala.collection.JavaConversions._
import scala.math.random

import processing.core._
import PConstants._
import java.awt.event.KeyEvent


class ViewerTool(override val p: ToolContainer) extends Tool(p) {
  Letter.parse

  val system = new ParticleSystem(800, 600)
  val attractorBehavior = new AttractToTarget(system)
  val containBehavior = new Contain(system)
  var offset: Vec = Vec()
  var text: String = "HELL"

  override def name() = "Viewer"

  override def setup(): Unit = {
    p.size(800, 600, P2D)
    p.smooth

    containBehavior.max = Vec(800, 600)
    p.noStroke
    for (t <- text) createLetter(t.toString)
  }

  override def draw(): Unit = {
    var o = Vec()
    system.flocks.foreach((f: Flock) => {
      updateParticlesTarget(f.asInstanceOf[LetterFlock], o)
      o += Vec(100, 0)
    })

    if (p.mouseDown) {
      system.flocks.foreach(shuffleParticles)
    }

    p.background(51)
    p.fill(255)

    system.update(0.1f)

    p.noFill
    p.stroke(255)

    system.flocks.foreach(drawFlock)

    //drawLetter(letter, 200, 200)

    //val p = system.particles(0)
    //text("steer: " + p.steer, 20, 20)
    //text("velocity: " + p.velocity, 20, 40)
  }


  override def toolActivated() {
    resetText
  }

  def resetText {
    system.flocks = List()
    offset = Vec()
    for (t <- text) createLetter(t.toString)
  }

  def createLetter(character: String) = {
    val letter = Letter(character)
    val flock = new LetterFlock(system, letter, offset)
    flock.behaviors = List(attractorBehavior, containBehavior)
    system.flocks = system.flocks.toList ::: List(flock)
    offset += Vec(100, 0)
    letter
  }


  override def keyPressed(e: KeyEvent) {
    if (e.getKeyCode == KeyEvent.VK_BACK_SPACE) {
      text = text.substring(0, text.length - 1)
      resetText
    } else {
      val character = e.getKeyChar.toString
      if (Letter.exists(character)) {
        createLetter(character)
        text += character
        resetText
      }
    }
  }

  def drawFlock(flock: Flock) {
    p.beginShape
    for (prt <- flock.particles) {
      p.vertex(prt.pos.x, prt.pos.y)
    }
    p.endShape()
  }

  def updateParticlesTarget(flock: LetterFlock, offset: Vec) {
    val letter = flock.letter
    for (i <- 0 until letter.shape.size) {
      val part = flock.particles(i)
      part.target = letter.shape.points(i) + Vec(p.mouseX, p.mouseY) + offset
    }
  }

  def shuffleParticles(flock: Flock) {
    val particles = java.util.Arrays.asList(flock.particles: _*)
    java.util.Collections.shuffle(particles)
    flock.particles = particles.iterator.toList
  }

  def drawLetter(l: Letter, x: Float, y: Float) {
    p.beginShape()
    for (v <- l.shape.points) {
      p.vertex(v.x + x, v.y + y)
    }
    p.endShape()
  }
}
