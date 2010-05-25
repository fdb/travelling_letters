package travelling

import scala.math.random

class FastAttract(override val system: ParticleSystem) extends Behavior(system) {
  var weight = 1.0f

  override def apply(p: Particle, dt: Float) {
    val mid = (p.target - p.pos) / 5
    p.pos += mid // + Vec(random.toFloat * 5f, random.toFloat * 5f)
  }
}