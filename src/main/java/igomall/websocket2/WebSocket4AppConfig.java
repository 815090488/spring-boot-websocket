package igomall.websocket2;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocket4AppConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new AppWebSocketHandler(), "/websocket/app/sendMessageByComplaintHandling","/websocket/app")
                .setAllowedOrigins("*").addInterceptors(new CountHandshakeInterceptor());
    }
}