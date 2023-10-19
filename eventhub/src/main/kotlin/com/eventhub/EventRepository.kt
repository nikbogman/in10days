package com.eventhub

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository: MongoRepository<Event, ObjectId> {
    fun findAllByUserId(userId: String): List<Event>;
    fun deleteAllByUserId(userId: String);
}