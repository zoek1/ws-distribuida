
import javax.xml.ws.{Endpoint}
import Chat.{Chat}


object ServidorChat
{
	def main (args: Array[String]): Unit = {
		Endpoint.publish("http://localhost:9086/calculadora/server",new Chat())
		System.out.println ("Servicio publicado en http://localhost:9086/chat/server")
	}
}
