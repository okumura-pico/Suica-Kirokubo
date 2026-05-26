package com.example.suica

import com.microsoft.azure.functions.*
import com.microsoft.azure.functions.annotation.*
import java.util.Optional

class TransactionsFunction {

    @FunctionName("transactions")
    fun run(
        @HttpTrigger(
            name = "req",
            methods = [HttpMethod.POST],
            authLevel = AuthorizationLevel.FUNCTION,
            route = "transactions"
        )
        request: HttpRequestMessage<Optional<String>>,
        context: ExecutionContext
    ): HttpResponseMessage {
        context.logger.info("Received transaction request")

        val body = request.body.orElse(null)
            ?: return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                .body("""{"error": "Request body is required"}""")
                .header("Content-Type", "application/json")
                .build()

        // TODO: #4 実装予定 - JSON パース → ハッシュID生成 → 重複チェック → 外出先推定 → Excel 書き込み
        context.logger.info("Body length: ${body.length}")

        return request.createResponseBuilder(HttpStatus.OK)
            .body("""{"inserted": 0, "skipped": 0}""")
            .header("Content-Type", "application/json")
            .build()
    }
}
