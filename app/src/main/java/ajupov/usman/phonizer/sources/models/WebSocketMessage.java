package ajupov.usman.phonizer.sources.models;
import android.support.annotation.Nullable;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import ajupov.usman.phonizer.sources.enums.WebSocketClientType;
import ajupov.usman.phonizer.sources.enums.WebSocketMessageType;

public class WebSocketMessage {
    public WebSocketMessageType messageType;
    public WebSocketClientType clientType;
    public String data;

    public WebSocketMessage(){
    }

    public WebSocketMessage(WebSocketMessageType messageType, WebSocketClientType clientType, String data){
        this.messageType = messageType;
        this.clientType = clientType;
        this.data = data;
    }

    public String toJsonString(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("MessageType", messageType.ordinal());
            jsonObject.put("ClientType", clientType.ordinal());
            jsonObject.put("Data", data);
        } catch (Exception e) {
        }

        return jsonObject.toString();
    }

    @Nullable
    public static WebSocketMessage toWebSocketMessage(String data){
        try {
            JSONParser parser = new JSONParser();
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(data);

            WebSocketMessage webSocketMessage = new WebSocketMessage();
            webSocketMessage.messageType =  WebSocketMessageType.values()[((Long) jsonObject.get("MessageType")).intValue()];
            webSocketMessage.clientType =  WebSocketClientType.values()[((Long) jsonObject.get("ClientType")).intValue()];
            webSocketMessage.data = jsonObject.get("Data").toString();

            return webSocketMessage;
        } catch (Exception e) {
            return null;
        }
    }
}