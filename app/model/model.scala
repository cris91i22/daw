package model

case class UID[T](value: String)

case class Vegetable(id: String,
                     name: String,
                     description: String,
                     price: Double)


case class Bolsones(id: String,
                    name: String,
                    contents: Seq[String],
                    price: BigDecimal,
                    img: String)

case class Fruta(name: String,
                 description: String)

case class Sucursal(name: String,
                    location: Option[String],
                    tel: String,
                    attendanceTime: String)

case class Pregunta(id: String,
                    description: String,
                    name: String,
                    date: String,
                    answers: Seq[Respuesta])

case class Respuesta(description: String,
                     user: String,
                     date: String)