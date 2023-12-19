package diegosneves.github.rachapedido.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
  	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  		http
  		 	.authorizeHttpRequests((authorizeHttpRequests) ->
  		 		authorizeHttpRequests.anyRequest().permitAll()
  		 	).csrf(AbstractHttpConfigurer::disable);
  		return http.build();
  	}

}
