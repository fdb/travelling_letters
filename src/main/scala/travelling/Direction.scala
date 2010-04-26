package travelling

class Direction(override val system: ParticleSystem) extends Behavior(system) {
	
	var direction = new Vec(1f, 1f)

	override def apply(p: Particle, dt:Float) {
		p.velocity += direction
	}

}