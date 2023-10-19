package com.eventhub

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import jakarta.servlet.http.*
import java.net.http.*
import java.net.URI

@Component
class BearerTokenInterceptor: HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val authorizationHeader = request.getHeader("Authorization")
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val token: String = authorizationHeader.substring("Bearer ".length)
            val url = "http://localhost:4000/authorize"
            val client = HttpClient.newHttpClient()
            val req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer $token")
                .GET()
                .build()
            val res = client.send(req, HttpResponse.BodyHandlers.ofString())
            return res.statusCode() == 200
        }
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        return false
    }
}