package travelling

import java.util.Properties
import java.io._
import scala.collection.mutable.Map
import scala.collection.JavaConversions._

object Letter {
  val properties = new Properties
  val letters: Map[String, Letter] = Map()
  val LETTERS_FILE = "letters.txt"

  def apply(character: String) = letters(character)

  def parse() {
    try {
      letters.clear
      val properties = new Properties
      properties.load(new FileInputStream(LETTERS_FILE))
      for (character <- properties.keys()) {
        val s = properties.get(character.toString)
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
    //new Letter(character, poly.resampledByAmount(30))
    new Letter(character, poly)
  }

  def save() {
    try {
      val properties = new Properties
      for ((c, letter) <- letters) {
        val s = letter.toPropertiesString
        properties.put(letter.character, s)
      }
      properties.store(new FileOutputStream(LETTERS_FILE), "")
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

}