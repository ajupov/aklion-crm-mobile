package ajupov.usman.phonizer.sources.helpers;
import android.support.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;

public class UriHelper {

    @Nullable
    public static URI convertFromString(String uriString) {
        URI uri;

        try {
            uri = new URI(uriString);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        return uri;
    }
}