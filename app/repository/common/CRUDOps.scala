package repository.common

import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}
import repository.dao.Entity

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait CRUDOps[T <: Entity, K] {
  self: Repository[T] =>

  def upConvert(dao: T): K

  protected def create(element: T)(implicit b: BSONDocumentWriter[T]): Future[K] = {
    collection.flatMap(_.insert(element)).map{ r =>
      if (r.ok) {
        upConvert(element)
      } else {
        throw new RuntimeException("Couldn't create a new element into the database")
      }
    }
  }

  def findAll(implicit b: BSONDocumentReader[T]): Future[Seq[K]] = for {
    col <- collection
    bson = BSONDocument.empty
    cursor = col.find(bson).cursor()
    list <- cursor.collect[Seq](Int.MaxValue, true) // Cursor.FailOnError[List[T]]() instead of true
  } yield list.map(upConvert)

  def findOne(id: String)(implicit b: BSONDocumentReader[T]) : Future[Option[K]] = for {
    _id <- parseId(id)
    bson = BSONDocument("_id" -> _id)
    col <- collection
    event <- col.find(bson).one[T]
    element = event.map(upConvert)
  } yield element

  def update(id: String, element: T)(implicit b: BSONDocumentWriter[T]): Future[Boolean] = for {
    _id <- parseId(id)
    bson = BSONDocument("_id" -> _id)
    col <- collection
    updated <- col.update(bson, element).map(_.ok)
  } yield updated

  def remove(id: String)(implicit b: BSONDocumentWriter[T]): Future[Boolean] = for {
    _id <- parseId(id)
    bson = BSONDocument("_id" -> _id)
    col <- collection
    updated <- col.remove(bson).map(_.ok)
  } yield updated

  private def parseId(id: String) = {
    BSONObjectID.parse(id) match {
      case Success(r) => Future.successful(r)
      case Failure(ex) => Future.failed(ex)
    }
  }

}
