package travelling

import scala.math.random

class FastAttract(override val system: ParticleSystem) extends Behavior(system) {
  var weight = 1.0f

  override def apply(p: Particle, dt: Float) {
    val midX = (p.target.x - p.pos.x) / 5
    val midY = (p.target.y - p.pos.y) / 5
    //val mid = (p.target - p.pos) / 5
    //p.pos += mid // + Vec(random.toFloat * 5f, random.toFloat * 5f)
    p.pos.x += midX
    p.pos.y += midY
  }
}