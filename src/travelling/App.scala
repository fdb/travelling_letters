package travelling

import scala.collection.JavaConversions._
import scala.math.random

import processing.core._
import PConstants._


object App extends processing.core.PApplet {

  Letter.parse
  
  val system = new ParticleSystem(800, 600)
  val attractorBehavior = new AttractToTarget(system)
  val containBehavior = new Contain(system)
  var offset: Vec = Vec()

  override def setup(): Unit = {
    size(800, 600, P2D)
    smooth

    containBehavior.max = Vec(800, 600)
    noStroke
    createLetter("L")
    createLetter("k")
  }

  override def draw(): Unit = {

    var o = Vec()
    system.flocks.foreach((f:Flock) => {
      updateParticlesTarget(f.asInstanceOf[LetterFlock], o)
      o += Vec(100, 0)
    })
  
    if (mousePressed) {
      system.flocks.foreach(shuffleParticles)
    }

    background(51)
    fill(255)

    system.update(0.1f)

    noFill
    stroke(255)

    system.flocks.foreach(drawFlock)

    //drawLetter(letter, 200, 200)

    //val p = system.particles(0)
    //text("steer: " + p.steer, 20, 20)
    //text("velocity: " + p.velocity, 20, 40)
  }

  def createLetter(character: String) = {
    val letter = Letter(character)
    val flock = new LetterFlock(system, letter, offset)
    flock.behaviors = List(attractorBehavior, containBehavior)
    system.flocks = system.flocks.toList ::: List(flock)
    offset += Vec(100, 0)
    letter
  }

  def drawFlock(flock: Flock) {
    beginShape
    for (p <- flock.particles) {
      vertex(p.pos.x, p.pos.y)
    }
    endShape(CLOSE)
  }

  def updateParticlesTarget(flock : LetterFlock, offset: Vec) {
    val letter = flock.letter
    for (i <- 0 until letter.shape.size) {
      val p = flock.particles(i)
      p.target = letter.shape.points(i) + Vec(mouseX, mouseY) + offset
    }
  }

  def shuffleParticles(flock : Flock) {
    val particles = java.util.Arrays.asList(flock.particles: _*)
    java.util.Collections.shuffle(particles)
    flock.particles = particles.iterator.toList
  }

  def drawLetter(l: Letter, x: Float, y: Float) {
    beginShape();
    for (v <- l.shape.points) {
      vertex(v.x + x, v.y + y)
    }
    endShape();
  }

  def main(args: Array[String]): Unit = {
    var frame = new javax.swing.JFrame("Travelling Letters")
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
    var applet = App
    frame.getContentPane().add(applet)
    frame.setSize(800, 600)
    applet.init
    frame.setVisible(true)
  }
}
