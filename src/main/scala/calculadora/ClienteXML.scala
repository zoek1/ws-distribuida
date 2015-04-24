import java.io._
import java.net._
import java.util._


object ClienteXML{

	def main(args: Array[String]): Unit = {
		try{
      val (op, a, b) = this.getDatosConsola
			if (op == "salir")  return
      else{
				 println(ClienteXML.sendDatos(op, a, b))
				main(args)}
    }catch { case e: Exception => println("error: "+e) }
	}


	def sendDatos(op: String, a: Double, b: Double): String = {

		val url = new URL("http://localhost:9085/calculadora/server?wsdl")
		val connection = url.openConnection() match {
			case d: HttpURLConnection => d
			case _ => throw new InvalidType("Tipo de datos invalido")
		}
		connection.setRequestMethod("POST")
		connection.setDoOutput(true)
		connection.setDoInput(true)
		connection.setRequestProperty("Content-type","text/xml")
		connection.setRequestProperty("SOAPAction","\"http://calculadora/\"")

		val mensaje =
			"<?xml version=\"1.0\" ?>"+
			"<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:calculadora=\"http://Calculadora/\">" +
			"		<S:Body>" +
			"			<ns2:"+ op + " xmlns:ns2=\"http://Calculadora/\">" +
			"				<arg0>" +a +"</arg0><arg1>"+b+"</arg1>" +
			"			</ns2:"+ op+ ">" +
			"		</S:Body>"+
			"	</S:Envelope>"

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

	def getDatosConsola(): (String, Double, Double)	= {
		println("Calculadora Usando Web Service ...");
		println("¿Que Operacion Desea realizar?\n");
		println("1. suma");
		println("2. resta");
		println("3. mul");
		println("4. div")  ;
		val str=readLine()
		println("Ingresa el primer dato:" );
		val a=readDouble()
		println("Ingresa el  segundo dato:" );
		val b=readDouble()
		(str, a, b)
	}
}


class InvalidType(d: String) extends Exception
