package travelling

class ParticleSystem(val width: Float, val height: Float) {

	var timeStep = 1
  var flocks : Seq[Flock] = List()
  var friction = 0.98f

	def update(dt : Float) {
    flocks.foreach(_.update(dt))
	}
}