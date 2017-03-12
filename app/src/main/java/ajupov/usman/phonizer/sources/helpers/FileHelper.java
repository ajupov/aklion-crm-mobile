package ajupov.usman.phonizer.sources.helpers;
import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHelper {
    public static void save(Context context, Object o, String filename) throws IOException {
        File file = new File(context.getFilesDir(), filename);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(o);
        oos.flush();
        oos.close();
    }

    public static Object read(Context context, String filename) throws IOException, ClassNotFoundException {
        File file = new File(context.getFilesDir(), filename);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        return ois.readObject();
    }
}