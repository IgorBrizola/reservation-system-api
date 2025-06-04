package com.system.reservation.adpters.web.exceptionHandler

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.system.reservation.core.domain.exceptions.BusinessException
import com.system.reservation.core.domain.exceptions.ResourceAlreadyExistsException
import com.system.reservation.core.domain.exceptions.ResourceNotFoundException
import jakarta.validation.ConstraintViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@ControllerAdvice
class ExceptionHandlerAdvice : ResponseEntityExceptionHandler() {
    private companion object {
        const val DEFAULT_USER_MESSAGE =
            "Something wrong occurred. Please, try again. If it persists please contact us."
    }

    @Autowired
    private lateinit var messageSource: MessageSource

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(
        ex: BusinessException,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val detail = ex.message ?: DEFAULT_USER_MESSAGE
        val status = HttpStatus.BAD_REQUEST
        val userMessage = DEFAULT_USER_MESSAGE
        val problem =
            this.createProblem(
                status,
                ProblemType.BUSINESS_ERROR,
                detail,
                userMessage,
            )

        return this.handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        ex: BusinessException,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val detail = ex.message ?: DEFAULT_USER_MESSAGE
        val userMessage = ex.message ?: DEFAULT_USER_MESSAGE
        val status = HttpStatus.NOT_FOUND
        val problem =
            this.createProblem(
                status,
                ProblemType.RESOURCE_NOT_FOUND,
                detail,
                userMessage,
            )

        return this.handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    @ExceptionHandler(ResourceAlreadyExistsException::class)
    fun handleResourceAlreadyExistsException(
        ex: BusinessException,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val detail = ex.message ?: DEFAULT_USER_MESSAGE
        val userMessage = ex.message ?: DEFAULT_USER_MESSAGE
        val status = HttpStatus.CONFLICT
        val problem =
            this.createProblem(
                status,
                ProblemType.RESOURCE_ALREADY_EXISTS,
                detail,
                userMessage,
            )

        return this.handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    @ExceptionHandler
    fun handleWrongURLParameterException(
        ex: MethodArgumentTypeMismatchException,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val detail =
            "URL param ${ex.name}, got an unexpected value ${ex.value}. " +
                "Please fix it. Expected value: ${ex.requiredType?.simpleName}"
        val status = HttpStatus.BAD_REQUEST
        val userMessage = DEFAULT_USER_MESSAGE
        val problem =
            this.createProblem(
                status,
                ProblemType.INVALID_PARAMETER,
                detail,
                userMessage,
            )

        return this.handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    @ExceptionHandler
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val detail = ex.message ?: DEFAULT_USER_MESSAGE
        val status = HttpStatus.BAD_REQUEST
        val userMessage = "Request param got an unexpected value. Please fix it."
        val problem =
            this.createProblem(
                status,
                ProblemType.INVALID_PARAMETER,
                detail,
                userMessage,
            )

        return this.handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    @ExceptionHandler
    fun handleConstraintViolationException(
        ex: ConstraintViolationException,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val detail = DEFAULT_USER_MESSAGE
        val status = HttpStatus.BAD_REQUEST
        val userMessage = "A field got an unexpected value. Please fix it."
        val problem =
            this.createProblem(
                status,
                ProblemType.INVALID_DATA,
                detail,
                userMessage,
            )

        ex.constraintViolations.forEach {
            run {
                problem.fields.add(Problem.Field(it.propertyPath.last().name, it.message))
            }
        }

        return this.handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? = this.handleInternalValidation(ex, HttpStatus.valueOf(status.value()), request, ex.bindingResult)

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        httpStatusCode: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val detail = "Requested param ${ex.parameterName}, is missing. Please fix it."
        val status = HttpStatus.BAD_REQUEST
        val userMessage = DEFAULT_USER_MESSAGE
        val problem =
            this.createProblem(
                status,
                ProblemType.MISSING_PARAMETER,
                detail,
                userMessage,
            )

        return this.handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val rootCause = ex.rootCause

        if (rootCause is InvalidFormatException) {
            return this.handleInvalidFormatException(rootCause, headers, status, request)
        }

        val problemType = ProblemType.UNCOMPREHENSIBLE_MESSAGE
        val detail = "Request body is invalid. Please fix it."

        val problem = this.createProblem(status, problemType, detail)

        return this.handleExceptionInternal(ex, problem, headers, status, request)
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val problemType = ProblemType.RESOURCE_NOT_FOUND
        val detail = "Resource ${ex.requestURL}, not found."
        val problem = this.createProblem(status, problemType, detail)
        problem.userMessage = detail

        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    private fun handleInvalidFormatException(
        ex: InvalidFormatException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val problemType = ProblemType.UNCOMPREHENSIBLE_MESSAGE
        val path = ex.path.map { ref -> ref.fieldName }.joinToString(".")
        val detail =
            "A property [$path] has received value [${ex.value}], of wrong type. " +
                "Fix it and inform a value that matches the type [${ex.targetType.simpleName}]"

        val problem = this.createProblem(status, problemType, detail, DEFAULT_USER_MESSAGE)
        return this.handleExceptionInternal(ex, problem, headers, status, request)
    }

    private fun handleInternalValidation(
        ex: Exception,
        status: HttpStatus,
        request: WebRequest,
        bindingResult: BindingResult,
    ): ResponseEntity<Any>? {
        val problemType = ProblemType.INVALID_DATA
        val detail = "One or more fields/parameters are invalid. Fix it and try again."
        val problem = this.createProblem(status, problemType, detail)
        problem.userMessage = detail

        bindingResult.fieldErrors.forEach { err ->
            run {
                val message = messageSource.getMessage(err, LocaleContextHolder.getLocale())
                problem.fields.add(Problem.Field(err.field, message))
            }
        }

        return this.handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    private fun handleExceptionInternal(
        ex: Exception,
        body: Any,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any>? = super.handleExceptionInternal(ex, body, headers, status, request)

    private fun createProblem(
        status: HttpStatusCode,
        problemType: ProblemType,
        detail: String,
        userMessage: String = "",
    ): Problem =
        Problem(
            status = status.value(),
            type = problemType.uri(),
            title = problemType.title,
            detail = detail,
            userMessage = userMessage,
            timestamp = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS),
        )
}
