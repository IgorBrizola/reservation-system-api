package com.system.reservation.adpters.web

import com.system.reservation.adpters.web.doc.ReservationOpenAPI
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/reservation")
class ReservationsController(

): ReservationOpenAPI {
}