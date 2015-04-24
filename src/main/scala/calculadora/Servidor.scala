
import javax.xml.ws.{Endpoint}
import Calculadora.{Calculadora}


object Servidor
{
	def main (args: Array[String]): Unit = {
		Endpoint.publish("http://localhost:9085/calculadora/server",new Calculadora())
		System.out.println ("Servicio publicado en http://localhost:9085/prueba/server")
	}
}
