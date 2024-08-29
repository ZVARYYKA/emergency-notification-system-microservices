package dev.zvaryyka.rebalancerservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.web.session.HttpSessionEventPublisher

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(
    @Lazy private val keycloakLogoutHandler: KeycloakLogoutHandler
) {
    companion object {
        private const val GROUPS = "groups"
        private const val REALM_ACCESS_CLAIM = "realm_access"
        private const val ROLES_CLAIM = "roles"
    }

    @Bean
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

    @Bean
    fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        return RegisterSessionAuthenticationStrategy(sessionRegistry())
    }

    @Bean
    fun httpSessionEventPublisher(): HttpSessionEventPublisher {
        return HttpSessionEventPublisher()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { auth -> auth.anyRequest().authenticated() }
        http.oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
        http.oauth2Login(Customizer.withDefaults())
            .logout { logout -> logout.addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl("/") }

        return http.build()
    }

    @Bean
    fun authoritiesMapper(): GrantedAuthoritiesMapper {
        return GrantedAuthoritiesMapper { authorities ->
            val mappedAuthorities = mutableSetOf<GrantedAuthority>()
            val authority = authorities.firstOrNull()

            if (authority is OidcUserAuthority) {
                val userInfo = authority.userInfo

                if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
                    val realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM)
                    val roles = realmAccess[ROLES_CLAIM] as Collection<String>
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles))
                } else if (userInfo.hasClaim(GROUPS)) {
                    val roles = userInfo.getClaim<Collection<String>>(GROUPS)
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles))
                }
            } else if (authority is OAuth2UserAuthority) {
                val userAttributes = authority.attributes

                if (userAttributes.containsKey(REALM_ACCESS_CLAIM)) {
                    val realmAccess = userAttributes[REALM_ACCESS_CLAIM] as Map<String, Any>
                    val roles = realmAccess[ROLES_CLAIM] as Collection<String>
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles))
                }
            }

            mappedAuthorities
        }
    }

    private fun generateAuthoritiesFromClaim(roles: Collection<String>): Collection<GrantedAuthority> {
        return roles.map { role -> SimpleGrantedAuthority("ROLE_$role") }
    }
}
