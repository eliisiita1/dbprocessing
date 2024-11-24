

1

true

"Hola Mundo."

"""HOLA
  |ESTO SON SALTOS DE LINEAS
  |QUE TAL OS PARECE?
  |""".stripMargin

//ESTO ES UN COMENTARIO DE UN NIVEL

/**
  ESTO ES DE VARIOS NIVELES
 POR EJEMPLO VALE */


2.2

1 + 1

"Hola " ++ "que tal?"

val str: String = "Hola,Mundo,soy ruben"
str.length
str.split(",")


val entero = 10
entero.toString.charAt(0)

str.toUpperCase
str.toLowerCase

(0 to 10).contains(10)

str.drop(1)

//mutable
var test = 1

test = 2


/** funciones */

//etiqueta de funcion/ nombre de funcion/ nombre variable de entrada/ tipo de la variable
def square(i: Double): Double = i * i
square(2)
square(2.2)
square("2".toDouble)

def suma(i:Double,j:Double): Double = i + j

suma(1,2)

def sumaCuadrados(i:Double,j:Double) = suma(square(i), square(j))


//condicionales
var n = 2


if( n == 1) println("N es un 1")
else println("No lo es ")


n=1

if( n == 1) {
  println("N es un 1")
}else {
  println("No lo es ")
}


n = 10


if( n == 1) {

  //esto es mi salida
  val out = "N es un 1"
  println(out)


}else {
  if(n>2){
    if(n != 1) println("Estoy seguro que no es un 1")
  }else println("No es")
}

println(if(n>2) "es mayor" else "no lo es")

//pater maching

val p = n match {
  case 1 => "Enero"
  case 2 => "Febrero"
  case _ => "es otro"
}
println(p)

/** WHILE DO WHILE*/

var k =0
while(k < 10){
  println(s"el valor ahore es: ${k}.")
  k=k+1
}



for(i <- 0 to 9) println(s"el valor ahora es: $i.")

/** Colecciones **/
val l = List(1,1,2,3,4,4)
val s = Set(1,1,2,3,4,6)
List("hola")
val mapa= Map(1->"Enero", 2->"Marzo", 3->"Abril")


mapa.filter(elemento => elemento._1 == 1)
mapa.filter(x => x._2 == "Abril")

s.filter(x => x > 2)
l(0)
l.head
l.tail

val concatenacion = l.head :: l.tail
concatenacion == l

l.map(elemento => elemento+1)
l.map(_+1)
def square(i: Int): Int = i * i

l.map(square)

l.foreach(x => println(s"este es mi elemento $x"))

def imprime(i: Int): Unit = println(s"este es mi elemento $i")
l.foreach(imprime)

l.map(square).filter(_>9).foreach(imprime)

case class Persona(nombre:String, edad:Int)

val personas = List(Persona("Ruben", 33), Persona("Alvaro", 39))

personas.filter(x => x.edad>34).foreach(println)


// FLATTEN
case class P(id:Int)


val lis = List(List(List(P(1)), List(P(2))),
  List(List(P(3),P(4))), List(List(P(5),P(6))))

lis.foreach(x=> x.foreach(println))

val flat = lis.flatten
flat.flatten


/** set */
val set = Set(1,2,3,1)

set + 3
set + 4
set ++ Set(4,5,6)
set - 3
set -- Set(1,3) //diferencia de conjuntos

set union Set(4,5,6)
set diff Set(1,3)
set & Set(3) //interseccion

class Box[T](value:T){ def get: T = value}
val intbox = new Box[Int](1)
val stringBox = new Box[String]("hello")

println(intbox.get)
println(stringBox.get)

val o: Option[String] = Some("5")

o.get
o.getOrElse("Otra cosa")


val no = None

no.get

Some(None).get

/** EJEMPLO DE Option en Scala */
case class Pe(nombre:String, edad:Option[Int])

val personaConEdad = Pe("Juan", Some(25))
val personaSinEdad = Pe("Ana", None)

personaConEdad.edad.get
personaSinEdad.edad.getOrElse("No tiene edad")
personaSinEdad.edad.get