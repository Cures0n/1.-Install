import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File dir1 = new File("D://Games", "src");
        File dir2 = new File("D://Games", "res");
        File dir3 = new File("D://Games", "savegames");
        File dir4 = new File("D://Games", "temp");
        File dir11 = new File("D://Games/src", "main");
        File dir12 = new File("D://Games/src", "test");
        File fileMain1 = new File("D://Games/src/main", "Main.java");
        File fileMain2 = new File("D://Games/src/main", "Utils.java");
        File dirRes1 = new File("D://Games/res", "drawables");
        File dirRes2 = new File("D://Games/res", "vectors");
        File dirRes3 = new File("D://Games/res", "icons");
        File tmp = new File("D://Games/temp","temp.txt");

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(createFolder(dir4));
        stringBuilder.append(createFile(tmp));
        stringBuilder.append(createFolder(dir3));
        stringBuilder.append(createFolder(dir11));
        stringBuilder.append(createFolder(dir12));
        stringBuilder.append(createFolder(dirRes1));
        stringBuilder.append(createFolder(dirRes2));
        stringBuilder.append(createFolder(dirRes3));
        stringBuilder.append(createFile(fileMain1));
        stringBuilder.append(createFile(fileMain2));

        FileWriter fw = new FileWriter(tmp.getPath());
        fw.write(stringBuilder.toString());
        fw.flush();
    }

    public static String createFolder(File dir) {
        if (dir.mkdirs()) {
            return String.valueOf(dir + " Созданы" + "\n");

        } else {
            return String.valueOf(dir + " уже существуют" + "\n");
        }
    }

    public static Object createFile (File file) {
        try {
            if (file.createNewFile()){
                return String.valueOf(file + " Созданы" + "\n");
            } else {
                return String.valueOf(file + " уже существуют" + "\n");
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
        return null;
    }
}
