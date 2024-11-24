package job


import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession, functions}
import org.apache.spark.sql.functions._

object Examen {

  /**Ejercicio 1: Crear un DataFrame y realizar operaciones básicas
   Pregunta: Crea un DataFrame a partir de una secuencia de tuplas que contenga información sobre
   estudiantes (nombre, edad, calificación).
   Realiza las siguientes operaciones:

   Muestra el esquema del DataFrame.
   Filtra los estudiantes con una calificación mayor a 8.
   Selecciona los nombres de los estudiantes y ordénalos por calificación de forma descendente.
   */
  def ejercicio1 (estudiantes: DataFrame): DataFrame = {
    estudiantes.show()

    val estudiantesFiltrados = estudiantes.filter(col("calificacion") > 8)

    val estudiantesOrdenados = estudiantesFiltrados
      .select("nombre")
      .orderBy(col("calificacion").desc)

    estudiantesOrdenados
  }

  /*Ejercicio 2: UDF (User Defined Function)
   Pregunta: Define una función que determine si un número es par o impar.
   Aplica esta función a una columna de un DataFrame que contenga una lista de números.*/

  def ejercicio2(numeros: DataFrame): DataFrame =  {
    val parImpar = udf((n:Int) => if (n % 2==0) "Par" else "Impar");

    val updatedDF = numeros.withColumn("ParImpar", parImpar(col("Numeros")))
    updatedDF.show()

    updatedDF
  }

  /*Ejercicio 3: Joins y agregaciones
   Pregunta: Dado dos DataFrames,
   uno con información de estudiantes (id, nombre)
   y otro con calificaciones (id_estudiante, asignatura, calificacion),
   realiza un join entre ellos y calcula el promedio de calificaciones por estudiante.*/

  def ejercicio3(estudiantes: DataFrame , calificaciones: DataFrame): DataFrame = {
    val join = estudiantes
      .join(calificaciones, estudiantes("id") === calificaciones("id_estudiante"))
      .select("id", "nombre", "asignatura", "calificacion")

    join.groupBy("id").agg(round(avg("calificacion"), 2).as("media")).orderBy("id")
  }


  /*Ejercicio 4: Uso de RDDs
   Pregunta: Crea un RDD a partir de una lista de palabras y cuenta la cantidad de ocurrencias de cada palabra. */

  def ejercicio4(palabras: List[String])(spark:SparkSession): RDD[(String, Int)] = {
    val sc = spark.sparkContext

    // Convertir la lista de palabras en un RDD
    val palabrasRDD = sc.parallelize(palabras)

    palabrasRDD.map(palabra => (palabra, 1)).reduceByKey(_ + _)
  }

   /*
   Ejercicio 5: Procesamiento de archivos
   Pregunta: Carga un archivo CSV que contenga información sobre
   ventas (id_venta, id_producto, cantidad, precio_unitario)
   y calcula el ingreso total (cantidad * precio_unitario) por producto.
   */
  def ejercicio5(ventas: DataFrame): DataFrame = {
    //calcular ingreso total por producto
    ventas.groupBy("id_producto").agg(sum(col("cantidad") * col("precio_unitario")).alias("ingreso_total")).orderBy("id_producto")
  }


}
