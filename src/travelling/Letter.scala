package travelling

import java.util.Properties
import java.io._
import scala.collection.mutable.Map
import scala.collection.JavaConversions._

object Letter {
	
	val properties =  new Properties
	val letters : Map[String, Letter] = Map()
	
	def apply(character:String) = letters(character)

	def parse() {
		 try {
			 properties.load(new FileInputStream("letters.txt"))
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
	
	def parse(character:String, s:String):Letter ={
    val points = s.split(" ").toList.map(Vec(_))
    val poly = new Polygon(points)
		new Letter(character, poly.resampledByAmount(500))
	}
}

class Letter(val character:String,val shape:Polygon) {

}