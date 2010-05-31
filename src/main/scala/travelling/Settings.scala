package travelling

import java.util.Properties
import java.io.FileInputStream

/**Application settings */
object Settings {
  val settingsFile = "settings.txt"
  val properties = new Properties
  properties.load(new FileInputStream(settingsFile))

  def apply(key: String): String = {
    properties.getProperty(key)
  }
}