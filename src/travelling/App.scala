package travelling

import scala.collection.JavaConversions._


object App extends processing.core.PApplet {
  import processing.core._;

  val system = new ParticleSystem(800, 600)
  val attractorBehavior = new AttractToTarget(system)
  val containBehavior = new Contain(system)
  var letter: Letter = null

  override def setup(): Unit = {
    size(800, 600, PConstants.P2D)

    containBehavior.max = Vec(800, 600)
    system.behaviors = List(attractorBehavior, containBehavior)
    noStroke
    Letter.parse
    letter = Letter("L")

    val particles = (0 until letter.shape.size).map((i: Int) => {
      val p = new Particle(system)
      p.pos = letter.shape.points(i) + 50
      p.target = letter.shape.points(i) + Vec(200, 50)
      p
    })
    system.particles = particles

  }

  override def draw(): Unit = {
    updateParticlesTarget

    if (mousePressed) {
      shuffleParticles
    }

    background(51)
    fill(255)

    system.update(0.1f)
    system.particles foreach (drawParticle(_))

    //drawLetter(letter, 200, 200)

    val p = system.particles(0)
    text("steer: " + p.steer, 20, 20)
    text("velocity: " + p.velocity, 20, 40)
    text("velocityMax: " + p.velocityMax, 20, 60)

  }

  def updateParticlesTarget() {
    for (i <- 0 until letter.shape.size) {
      val p = system.particles(i)
      p.target = letter.shape.points(i) + Vec(mouseX, mouseY)
    }
  }

  def shuffleParticles() {
    val particles = java.util.Arrays.asList(system.particles: _*)
    java.util.Collections.shuffle(particles)
    system.particles = particles.iterator.toList
  }

  def drawParticle(p: Particle) {
    rect(p.pos.x, p.pos.y, 1, 1)
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
