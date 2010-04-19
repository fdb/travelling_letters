package travelling

class Repel(override val system:ParticleSystem) extends Behavior(system) {
	
	var weight = 0.001f

	override def apply(p: Particle, dt:Float) {
		for(other <- system.particles ) {
			var tmp = other.pos - p.pos
			tmp = tmp.normalize
			tmp = tmp * -weight
			p.steer += tmp
		}
	}
}