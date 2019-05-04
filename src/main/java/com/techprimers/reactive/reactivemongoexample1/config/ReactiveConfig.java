package com.techprimers.reactive.reactivemongoexample1.config;

import com.techprimers.reactive.reactivemongoexample1.RouterHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class ReactiveConfig {


    @Bean
    RouterFunction<?> routerFunction(RouterHandlers routerHandlers) {

        return RouterFunctions.route(RequestPredicates.GET("/rest/track/all"), routerHandlers::getAll)
                .andRoute(RequestPredicates.GET("/rest/track/{id}"), routerHandlers::getId)
                .andRoute(RequestPredicates.GET("/rest/track/{id}/events"), routerHandlers::getEvents)
                ;

    }

}
