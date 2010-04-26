package travelling

/**
 * A collection of all the particles that make up a letter.
 */

class LetterFlock(override val system: ParticleSystem, val letter: Letter, val offset: Vec) extends Flock(system) {
  particles = letter.shape.points.map(pt => {
    val p = new Particle(this)
    p.pos = pt + offset
    p
  })

}