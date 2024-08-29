package dev.zvaryyka.rebalancerservice.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class KeycloakLogoutHandler @Autowired constructor(
    @Lazy private val restTemplate: RestTemplate
) : LogoutHandler {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(KeycloakLogoutHandler::class.java)
    }

    override fun logout(request: HttpServletRequest, response: HttpServletResponse, auth: Authentication) {
        logoutFromKeycloak(auth.principal as OidcUser)
    }

    @Bean
    fun getNewRestTemplate(): RestTemplate = RestTemplate()

    private fun logoutFromKeycloak(user: OidcUser) {
        val endSessionEndpoint = "${user.issuer}/protocol/openid-connect/logout"
        val builder = UriComponentsBuilder
            .fromUriString(endSessionEndpoint)
            .queryParam("id_token_hint", user.idToken.tokenValue)

        val logoutResponse: ResponseEntity<String> = restTemplate.getForEntity(builder.toUriString(), String::class.java)
        if (logoutResponse.statusCode.is2xxSuccessful) {
            logger.info("Successfully logged out from Keycloak")
        } else {
            logger.error("Could not propagate logout to Keycloak")
        }
    }
}
