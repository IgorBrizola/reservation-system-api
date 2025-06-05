package com.system.reservation.adapters.web.model.enumerated

import com.system.reservation.core.domain.exceptions.BusinessException

enum class StatusTable(
    val statusId: Int,
) {
    AVAILABLE(1),
    RESERVED(2),
    INACTIVE(3),
    ;

    companion object {
        fun getById(statusId: Int): StatusTable = entries.find { it.statusId == statusId } ?: throw BusinessException("StatusId invalid!")
    }
}
