package com.system.reservation.adpters.web.exceptionHandler

import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

@Schema(description = "Representação de um resposta com erro", name = "Problem")
data class Problem(
    @Schema(description = "Código http status da resposta", example = "400")
    var status: Int,
    @Schema(
        description = "Url contendo tipo do erro. Frontend pode usar para gerar páginas específicas por tipo de erro",
        example = "http://localhost:8080/bad-request",
    )
    var type: String,
    @Schema(description = "Título do erro", example = "Bad Request")
    var title: String,
    @Schema(description = "Mensagem de erro detalhada", example = "Falha na conversão do enum TipoLancamento")
    var detail: String,
    @Schema(
        description = "Mensagem amigável para usuário",
        example = "O Tipo de Lançamento informado na pesquisa é inválido",
    )
    var userMessage: String = "",
    @Schema(description = "Timestamp do momento do erro", example = "2023-04-04T14:03:13.819Z")
    var timestamp: OffsetDateTime,
    @Schema(description = "Campos que apresentaram erros")
    val fields: ArrayList<Field> = arrayListOf(),
) {
    @Schema(description = "Representação de um campo que apresentou erro durante uma requisição", name = "Field")
    class Field(
        @Schema(description = "Nome do campo que apresentou erro", example = "tipoLancamento")
        var name: String,
        @Schema(
            description = "Mensagem amigável para usuário",
            example = "O Tipo de lançamento recebeu um valor inválido",
        )
        var userMessage: String,
    )
}
