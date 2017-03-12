package ajupov.usman.phonizer.sources.activities;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import ajupov.usman.phonizer.R;
import ajupov.usman.phonizer.sources.enums.WebSocketClientType;
import ajupov.usman.phonizer.sources.enums.WebSocketMessageType;
import ajupov.usman.phonizer.sources.helpers.SettingsHelper;
import ajupov.usman.phonizer.sources.handlers.WebSocketHandler;
import ajupov.usman.phonizer.sources.models.WebSocketMessage;

public class ConnectActivity extends AppCompatActivity {
    private EditText editTextUri;
    private CheckBox checkBoxRemember;
    private Button buttonConnect;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityParameters();
        bindControls();
        bindEventListeners();
        loadAddress();
    }

    private void setActivityParameters() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.activity_connect);
    }

    private void bindControls() {
        editTextUri = (EditText) findViewById(R.id.editTextUri);
        checkBoxRemember = (CheckBox) findViewById(R.id.checkBoxRemember);
        buttonConnect = (Button) findViewById(R.id.buttonConnect);
        buttonSend = (Button) findViewById(R.id.buttonSend);
    }

    private void bindEventListeners() {
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String address = editTextUri.getText().toString();

                    if (checkBoxRemember.isChecked()) {
                        SettingsHelper.saveAddress(getApplicationContext(), address);
                    }

                    WebSocketHandler.initialize(address, getApplicationContext());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Произошла ошибка при подключении", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String deviceInfo = Build.MODEL;

                    WebSocketMessage helloMessage = new WebSocketMessage(
                            WebSocketMessageType.ClientSendHello,
                            WebSocketClientType.AndroidClient,
                            deviceInfo);

                    WebSocketHandler.sendToServer(helloMessage);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Произошла ошибка при тестировании", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadAddress() {
        try {
            String address = SettingsHelper.getAddress(getApplicationContext());
            if (address != null) {
                editTextUri.setText(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}