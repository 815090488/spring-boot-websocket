package igomall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 配置WebSocket
 * 注释@EnableWebSocketMessageBroker开始使用STOMP协议来传输基于代理（message broker）的消息
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //设置连接端点
        registry.addEndpoint("/socket").withSockJS();

        registry.addEndpoint("/socket")
                //解决跨域问题
                .setAllowedOrigins("*")
                //握手处理，主要连接的时候认证
                //.setHandshakeHandler()
                //拦截处理
                //.addInterceptors()
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/server", "/user", "/message");

        //registry.enableStompBrokerRelay().setRelayHost().setRelayPort() 其他方式


        //这里使用的是内存模式，生产环境可以使用rabbitmq或者其他mq。
        //这里注册两个，主要是目的是将广播和队列分开。

        //registry.enableSimpleBroker("/topic", "/queue");

        //客户端名称前缀 （可有可无）
        //registry.setApplicationDestinationPrefixes("/app");

        //用户名称前（可有可无）
        //registry.setUserDestinationPrefix("/user");
    }
}
