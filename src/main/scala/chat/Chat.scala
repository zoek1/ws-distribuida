package Chat

import javax.jws.{WebMethod}
import javax.jws.{WebService}

case class Correo(nombre: String, msg: String)

@WebService class Chat {
  var correos: List[Correo] = Nil

  @WebMethod def enviar(nombre: String, msg: String): String = {
     correos = Correo(nombre, msg) :: correos
     "Correo enviado satisfactoriamente"
  }
  @WebMethod def recibir(nombre: String, empty: String): String = {
     for (i <- correos
            if i.nombre == nombre ) {
        val msg = i.msg
        correos = correos diff List(i)
        return msg
     }
     ""
  }
}
