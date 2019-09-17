package by.qulixsystem.practice;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Класс который считывает из файла информацию в поток.
 */
public class FileManager {
    private static Logger logger = Logger.getLogger(FileManager.class.getName());
    private Path path;

    public Path getPath() {
        return path;
    }



    public FileManager(Path path) {
        this.path = path;
    }

    /**
     * Get characters in stream.
     * @return characters in stream
     */
    public Stream<Character> readFileCharStream(){
        try {
            List<String> inputFile = Files.readAllLines(path);
            String text = convertListToString(inputFile);
            return  text.chars().mapToObj(c -> (char) c);
        } catch (IOException ex){
            logger.info("Not found path");
         //   ex.printStackTrace();
        }
        return null;
    }

    /**
     * Get strings in stream.
     * @return strings in stream
     */
    public Stream<String> readFileWordStream(){
        try {
            List<String> inputFile = Files.readAllLines(path);
            inputFile = Arrays.asList(convertListToString(inputFile).split(" "));
            return  inputFile.stream();
        } catch (IOException ex){
            logger.info("File not found");
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Converts list to string.
     * @param list
     * @return full string
     */
    public String convertListToString(List<String> list){
        String text = new String();
        if (list.size() != 0){
            for (String item: list) {
                text += " " + item;
            }
        }
        return  text;
    }

}
