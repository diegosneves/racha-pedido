package diegosneves.github.rachapedido.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe de configuração responsável pelas definições de segurança da aplicação web.
 *
 * @author diegosneves
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	/**
     * Configura a cadeia de filtros de segurança para o serviço HTTP.
     * Todas as solicitações recebidas serão permitidas e o recurso CSRF será desativado.
     *
     * @param http instância do serviço {@link HttpSecurity}
     * @return retorna a instância configurada de {@link SecurityFilterChain}
     * @throws Exception exceção lançada em caso de falha durante a configuração
     */
    @Bean
  	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  		http
  		 	.authorizeHttpRequests((authorizeHttpRequests) ->
  		 		authorizeHttpRequests.anyRequest().permitAll()
  		 	).csrf(AbstractHttpConfigurer::disable);
  		return http.build();
  	}

}
