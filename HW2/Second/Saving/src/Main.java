import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress firstGameProgress = new GameProgress(100, 21, 11, 567);
        GameProgress secondGameProgress = new GameProgress(87, 36, 16, 874);
        GameProgress thirdGameProgress = new GameProgress(12, 7, 5, 238);

        saveGame("C://Games/savegames/save1.dat", firstGameProgress);
        saveGame("C://Games/savegames/save2.dat", secondGameProgress);
        saveGame("C://Games/savegames/save3.dat", thirdGameProgress);

        List<String> files = Arrays.asList("C://Games/savegames/save1.dat",
                                            "C://Games/savegames/save2.dat",
                                            "C://Games/savegames/save3.dat");
        zipFiles("C://Games/savegames/saves.zip", files);

        File first = new File("C://Games/savegames/save1.dat");
        File second = new File("C://Games/savegames/save2.dat");
        File third = new File("C://Games/savegames/save3.dat");
        if (first.delete()) {
            System.out.println("C://Games/savegames/save1.dat deleted");
        }
        if (second.delete()) {
            System.out.println("C://Games/savegames/save2.dat deleted");
        }
        if (third.delete()) {
            System.out.println("C://Games/savegames/save3.dat deleted");
        }
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> files) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (String file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry("packed_" + fileName(file));
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String fileName(String path) {
        int it = path.length() - 1;
        while (it > 0 && path.charAt(it) != '/') {
            --it;
        }
        StringBuilder res = new StringBuilder();
        while (it < path.length() - 1) {
            ++it;
            res.append(path.charAt(it));
        }
        return res.toString();
    }
}
