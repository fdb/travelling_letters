package travelling

class Contain(override val system:ParticleSystem) extends Behavior(system) {
	
	var min = Vec()
	var max = Vec(1f, 1f)
	
	override def apply(p:Particle, dt:Float) {
		if (p.pos.x < min.x || p.pos.x > max.x) {
			p.velocity *= -1
		}
		
		if (p.pos.y < min.y || p.pos.y > max.y) {
			p.velocity *= -1
		}
		
		
	}

}