package job

import job.Examen.{ejercicio1, ejercicio2, ejercicio3, ejercicio4, ejercicio5}
import org.apache.spark.SparkContext
import org.apache.spark.sql.DataFrame
import utils.TestInit
import org.apache.spark.sql.functions._

class ExamenTest extends TestInit {
  "ejercicio1" should "ordenar y filtrar los estudiantes con calificación mayor de 8 de un dataFrame" in {
    val sc: SparkContext = spark.sparkContext

    import spark.implicits._

    val estudiantesDF = sc.parallelize(Seq(
      ("Juan", 20, 9.5),
      ("Ana", 22, 7.8),
      ("Luis", 21, 8.5),
      ("Marta", 23, 9.0),
      ("JuanJo", 25, 9.5),
      ("Anabel", 19, 7.8),
      ("Pedro", 18, 8.5),
      ("Marcos", 20, 6.0),
      ("María", 21, 4.5),
      ("Mateo", 23, 4.8),
      ("Berta", 21, 4.5),
      ("Rocio", 21, 10.0),
      ("Elisa", 19, 9.5),
      ("Antonio", 26, 6.8),
      ("Rodrigo", 24, 8.5),
      ("Alicia", 24, 8.0)
    )).toDF("nombre", "edad", "calificacion")

    val countEstudiantesFiltrados = estudiantesDF.filter($"calificacion" > 8).count()

    val result: DataFrame = ejercicio1(estudiantesDF)

    result.show()

    assert(countEstudiantesFiltrados == result.count())
    assert(result.schema.fields.map(_.name).contains("nombre"))
  }

  "ejercicio2" should "crear columna nueva indicando si el numero de la primera columna es par o impar" in {
    import spark.implicits._

    val expectedResult = Seq(
      (1, "Impar"),
      (2, "Par"),
      (3, "Impar"),
      (4, "Par"),
      (5, "Impar"),
      (6, "Par"),
      (7, "Impar"),
      (8, "Par"),
      (9, "Impar"),
      (10, "Par"),
      (11, "Impar"),
      (12, "Par"),
      (13, "Impar"),
      (14, "Par"),
      (15, "Impar"),
      (16, "Par"),
      (17, "Impar"),
      (18, "Par"),
      (19, "Impar"),
      (20, "Par")
    ).toDF("Numeros", "ParImpar")

    //se crea el dataframe para enviar al ejercicio 2 como parametro
    val minValue = 1
    val maxValue = 20

    val numerosDF: DataFrame = (minValue to maxValue).toDF("Numeros")

    val result: DataFrame = ejercicio2(numerosDF)

    // Assert
    assert(result.schema == expectedResult.schema)
    assert(result.schema == expectedResult.schema)

    val diff1 = result.except(expectedResult)
    val diff2 = expectedResult.except(result)

    assert(diff1.isEmpty && diff2.isEmpty, "Los DataFrames no son iguales")
  }

  "ejercicio3" should "Con dos dataFrames, hacer un join entre ellos y calcular el promedio de calificaciones por estudiante." in {
    import spark.implicits._

    // DataFrame con información de estudiantes
    val estudiantes = Seq(
      (1, "Alex"),
      (2, "Maria"),
      (3, "Juan"),
      (4, "Lucia"),
      (5, "Pedro")
    ).toDF("id", "nombre")

    // DataFrame con calificaciones
    val calificaciones = Seq(
      (1, "Matemáticas", 8.5),
      (2, "Matemáticas", 9.2),
      (3, "Matemáticas", 7.8),
      (4, "Matemáticas", 8.0),
      (5, "Matemáticas", 6.9),
      (1, "Historia", 7.5),
      (2, "Historia", 8.3),
      (3, "Historia", 6.7),
      (4, "Historia", 9.1),
      (5, "Historia", 7.4),
      (1, "Ingles", 5.0),
      (2, "Ingles", 6.5),
      (3, "Ingles", 8.7),
      (4, "Ingles", 3.5),
      (5, "Ingles", 8.5)
    ).toDF("id_estudiante", "asignatura", "calificacion")

    val result: DataFrame = ejercicio3(estudiantes, calificaciones)

    result.show()

    assert(result.filter(col("id") === 1).select("media").collect()(0).getDouble(0) === 7.0)
    assert(result.filter(col("id") === 2).select("media").collect()(0).getDouble(0) === 8.0)
    assert(result.filter(col("id") === 3).select("media").collect()(0).getDouble(0) === 7.73)
    assert(result.filter(col("id") === 4).select("media").collect()(0).getDouble(0) === 6.87)
    assert(result.filter(col("id") === 5).select("media").collect()(0).getDouble(0) === 7.6)
  }

  "ejercicio4" should "a partir de una lista de palabras, devolver un RDD con el conteo de cada palabra" in {
    val palabras = List(
      "en", "un", "lugar", "de", "la", "Mancha", "de", "de", "lugar", "lugar","lugar", "lugar", "lugar")

    val enCountExpected = palabras.count(x => x == "en")
    val unCountExpected = palabras.count(x => x == "un")
    val lugarCountExpected = palabras.count(x => x == "lugar")
    val deCountExpected = palabras.count(x => x == "de")
    val laCountExpected = palabras.count(x => x == "la")
    val manchaCountExpected = palabras.count(x => x == "Mancha")

    val result = ejercicio4(palabras)(spark)

    //result
    result.foreach(println)

    //assert
    assert(enCountExpected == result.filter(_._1 == "en").collect()(0)._2)
    assert(unCountExpected == result.filter(_._1 == "un").collect()(0)._2)
    assert(lugarCountExpected == result.filter(_._1 == "lugar").collect()(0)._2)
    assert(deCountExpected == result.filter(_._1 == "de").collect()(0)._2)
    assert(laCountExpected == result.filter(_._1 == "la").collect()(0)._2)
    assert(manchaCountExpected == result.filter(_._1 == "Mancha").collect()(0)._2)
  }

  "ejercicio5" should "leer un csv, crear dataframe y calcular ingresos totales" in {
    // Leer el fichero CSV
    val ventasDF = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("src\\test\\resources\\examen")

    ventasDF.show()

    val result = ejercicio5(ventasDF)
    result.show()

    //asserts
    assert(result.filter(col("id_producto") === 101).select("ingreso_total").collect()(0).getDouble(0) === 460.0)
    assert(result.filter(col("id_producto") === 102).select("ingreso_total").collect()(0).getDouble(0) === 405.0)
    assert(result.filter(col("id_producto") === 103).select("ingreso_total").collect()(0).getDouble(0) === 280.0)
    assert(result.filter(col("id_producto") === 104).select("ingreso_total").collect()(0).getDouble(0) === 800.0)
    assert(result.filter(col("id_producto") === 105).select("ingreso_total").collect()(0).getDouble(0) === 570.0)
    assert(result.filter(col("id_producto") === 106).select("ingreso_total").collect()(0).getDouble(0) === 425.0)
    assert(result.filter(col("id_producto") === 107).select("ingreso_total").collect()(0).getDouble(0) === 396.0)
    assert(result.filter(col("id_producto") === 108).select("ingreso_total").collect()(0).getDouble(0) === 486.0)
    assert(result.filter(col("id_producto") === 109).select("ingreso_total").collect()(0).getDouble(0) === 540.0)
    assert(result.filter(col("id_producto") === 110).select("ingreso_total").collect()(0).getDouble(0) === 494.0)
  }
  }
