package travelling

/**
 * A flock is a collection of particles that behave in a similar way.
 */

class Flock(val system: ParticleSystem) {
  var particles: Seq[Particle] = List()
  var behaviors: Seq[Behavior] = List()
  var age: Float = 0

  def update(dt: Float) {
    for (b <- behaviors) {
      particles foreach (b.apply(_, dt))
    }
    particles foreach (_.update(dt))
  }

}