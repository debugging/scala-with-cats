package example

object Chapter1_TypeClasses extends App {

  /*
    scala type classes components:
      - traits; type classes
      - implicit values: type class instances
      - implicit parameters: type class use; and
      - implicit classes: optional utilities that make type classes easier to use
   */

  sealed trait Json
  final case class JsObject(get: Map[String, Json]) extends Json
  final case class JsString(get: String) extends Json
  final case class JsNumber(get: Double) extends Json
  final case object JsNull extends Json

  trait JsonWriter[A] {
    def write(value: A): Json
  }

  final case class Person(name: String, email: String)

  object JsonWriterInstances {
    implicit val stringWriter: JsonWriter[String] =
      new JsonWriter[String] {
        def write(value: String): Json =
          JsString(value)
      }

    implicit val personWriter: JsonWriter[Person] =
      new JsonWriter[Person] {
        def write(value: Person): Json =
          JsObject(
            Map(
              "name" -> JsString(value.name),
              "email" -> JsString(value.email)
            )
          )
      }
  }

  // Type class use
  // 2 ways:  Interface Objects or Interface Syntax

}
