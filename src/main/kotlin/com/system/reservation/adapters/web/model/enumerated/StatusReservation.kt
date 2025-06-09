package com.system.reservation.adapters.web.model.enumerated

import com.system.reservation.core.domain.exceptions.BusinessException

enum class StatusReservation(
    val statusId: Int,
) {
    ACTIVE(1),
    CANCELED(2),
    ;

    companion object {
        fun getById(statusId: Int): StatusReservation =
            entries.find { it.statusId == statusId }
                ?: throw BusinessException("StatusId invalid!")
    }
}
