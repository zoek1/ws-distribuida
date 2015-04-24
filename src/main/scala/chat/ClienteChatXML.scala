import java.io._
import java.net._
import java.util._


object ClienteChatXML{

	def main(args: Array[String]): Unit = {
		try{
      val (op, a, b) = this.getDatosConsola
			if (op == "salir")  return
      else { println(ClienteChatXML.sendDatos(op, a, b))
				main(args)
			}
    }catch { case e: Exception => println("error: "+e) }
	}


	def sendDatos(op: String, a: String, b: String): String = {

		val url = new URL("http://localhost:9086/calculadora/server?wsdl")
		val connection = url.openConnection() match {
			case d: HttpURLConnection => d
			case _ => throw new InvalidTypeChat("Tipo de datos invalido")
		}
		connection.setRequestMethod("POST")
		connection.setDoOutput(true)
		connection.setDoInput(true)
		connection.setRequestProperty("Content-type","text/xml")
		connection.setRequestProperty("SOAPAction","\"http://calculadora/\"")

		val mensaje =
			"<?xml version=\"1.0\" ?>"+
			"<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:calculadora=\"http://Chat/\">" +
			"		<S:Body>" +
			"			<ns2:"+ op + " xmlns:ns2=\"http://Chat/\">" +
			"				<arg0>" +a +"</arg0><arg1>"+b+"</arg1>" +
			"			</ns2:"+ op+ ">" +
			"		</S:Body>"+
			"	</S:Envelope>"
     println(mensaje)
		val bytes = mensaje.getBytes();
		connection.setRequestProperty("Content-length", String.valueOf(bytes.length));
		val out = connection.getOutputStream()
		out.write(bytes)
		out.close()
		val rd = new BufferedReader(new InputStreamReader(connection.getInputStream()))
		val respuesta = new StringBuffer()

		var linea: Option[String] = Some("")
		for (  linea<-Option(rd.readLine())
		       if linea != "") {
			respuesta.append(linea + "\n");
		}
		rd.close()

		respuesta.toString
	}

	def getDatosConsola(): (String, String, String)	= {
		println("Chat Usando Web Service ...")
		println("¿Que Accion Desea realizar?\n")
		println("1. enviar")
		println("2. recibir")
		val str=readLine()
		var a = ""
		var b = ""
    str match {
			case "enviar" =>
		println("Ingresa el destinatario:" );
		a=readLine()
		println("Ingresa el mensaje:" );
		b=readLine()
		case "recibir" =>
		println("Ingresa tu nombre de usuario:" );
		a=readLine()
	}
	(str, a, b)
	}
}


class InvalidTypeChat(d: String) extends Exception
