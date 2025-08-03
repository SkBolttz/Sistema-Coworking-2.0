package br.com.sistema.coworking.Configuration;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String caminhoAbsoluto = Paths.get("uploads/imagens/salas").toAbsolutePath().toString();
        registry.addResourceHandler("/imagens/**")
                .addResourceLocations("file:" + caminhoAbsoluto + "/");
    }
}
