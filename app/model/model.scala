package model

case class UID[T](value: String)

case class Vegetable(id: String,
                     name: String,
                     description: String,
                     price: Double)
