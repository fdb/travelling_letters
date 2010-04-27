package travelling

/**
 * A mapping between characters and their serializable counterparts.
 *
 * Because a pound symbol ("#") cannot be saved in a properties file, we need to convert (escape) this symbol to
 * a usable name. We use HTML entities to represent those characters.
 */
object Entities {

  val charToName = Map(
  '"' -> "quot",
  '\'' -> "apos",
  '&' -> "amp",
  '<' -> "lt",
  '>' -> "gt",
  '!' -> "excl",
  ' ' -> "space",
  '#' -> "pound",
  '$' -> "dollar",
  '(' -> "openparen",
  ')' -> "closeparen",
  '*' -> "asterisk",
  '+' -> "plus",
  ',' -> "comma",
  '-' -> "hyphen",
  '.' -> "period",
  '/' -> "slash",
  ':' -> "colon",
  ';' -> "semicolon",
  '?' -> "question",
  '=' -> "equals",
  '@' -> "at",
  '_' -> "underscore",
  '|' -> "bar"
    )

  val nameToChar = charToName.map(_.swap)

  def escape(c: Char) = charToName.getOrElse(c, c.toString)
  def unEscape(s: String) = nameToChar.getOrElse(s, s(0))

}

