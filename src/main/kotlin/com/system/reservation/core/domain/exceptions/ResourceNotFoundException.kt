package com.system.reservation.core.domain.exceptions

import com.system.reservation.core.domain.exceptions.BusinessException

class ResourceNotFoundException(
    message: String,
) : BusinessException(message)
