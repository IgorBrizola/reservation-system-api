package com.system.reservation.util

import com.fasterxml.jackson.core.JsonParser.Feature
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val log = LoggerFactory.getLogger(AppUtil::class.java)

private const val PATTERN_FORMAT = "EEEE, dd 'de' MMMM 'de' yyyy 'às' HH:mm"

object AppUtil {
    fun logContext(
        description: String,
        context: Any?,
    ) = log.info("[$description] - ${mapperDefault().writeValueAsString(context)}")

    fun logContext(description: String) = log.info(description)

    fun logContextError(
        description: String,
        context: Any?,
    ) = log.error("[$description] - ${mapperDefault().writeValueAsString(context)}")

    fun logContextError(description: String) = log.error(description)

    private fun mapperDefault(): ObjectMapper =
        jacksonObjectMapper()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
            .enable(SerializationFeature.INDENT_OUTPUT)
            .enable(Feature.ALLOW_UNQUOTED_FIELD_NAMES)
            .registerModules(JavaTimeModule())

    fun getDateFormatBR(dateInstant: Instant): String {
        val formatter =
            DateTimeFormatter
                .ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.of("America/Sao_Paulo"))

        return formatter.format(dateInstant)
    }
}
