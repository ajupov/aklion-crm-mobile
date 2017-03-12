package ajupov.usman.phonizer.sources.helpers;
import android.content.Context;
import java.io.IOException;

public class SettingsHelper {
    private static final String SETTINGS_FILE_NAME  = "AklionCliSettings.bin";

    public static void saveAddress(Context context, String address) throws IOException {
        FileHelper.save(context, address, SETTINGS_FILE_NAME);
    }

    public static String getAddress(Context context) throws IOException, ClassNotFoundException {
        return (String)FileHelper.read(context, SETTINGS_FILE_NAME);
    }
}