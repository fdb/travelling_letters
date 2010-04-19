package travelling

class ParticleSystem(val width: Float, val height: Float) {

	var timeStep = 1
	var particles : Seq[Particle]= List()
	var behaviors: Seq[Behavior] = List()
  var friction = 0.97f
	
	def createParticles(amount:Int) {
//		for (i <- 0 to amount) {
//			particles = List(new Particle(this)) ::: particles
//		}
	}
	
	def update(dt : Float) {
		for (b <- behaviors) {
			particles foreach (b.apply(_, dt))
		}
		particles foreach (_.update(dt))
	}
}