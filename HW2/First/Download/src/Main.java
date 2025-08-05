import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();
        String gamesPath = "C://Games";
        List<String> gamesDirsNames = Arrays.asList("src", "res", "savegames", "temp");
        List<File> gamesDirs = listDirsCreation(gamesPath, gamesDirsNames, log);

        String srcPath = gamesDirs.get(0).toString();   // get path of src
        List<String> srcDirsNames = Arrays.asList("main", "test");
        List<File> srcDirs = listDirsCreation(srcPath, srcDirsNames, log);

        String mainPath = srcDirs.get(0).toString();
        List<String> mainFilesNames = Arrays.asList("Main.java", "Utils.java");
        List<File> mainFiles = listFilesCreation(mainPath, mainFilesNames, log);

        String resPath = gamesDirs.get(1).toString();
        List<String> resDirsNames = Arrays.asList("drawables", "vectors", "icons");
        List<File> resDirs = listDirsCreation(resPath, resDirsNames, log);

        String tempPath = gamesDirs.get(3).toString();
        List<String> tempFilesNames = Arrays.asList("temp.txt");
        List<File> tempFiles = listFilesCreation(tempPath, tempFilesNames, log);

        logging(tempPath, "temp.txt", log);
    }

    public static List<File> listDirsCreation(String path, List<String> names, StringBuilder log) {
        List<File> res = new ArrayList<>();
        for (String dirName : names) {
            res.add(dirCreation(path, dirName, log));
        }
        return res;
    }

    public static List<File> listFilesCreation(String path, List<String> names, StringBuilder log) {
        List<File> res = new ArrayList<>();
        for (String fileName : names) {
            res.add(fileCreation(path, fileName, log));
        }
        return res;
    }

    public static File dirCreation(String path, String dirName, StringBuilder log) {
        File res = new File(path, dirName);
        if (res.mkdir()) {
            log.append((String) (path + '/' + dirName + " created.\n"));
        }
        return res;
    }

    public static File fileCreation(String path, String fileName, StringBuilder log) {
        File res = new File(path, fileName);
        try {
            if (res.createNewFile()) {
                log.append((String) (path + '/' + fileName + " created.\n"));
            }
        } catch (IOException e) {
            log.append((String) ("Error: " + e.getMessage() + '\n'));
        }
        return res;
    }

    public static void logging(String path, String fileName, StringBuilder log) {
        String tempTxt = new StringBuilder(path).append('/').append(fileName).toString();
        try (FileWriter fw = new FileWriter(tempTxt, false)) {
            fw.write(log.toString());
            fw.flush();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage() + '\n');
        }
    }
}
