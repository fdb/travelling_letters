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
  var absVelocity = Vec()
  var tmpFloat: Float = 0f
  //var lifeTime = 10 * 1000f

  def system() = flock.system

  def update(dt: Float) {
    age += dt
    updatePosition(dt)
  }

  def updatePosition(dt: Float) {
    //steer = steer.clamp(steerMax)
    steer.clampIt(steerMax)
    //velocity = velocity + steer
    //velocity = velocity.clamp(velocityMax)
    velocity += steer
    velocity.clampIt(velocityMax)

    tmpFloat = dt / system.timeStep
    absVelocity.x = velocity.x * tmpFloat
    absVelocity.y = velocity.y * tmpFloat
    //absVelocity = velocity * (dt / system.timeStep)
    pos += absVelocity

    velocity *= system.friction
    steer.nullMe()
  }

}