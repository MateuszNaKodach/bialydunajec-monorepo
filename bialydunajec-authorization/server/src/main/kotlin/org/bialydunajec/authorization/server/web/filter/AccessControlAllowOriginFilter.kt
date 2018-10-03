package org.bialydunajec.authorization.server.web.filter

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
internal class AccessControlAllowOriginFilter : Filter {

    @Throws(ServletException::class)
    override fun init(fc: FilterConfig) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, resp: ServletResponse,
                          chain: FilterChain) {
        val response = resp as HttpServletResponse
        val request = req as HttpServletRequest
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "${HttpMethod.POST}, ${HttpMethod.GET}, ${HttpMethod.PUT}, ${HttpMethod.OPTIONS}, ${HttpMethod.DELETE}")
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600")
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN")

        if (HttpMethod.OPTIONS.toString().equals(request.method, ignoreCase = true)) {
            response.status = HttpServletResponse.SC_OK
        } else {
            chain.doFilter(req, resp)
        }

    }

    override fun destroy() {}

}