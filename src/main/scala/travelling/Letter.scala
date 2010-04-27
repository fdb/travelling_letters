package travelling

import java.util.Properties
import java.io._
import scala.collection.mutable.Map
import scala.collection.JavaConversions._

object Letter {
  val letters: Map[String, Letter] = Map()
  val DEFAULT_LETTER = new Letter("\0", new Polygon(List(Vec(0, 0), Vec(40, 0), Vec(40, 100), Vec(0, 100), Vec(0, 0))))

  def exists(character: String) = letters.contains(character)

  def apply(character: String) = letters.getOrElse(character, DEFAULT_LETTER)

  def parse() {
    try {
      letters.clear
      val properties = new Properties
      properties.load(new FileInputStream(Letters.lettersFile))
      for (entity <- properties.keys()) {
        val character = Entities.unEscape(entity.toString)
        val s = properties.get(entity.toString)
        val letter = parse(character.toString, s.toString)
        letters.put(character.toString, letter)
      }

    } catch {
      case ex: IOException => {
        println("Error parsing properties" + ex.getMessage())
      }
    }
  }

  def parse(character: String, s: String): Letter = {
    val points = s.split(" ").toList.map(Vec(_))
    val poly = new Polygon(points)
    //new Letter(character, poly.resampledByAmount(100))
    new Letter(character, poly)
  }

  def save() {
    try {
      val properties = new Properties
      for ((c, letter) <- letters) {
        val entity = Entities.escape(letter.character(0))
        val s = letter.toPropertiesString
        properties.put(entity, s)
      }
      properties.store(new FileOutputStream(Letters.lettersFile), "")
    } catch {
      case ex: IOException => {
        println("Error saving properties" + ex.getMessage())
      }
    }
  }
}

class Letter(val character: String, var shape: Polygon) {
  def replacePoint(index: Int, newPoint: Vec) {
    var newPoints: List[Vec] = List()
    for (i <- 0 until shape.points.size) {
      var point = shape.points(i)
      if (i == index) point = newPoint
      newPoints = newPoints ::: List(point)
    }
    shape = new Polygon(newPoints)
  }

  def removePoint(index: Int) {
    var newPoints: List[Vec] = List()
    for (i <- 0 until shape.points.size) {
      var point = shape.points(i)
      if (i != index)
        newPoints = newPoints ::: List(point)
    }
    shape = new Polygon(newPoints)
  }

  def splitSegment(index: Int) {
    var newPoints: List[Vec] = List()
    for (i <- 0 until shape.points.size) {
      val point = shape.points(i)
      if (i == index) {
        val nextPoint = shape.points(i + 1)
        val middlePoint = Vec.linePoint(0.5f, point.x, point.y, nextPoint.x, nextPoint.y)
        newPoints = newPoints ::: List(point)
        newPoints = newPoints ::: List(middlePoint)
      } else {
        newPoints = newPoints ::: List(point)
      }
    }
    shape = new Polygon(newPoints)
  }

  def translate(offset: Vec) {
    var newPoints: List[Vec] = List()
    for (p <- shape.points) {
      newPoints = newPoints ::: List(p + offset)
    }
    shape = new Polygon(newPoints)
  }

  def toPropertiesString() = {
    val pointLetters = shape.points.map(p => "%.0f,%.0f".format(p.x, p.y))
    pointLetters.mkString(" ")
  }

  override def toString = character

}