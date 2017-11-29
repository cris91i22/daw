package repository.common

import repository.dao.Entity

trait BaseRepository[T <: Entity, K] extends Repository[T] with CRUDOps[T, K]
