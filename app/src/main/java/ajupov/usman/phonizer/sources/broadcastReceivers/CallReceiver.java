package ajupov.usman.phonizer.sources.broadcastReceivers;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import ajupov.usman.phonizer.sources.enums.WebSocketClientType;
import ajupov.usman.phonizer.sources.enums.WebSocketMessageType;
import ajupov.usman.phonizer.sources.handlers.WebSocketHandler;
import ajupov.usman.phonizer.sources.models.WebSocketMessage;

public class CallReceiver extends BroadcastReceiver {
    private static final String ACTION_NAME = "android.intent.action.PHONE_STATE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_NAME)) {
            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            //Входящий вызов (трубка не поднята)
            if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                WebSocketMessage message = new WebSocketMessage(
                        WebSocketMessageType.ClientSendPhoneNumber,
                        WebSocketClientType.AndroidClient,
                        phoneNumber
                );

                WebSocketHandler.sendToServer(message);

            //В режиме набор номера / разговор
            } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

            //В ждущем режиме
            } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            }
        }
    }
}