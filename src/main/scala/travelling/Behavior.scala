package travelling

class Behavior(val system: ParticleSystem) {
	
def prepare(dt: Float) {}
def apply(p: Particle, dt: Float) {}
	
}