package com.eventhub

import com.eventhub.dto.*
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/events")
class EventController(
    @Autowired val eventService: EventService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun scheduleEvent(@RequestBody body: CreateEventDto) = eventService.insertEvent(body)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getEvents(@RequestParam("userId") userId: String?) =
        if(userId.isNullOrEmpty()) eventService.getAllEvents()
        else eventService.getAllEventsByUserId(userId)

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    fun deleteEventsByUserId(@RequestParam("userId") userId: String) = eventService.deleteAllEventsByUserId(userId)

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    fun getEvent(@PathVariable("eventId") eventId: String) = eventService
            .getEvent(ObjectId(eventId)) ?: ResponseStatusException(HttpStatus.NOT_FOUND)

    @PutMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateEvent(@PathVariable("eventId") eventId: String, @RequestBody body: UpdateEventDto) = eventService
        .updateEvent(ObjectId(eventId), body) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteEvent(@PathVariable("eventId") eventId: String) = eventService.deleteEvent(ObjectId(eventId))

//    @PatchMapping("/{id}")
//    fun cancelEvent(@PathVariable("id") id: String)
}