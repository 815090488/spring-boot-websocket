package igomall.websocket2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class AppWebSocketHandler implements WebSocketHandler {


    /**
     *
     * 保存用户信息
     */
    private static ConcurrentHashMap<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();
    /**
     * 连接就绪时
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String query = webSocketSession.getUri().getQuery().split("=")[1];
        users.put(query,webSocketSession);
    }

    /**
     *处理信息
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        String msg = webSocketMessage.toString();
        String userId = this.getUserId(webSocketSession);
        System.err.println("该" + userId + "用户发送的消息是：" + msg);
        webSocketMessage = new TextMessage("服务端已经接收到消息，msg=" + msg);
        webSocketSession.sendMessage(webSocketMessage);
    }

    /**
     * 信息传输异常
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        log.error("APP的websocket异常信息：" + throwable.getMessage());
    }

    /**
     * 关闭连接
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
    
    }

    /**
     * 是否支持分包
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取userId
     * @param session
     * @return
     */
    private String getUserId(WebSocketSession session) {
        try {
            String userId = session.getId();
            return userId;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给某个用户发送消息
     *
     * @param userId
     * @param message
     */
    public void sendMessageToUser(String userId, TextMessage message) {
        WebSocketSession webSocketSession = users.get(userId);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            try {
                webSocketSession.sendMessage(message);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}