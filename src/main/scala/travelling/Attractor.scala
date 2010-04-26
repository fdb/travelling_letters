package travelling

class Attractor(override val system: ParticleSystem) extends Behavior(system) {
	
	var position = Vec()
	var weight = 0.1f

	override def apply(p: Particle, dt:Float) {
		val distanceSquared = p.pos.distanceSquared(position)
		var tmp = position - p.pos
		tmp = tmp.normalize
		tmp = tmp * weight
		p.steer = p.steer +  tmp
	}

}