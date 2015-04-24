package Calculadora

import javax.jws.{WebMethod}
import javax.jws.{WebService}

@WebService class Calculadora {
  @WebMethod def suma(a: Double, b: Double): String = "Suma = " + (a+b)
  @WebMethod def resta(a: Double, b: Double): String = "Resta = " + (a - b)
  @WebMethod def mul(a: Double, b: Double): String =  "Multiplicacion = "+ (a*b)
  @WebMethod def div(a: Double, b: Double): String = "Divicion = "+ (a/b)
}
