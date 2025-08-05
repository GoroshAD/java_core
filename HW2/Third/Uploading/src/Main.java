import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        openZip("C://Games/savegames/saves.zip", "C://Games/savegames");

        GameProgress first = openProgress("C://Games/savegames/save1.dat");
        GameProgress second = openProgress("C://Games/savegames/save2.dat");
        GameProgress third = openProgress("C://Games/savegames/save3.dat");

        System.out.println(first);
        System.out.println(second);
        System.out.println(third);
    }

    public static void openZip(String path, String folder) {
        try (FileInputStream fis = new FileInputStream(path);
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fos = new FileOutputStream(folder + '/' + unpackedName(name));
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fos.write(c);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static GameProgress openProgress(String path) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return gameProgress;
    }

    public static String unpackedName(String name) {
        StringBuilder res = new StringBuilder();
        int underlineNumber = name.indexOf('_');
        for (int it = underlineNumber + 1; it < name.length(); ++it) {
            res.append(name.charAt(it));
        }
        return res.toString();
    }
}
