package br.com.erudio.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition(info =
@Info(title = "Book Service API", version = "1.0", description = "Documentation Book Service API v1.0"))
public class OpenApiConfiguration {

	@Bean
	public OpenAPI customOpenAPI(@Value("1.5.7") String appVersion) {
		return new OpenAPI()
			.components(new Components())
			.info(
				new io.swagger.v3.oas.models.info.Info()
					.title("Book Service API")
					.version(appVersion)
					.license(
						new License()
						.name("Apache 2.0")
						.url("http://springdoc.org")
						)
			);
	}
}
