package travelling

import scala.math.random

class Particle(val flock: Flock) {
  var pos = Vec.random(flock.system.width, flock.system.height)
  var velocity = Vec()
  var steer = Vec()
  var steerMax = 1f
  var age = 0f
  var velocityMax = 380f + (random.toFloat * 10f)
  var target = Vec()
  //var lifeTime = 10 * 1000f

  def system() = flock.system

  def update(dt: Float) {
    age += dt
    updatePosition(dt)
  }

  def updatePosition(dt: Float) {
    steer = steer.clamp(steerMax)
    velocity = velocity + steer
    velocity = velocity.clamp(velocityMax)

    val absVelocity = velocity * (dt / system.timeStep)
    pos += absVelocity

    velocity = velocity * system.friction
    steer = Vec()
  }

}