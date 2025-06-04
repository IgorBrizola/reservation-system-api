package com.system.reservation.configuration.doc

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenapiConfig {
    private val apiVersion = "1.0.0"
    private val urlLocalServer = "http://localhost:8080/"
    private val urlDevelopmentServer = "https://backdev.reservation.com.br/"
    private val internalServerErrorResponse = "InternalServerErrorResponse"
    private val notFoundResponse = "NotFoundResponse"
    private val badRequestResponse = "BadRequestResponse"

    @Bean
    fun api(): OpenAPI {
        val localServer = Server().description("Local").url(this.urlLocalServer)
        val devServer = Server().description("Development").url(this.urlDevelopmentServer)
        return OpenAPI()
            .servers(
                listOf(localServer, devServer),
            ).info(
                Info()
                    .title("Reservation System API")
                    .description("Generated API from default reservation kotlin archetype")
                    .version(this.apiVersion),
            ).components(
                Components()
                    .responses(generateDefaultResponses()),
            )
    }

    private fun generateDefaultResponses(): MutableMap<String, ApiResponse> {
        val responsesMap = mutableMapOf<String, ApiResponse>()
        val content =
            Content()
                .addMediaType(
                    org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                    MediaType(),
                )

        responsesMap[this.internalServerErrorResponse] = ApiResponse().description("Internal Server Error").content(content)
        responsesMap[this.notFoundResponse] = ApiResponse().description("Not Found").content(content)
        responsesMap[this.badRequestResponse] = ApiResponse().description("Bad Request").content(content)

        return responsesMap
    }

    @Bean
    fun openApiCustomizer(): OpenApiCustomizer =
        OpenApiCustomizer {
            it.paths.values.forEach { item ->
                item.readOperationsMap().forEach { (httpMethod, operation) ->
                    run {
                        val responses = operation.responses
                        when (httpMethod) {
                            PathItem.HttpMethod.GET -> {
                                responses.addApiResponse("500", ApiResponse().`$ref`(this.internalServerErrorResponse))
                                responses.addApiResponse("404", ApiResponse().`$ref`(this.notFoundResponse))
                                responses.addApiResponse("400", ApiResponse().`$ref`(this.badRequestResponse))
                            }
                            PathItem.HttpMethod.PUT -> {
                                responses.addApiResponse("500", ApiResponse().`$ref`(this.internalServerErrorResponse))
                                responses.addApiResponse("404", ApiResponse().`$ref`(this.notFoundResponse))
                                responses.addApiResponse("400", ApiResponse().`$ref`(this.badRequestResponse))
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
}
