package com.system.reservation.core.domain.exceptions

import com.system.reservation.core.domain.exceptions.BusinessException

class ResourceAlreadyExistsException(
    message: String,
) : BusinessException(message)
