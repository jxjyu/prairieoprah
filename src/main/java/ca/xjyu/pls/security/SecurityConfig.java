package ca.xjyu.pls.security;

import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures the Spring Security filter chain for the application.
 * <p>
 * This class enables web security and integrates Vaadin's security configurations with
 * Auth0 for OAuth2 authentication. It specifically manages the login
 * redirection and the OIDC logout process.
 *
 * @author Jeff Yu
 * @version 0.3
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /**
     * Defines the security filter chain to manage HTTP security.
     * <p>
     * This bean configures:
     * <ul>
     *      <li>The Auth0 logout handler with a specific post-logout redirect URI.
     *      <li>Vaadin's internal security settings, pointing to the Auth0 authorisation endpoint.
     *      <li>The registration of the logout success handler within the security filter.
     * </ul>
     *
     * @param http                         The {@link HttpSecurity} object to configure.
     * @param clientRegistrationRepository The repository containing OAuth2 client registrations.
     * @return  A fully configured {@link SecurityFilterChain}.
     * @throws Exception    If an error occurs during the security configuration process.
     */
    @Bean
    public SecurityFilterChain securityFilterChain
            (HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        // Configure Auth0 log-out handler
        OidcClientInitiatedLogoutSuccessHandler logoutSuccessHandler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        logoutSuccessHandler.setPostLogoutRedirectUri("https://ow.ubcea-lounge.ca/");

        http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
            configurer.oauth2LoginPage("https://ow.ubcea-lounge.ca/oauth2/authorization/auth0");
        });

        http.logout(logout -> logout
                .logoutSuccessHandler(logoutSuccessHandler)
        );

        return http.build();
    }
}