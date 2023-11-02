package com.dependency.test.demo.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Profile("!test")
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomFilter customFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /** boot 2.x
        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .addFilterAfter(customFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()

                .requestMatchers(
                        "/**"
                )
                .permitAll()
        ;
        */

        http
                .csrf(AbstractHttpConfigurer::disable)                                      //csrf().disable()
                .httpBasic(AbstractHttpConfigurer::disable)                                 //httpBasic().disable()
                .cors(Customizer.withDefaults())                                            //cors()
                .addFilterAfter(customFilter, UsernamePasswordAuthenticationFilter.class)   //특정 필터 이후에 커스텀 필터 적용
                .authorizeHttpRequests(request ->
                    request
                            .requestMatchers("/permit-all").permitAll()
                            .requestMatchers("/multi-role-check").hasAnyRole("MULTI_ROLE_1","MULTI_ROLE_2")
                            .requestMatchers("single-role.check").hasRole("SINGLE_ROLE")
                            .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowedHeaders(List.of("HEAD", "GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
