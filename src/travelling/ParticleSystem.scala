package travelling

class ParticleSystem(val width: Float, val height: Float) {

	var timeStep = 1
  var flocks : Seq[Flock] = List()
  var friction = 0.97f
	
	def createParticles(amount:Int) {
//		for (i <- 0 to amount) {
//			particles = List(new Particle(this)) ::: particles
//		}
	}
	
	def update(dt : Float) {
    flocks.foreach(_.update(dt))
	}
}