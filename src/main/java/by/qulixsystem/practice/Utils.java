package by.qulixsystem.practice;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Вспомогательный класс, содержит дополнительные функции
 * для реализации задания.
 */
public class Utils {

    private static Logger logger = Logger.getLogger(Utils.class.getName());

    /**
     * There are files in this directory.
     * @param path path directory
     * @return list paths in files.
     */
    public List<Path> getСontentsDirectory(String path) {
        Path dir = Paths.get(path);
        List<Path> directoryList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                directoryList.add(file);
            }
            return directoryList;
        } catch (IOException | DirectoryIteratorException ex) {
            ex.printStackTrace();
        }
        return directoryList;
    }

    /**
     * Match regex mask.
     * @param keyMask key -mask-<expansion>
     * @return
     */
    public boolean isMask(String keyMask){
        Pattern patternMask = Pattern.compile("-mask-\\.\\w*\\b");
        return patternMask.matcher(keyMask).matches();
    }

    /**
     * Match regex mask.
     * @param keyThread key -thread-<count>
     * @return
     */
    public boolean isThread(String keyThread){
        Pattern patternThread = Pattern.compile("-thread-\\d*\\b");
        return patternThread.matcher(keyThread).matches();
    }


    /**
     * There are a lot threads in this threadKey.
     * @param threadKey
     * @return count thread;
     */
    public int getCountThread(String threadKey){
        Pattern p = Pattern.compile("\\d");
        Matcher matcher = p.matcher(threadKey);
        String countThread = "";
        while (matcher.find()) {
            countThread += matcher.group(0);
        }
        return Integer.valueOf(countThread);
    }

    /**
     * Get file expansion.
     * @param filename name file
     * @return expansion (exmp: ".txt"; ".doc"; ".pdf")
     */
    public String getExpansion(String filename) {
        int index = filename.indexOf('.');
        return filename.substring(index);
    }

    public boolean isExists(HandlingFile file){
        String path_file_work = file.getManager().getPath().toString();
        if (!new File(path_file_work).isFile()){
            return false;
        }
        Path path = Paths.get(path_file_work
                .substring(0,path_file_work.indexOf(".")) + ".start");
        if (Files.exists(path)){
            logger.info("File  " + path_file_work.substring(path_file_work.lastIndexOf("\\"))
                    + " exists." );
            return true;
        }return false;
    }
}
