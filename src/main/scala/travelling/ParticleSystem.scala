package travelling

class ParticleSystem(val width: Float, val height: Float) {

	var timeStep = 0.1f
  var flocks : Seq[Flock] = List()
  var friction = 0.90f

	def update(dt : Float) {
    flocks.foreach(_.update(dt))
	}
}