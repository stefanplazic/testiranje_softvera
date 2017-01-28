package com.nekretnine.endpoints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(notificationHandler(), "/notification").withSockJS();
		registry.addHandler(adminDashboardHandler(), "/adminDashboard").setAllowedOrigins("*").withSockJS();
		registry.addHandler(moderatorDashboardHandler(), "/moderatorDashboard").withSockJS();
    }

    @Bean
    public WebSocketHandler notificationHandler() {
        return new NotificationHandler();
    }
    
    @Bean
    public WebSocketHandler adminDashboardHandler() {
        return new AdminDashboardHandler();
    }
    
    @Bean
    public WebSocketHandler moderatorDashboardHandler() {
    	return new ModeratorDashboardHandler();
    }
}