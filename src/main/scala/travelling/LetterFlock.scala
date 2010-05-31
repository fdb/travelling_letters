package travelling

/**
 * A collection of all the particles that make up a letter.
 */

class LetterFlock(override val system: ParticleSystem, val letter: Letter, val offset: Vec) extends Flock(system) {
  particles = letter.shape.points.map(pt => {
    val p = new Particle(this)
    p.pos = pt * 0.2f + offset
    p.target = p.pos
    p
  })

  def moveBack() {
    var i = 0
    letter.shape.points.map(pt => {
      val p = particles(i)
      p.target.x = pt.x * 0.2f + offset.x
      p.target.y = pt.y * 0.2f + offset.y
      // p.target = pt * 0.2f + offset
      i += 1
    })
    age = 0
  }

}