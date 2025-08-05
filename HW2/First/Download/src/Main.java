import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();

        File src = new File("C://Games", "src");
        File res = new File("C://Games", "res");
        File savegames = new File("C://Games", "savegames");
        File temp = new File("C://Games", "temp");
        if (src.mkdir()) {
            log.append("C://Games/src created\n");
        }
        if (res.mkdir()) {
            log.append("C://Games/res created\n");
        }
        if (savegames.mkdir()) {
            log.append("C://Games/savegames created\n");
        }
        if (temp.mkdir()) {
            log.append("C://Games/temp created\n");
        }

        File main = new File(src, "main");
        File test = new File(src, "test");
        if (main.mkdir()) {
            log.append("C://Games/src/main created\n");
        }
        if (test.mkdir()) {
            log.append("C://Games/src/test created\n");
        }

        File mainJava = new File(main, "Main.java");
        File utilsJava = new File(main, "Utils.java");
        try {
            if (mainJava.createNewFile()) {
                log.append("C://Games/src/main/Main.java created\n");
            }
            if (utilsJava.createNewFile()) {
                log.append("C://Games/src/main/Utils.java created\n");
            }
        } catch (IOException e) {
            log.append((String) ("Error: " + e.getMessage()));
        }

        File drawables = new File(res, "drawables");
        File vectors = new File(res, "vectors");
        File icons = new File(res, "icons");
        if (drawables.mkdir()) {
            log.append("C://Games/res/drawables created\n");
        }
        if (vectors.mkdir()) {
            log.append("C://Games/res/vectors created\n");
        }
        if (icons.mkdir()) {
            log.append("C://Games/res/icons created\n");
        }

        File tempTxt = new File(temp, "temp.txt");
        try {
            if (tempTxt.createNewFile()) {
                log.append("C://Games/temp/temp.txt created\n");
            }
        } catch (IOException e) {
            log.append((String) ("Error: " + e.getMessage()));
        }

        try (FileWriter fw = new FileWriter(tempTxt, false)) {
            fw.write(log.toString());
            fw.flush();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
