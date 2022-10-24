package deno.springboot.armut

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
// main application class it should build the
// context and configurations starting from this class
// as a starting point
class ArmutApplication {
	@Bean
	fun restTemplate(builder:RestTemplateBuilder):RestTemplate = builder.build()
}

fun main(args: Array<String>) {
	runApplication<ArmutApplication>(*args)
}
