package repository.dao

import reactivemongo.bson.BSONObjectID

trait Entity {
  //  @Key("_id")
  val _id: BSONObjectID
}

case class VegetableDAO(_id: BSONObjectID,
                        id: String,
                        name: String,
                        description: String,
                        price: Double) extends Entity

case class QuestionDAO(_id: BSONObjectID,
                       id: String,
                       name: String,
                       description: String,
                       date: String,
                       answers: Seq[RespuestaDAO]) extends Entity

case class RespuestaDAO(_id: BSONObjectID,
                        id: String,
                        description: String,
                        user: String,
                        date: String) extends Entity

case class ContactDAO(_id: BSONObjectID,
                      id: String,
                      bolsonId: String,
                      name: String,
                      email: String,
                      contactNumber: String,
                      delivery: Boolean) extends Entity