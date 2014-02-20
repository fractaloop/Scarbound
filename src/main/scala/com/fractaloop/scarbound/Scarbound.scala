package com.fractaloop.scarbound

import java.io.File
import com.fractaloop.scarbound.server.ScarboundServer

object Scarbound {
  def main(args: Array[String]) {
    val Version = getClass.getPackage.getImplementationVersion

    case class ScarboundConfig(command: String = "", configFile: Option[File] = None)

    val parser = new scopt.OptionParser[ScarboundConfig](s"java -jar scarbound-$Version.jar") {
      override def showUsageOnError = true
      head("Scarbound", Version)
      help("help") text "prints this usage text"
      cmd("server") action { (_, c) ⇒
        c.copy(command = "server") } text "Launch a Scarbound server" children(
          opt[File]('c', "config") valueName "<file>"
            action { (x, c) ⇒ c.copy(configFile = Option(x)) }
            validate { x ⇒ if (x.exists()) success else failure(s"Configuration file $x not found") }
        )
      checkConfig { c ⇒
        if (c.command.length <= 0) failure("must specify a command") else success
      }
    }

    parser.parse(args, ScarboundConfig()) map { config ⇒
      config.command match {
        case "server" ⇒
          ScarboundServer.run(config.configFile)
      }
    }

  }
}
