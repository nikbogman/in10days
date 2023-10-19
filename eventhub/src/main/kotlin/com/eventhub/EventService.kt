package com.eventhub

import com.eventhub.dto.*
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EventService(val eventRepository: EventRepository) {
    fun getAllEvents(): List<Event> {
        return eventRepository.findAll()
    }

    fun getAllEventsByUserId(userId: String): List<Event> {
        return eventRepository.findAllByUserId(userId)
    }

    fun deleteAllEventsByUserId(userId: String) {
        return eventRepository.deleteAllByUserId(userId)
    }

    fun insertEvent(dto: CreateEventDto): Event {
        val event = Event(
            name = dto.name,
            description = dto.description,
            userId = dto.userId
        )
        return eventRepository.insert(event)
    }

    fun getEvent(id: ObjectId): Event? {
        return eventRepository.findById(id).orElse(null)
    }

    fun updateEvent(id: ObjectId, dto: UpdateEventDto): Event? {
        val existing = eventRepository.findById(id)
        if (!existing.isPresent) {
            return null
        }
        val prev = existing.get()
        if(prev.name == dto.name || prev.description == dto.description){
            return prev
        }
        return eventRepository.save(
            prev.copy(
                name = if(dto.name.isNullOrEmpty()) prev.name else dto.name,
                description = if(dto.description.isNullOrEmpty()) prev.name else dto.description,
                modifiedDate = LocalDateTime.now()
            )
        )
    }

    fun deleteEvent(id: ObjectId) {
        return eventRepository.deleteById(id)
    }
}