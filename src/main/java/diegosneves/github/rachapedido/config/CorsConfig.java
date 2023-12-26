package diegosneves.github.rachapedido.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Esta classe é responsável por configurar o CORS (Compartilhamento de Recursos de Origem Cruzada) para a aplicação.
 *
 * @author diegosneves
 */
@Configuration
public class CorsConfig {

    /**
     * Configura e retorna um {@link CorsFilter}.
     * <p>
     * Este filtro permite solicitações de qualquer origem (*),
     * aceita qualquer cabeçalho (*) e permite qualquer método HTTP (*), como GET, POST, etc.
     *
     * @return {@link CorsFilter} com a configuração definida
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // Permitir solicitações de qualquer origem
        config.addAllowedHeader("*"); // Permitir qualquer cabeçalho
        config.addAllowedMethod("*"); // Permitir qualquer método (GET, POST, etc.)
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
