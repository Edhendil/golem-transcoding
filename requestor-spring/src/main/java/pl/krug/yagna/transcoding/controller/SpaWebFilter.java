package pl.krug.yagna.transcoding.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class SpaWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (!path.startsWith("/api") && !path.startsWith("/public") && !path.startsWith("/output")) {
            return chain.filter(
                    exchange.mutate().request(exchange.getRequest().mutate().path("/public/index.html").build()
                    ).build());
        }
        return chain.filter(exchange);
    }
}
