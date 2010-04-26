package travelling

class AttractToTarget(override val system: ParticleSystem) extends Behavior(system) {
  var weight = 1.0f

  override def apply(p: Particle, dt: Float) {
    val distanceSquared = p.pos.distanceSquared(p.target)
    var tmp = p.target - p.pos
    tmp = tmp.normalize
    tmp = tmp * weight

    p.steer = p.steer + tmp
  }
}