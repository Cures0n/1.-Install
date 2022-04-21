import java.io.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) throws IOException {
        // 1ое задание
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

        createFolder(dir4);
        createFile(tmp);
        createFolder(dir3);
        createFolder(dir11);
        createFolder(dir12);
        createFolder(dirRes1);
        createFolder(dirRes2);
        createFolder(dirRes3);
        createFile(fileMain1);
        createFile(fileMain2);

        FileWriter fw = new FileWriter(tmp.getPath());

        //2-ое задание
        GameProgress user1 = new GameProgress(100, 5, 10, 34.51d);
        GameProgress user2 = new GameProgress(55, 1, 30, 88.25d);
        GameProgress user3 = new GameProgress(15, 3, 55, 208.20d);
        List<String> savedFiles = new ArrayList<>();

        saveGame(String.valueOf(dir3) + "/save1.dat", user1);
        savedFiles.add(String.valueOf(dir3) + "/save1.dat");

        saveGame(String.valueOf(dir3) + "/save2.dat", user2);
        savedFiles.add(String.valueOf(dir3) + "/save2.dat");

        saveGame(String.valueOf(dir3) + "/save3.dat", user3);
        savedFiles.add(String.valueOf(dir3) + "/save3.dat");

        System.out.println(savedFiles);

        zipFiles("D://Games/savegames/gamesave.zip", savedFiles);
        deleteUnarchivedFiles(savedFiles);

        fw.write(stringBuilder.toString());
        fw.flush();
    }

    public static void createFolder(File dir) {
        if (dir.mkdirs()) {
            stringBuilder.append(String.valueOf(dir + " Созданы" + "\n"));

        } else {
            stringBuilder.append(String.valueOf(dir + " уже существуют" + "\n"));
        }
    }

    public static void createFile (File file) {
        try {
            if (file.createNewFile()){
                stringBuilder.append(String.valueOf(file + " Созданы" + "\n"));
            } else {
                stringBuilder.append(String.valueOf(file + " уже существуют" + "\n"));
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            stringBuilder.append("\n"+
                    "Игра: " + gameProgress.hashCode() +
                    " успешно сохранена в " + path);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            stringBuilder.append("\n" +
                    "Ошибка сохранения игры: " + gameProgress.hashCode() +
                    ". " + ex.getMessage());
        }
    }

    public static void zipFiles(String archivePath, List<String> fileList) {
        ZipOutputStream zout = null;
        Deque<FileInputStream> fisList = new LinkedList<>();

        try {
            zout = new ZipOutputStream(new FileOutputStream(archivePath));

            for (String file : fileList) {
                fisList.add(new FileInputStream(file));
                ZipEntry entry = new ZipEntry(file);
                zout.putNextEntry(entry);

                byte[] buffer = new byte[fisList.peekLast().available()];
                fisList.peekLast().read(buffer);
                zout.write(buffer);
                zout.closeEntry();

                stringBuilder.append("\n" +
                        "Файл " + file + " добавлен в архив " + archivePath);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                for (FileInputStream fis : fisList) {
                    if (fis != null) fis.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                stringBuilder.append("\n" +
                        " Ошибка при попытке закрыть поток(и) чтения из файлов сохранения игры" +
                        ex.getMessage());
            }

            try {
                if (zout != null) zout.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                stringBuilder.append("\n" +
                        " Ошибка при попытке закрыть поток выводв zip архив" +
                        ex.getMessage());
            }
        }
    }

    public static void deleteUnarchivedFiles(List<String> fileList) {
        for (String file : fileList) {
            if (new File(file).delete()) {
                stringBuilder.append("\n" +
                        "Файл " + file + " удален");
            } else {
                stringBuilder.append("\n" +
                        "Не удалось удалить файл " + file);
            }
        }
    }
}
