package ajupov.usman.phonizer.sources.handlers;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import ajupov.usman.phonizer.sources.enums.WebSocketClientType;
import ajupov.usman.phonizer.sources.enums.WebSocketMessageType;
import ajupov.usman.phonizer.sources.helpers.UriHelper;
import ajupov.usman.phonizer.sources.models.WebSocketMessage;

public class WebSocketHandler {
    private static WebSocketClient webSocketClient;
    private static Handler handler;

    public static void initialize(String uriString, final Context context) {
        handler = new Handler(context.getMainLooper());

        URI uri = UriHelper.convertFromString(uriString);
        if (uri == null) {
            return;
        }

        webSocketClient = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake handshakeData) {
                String deviceInfo = Build.MODEL;

                WebSocketMessage helloMessage = new WebSocketMessage(
                        WebSocketMessageType.ClientSendHello,
                        WebSocketClientType.AndroidClient,
                        deviceInfo);

                sendToServer(helloMessage);
            }

            @Override
            public void onMessage(String message) {
                final WebSocketMessage socketMessage = WebSocketMessage.toWebSocketMessage(message);
                if (socketMessage == null) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (socketMessage.messageType == WebSocketMessageType.ServerSendHello) {
                            Toast.makeText(context, "Получен номер: " + socketMessage.data, Toast.LENGTH_SHORT).show();
                        } else if (socketMessage.messageType == WebSocketMessageType.ServerSendPhoneNumber) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + socketMessage.data));
                                context.startActivity(intent);
                            } catch (SecurityException e){
                                Toast.makeText(context, "Нет доступа к совершению вызова", Toast.LENGTH_SHORT).show();
                            } catch (Exception e){
                                Toast.makeText(context, "Произошла ошибка при совершении вызова", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Отключено", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception ex) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        webSocketClient.connect();
    }

    public static void sendToServer(WebSocketMessage message){
        try {
            webSocketClient.send(message.toJsonString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void runOnUiThread(Runnable r) {
        handler.post(r);
    }
}