package br.com.desafio.marvel.configuration;

import br.com.desafio.marvel.threads.MarvelTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by alexcosta on 29/11/16.
 */
@Configuration
@EnableAsync
public class AppConfig {

    @Bean
    public MarvelTask asyncTask() {
        return new MarvelTask();
    }
}