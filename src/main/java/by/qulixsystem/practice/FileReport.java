package by.qulixsystem.practice;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.lang.Thread.MAX_PRIORITY;
import static java.nio.file.StandardOpenOption.*;

/**
 * Класс который находит все необходимые данные(файл или директорию,
 * какие маски нужно применить). Запускает обработку.
 */
public class FileReport {
    private static Logger logger = Logger.getLogger(FileReport.class.getName());

    private HandlingFile handlingFile;
    private String[] parameters;
    private String format;
    private int countThreads;
    private Utils utils;

    /**
     * Shell out program.
     * @return
     */

    public FileReport(String[] parameters) {
        this.parameters = parameters;
        utils = new Utils();

        if ( (parameters.length == 2) ) {
            if (utils.isMask(parameters[1])) {
                this.format = utils.getExpansion(parameters[1]);
                this.countThreads = 1;
            }
            if (utils.isThread(parameters[1])) {
                this.countThreads = utils.getCountThread(parameters[1]) + 1;
                this.format  = "*";
            }
        }else if ( (parameters.length == 3) ) {
            if (utils.isMask(parameters[1]))
                this.format = utils.getExpansion(parameters[1]);

            if (utils.isThread(parameters[2]))
                this.countThreads = utils.getCountThread(parameters[2]) + 1;
        } else {
            this.format  = "*";
            this.countThreads = 1;
        }
    }

    public boolean handlingFiles(){
        try{
            if (!new File(parameters[0]).exists()){
                logger.info("Not found path");
                return false;
            }
            if (new File(parameters[0]).isFile()){
                handlingFile = new HandlingFile(new FileManager(Paths.get(parameters[0])));
                fileWrite();
            }
            if (new File(parameters[0]).isDirectory()){
                List<Path> paths = utils.getСontentsDirectory(parameters[0]);

                if (!format.equals("*")) {
                    List<Path> pathInFormat = new ArrayList<>();
                    for (Path item : paths) {
                        if (utils.getExpansion(item.toString()).equals(format)) {
                            pathInFormat.add(item);
                        }
                    }
                    paths = pathInFormat;
                }


                if (paths.size() < countThreads){
                    countThreads = paths.size() + 1;
                }
                ExecutorService executor = Executors.newFixedThreadPool(countThreads);

                for (Path item: paths) {
                    handlingFile = new HandlingFile(new FileManager(item));
                    if (!utils.isExists(handlingFile)){
                        if (countThreads > 1) {
                            executor.submit(new ThreadFilesParsing(handlingFile));
                            countThreads--;
                        } else {
                            fileWrite();
                        }
                    }
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(MAX_PRIORITY, TimeUnit.HOURS);
                }catch (InterruptedException ex){}
            }
        } catch (ArrayIndexOutOfBoundsException ex){
            logger.info("Not found path");
            return false;
        }
        return true;
    }



    /**
     * Write and create report.
     */
    private void fileWrite(){
        String path_file_work = "";
        try{
            byte[] inf = (handlingFile.SharedInformation() +"\n\n"+ handlingFile.TableChar() +"\n"+
                    (handlingFile.TypeFiles().equals("unicode") ? " ": handlingFile.TableWord()) +
                    "\n"+ handlingFile.TimeFileProcessing()).getBytes();
            OpenOption[] options = new OpenOption[] { WRITE, TRUNCATE_EXISTING, CREATE};

            path_file_work = handlingFile.getManager().getPath().toString();
            Path path = Paths.get(path_file_work
                    .substring(0,path_file_work.indexOf(".")) + ".start");
            if (!Files.exists(path)){
                Files.write(path, inf, options);
                logger.info("File  " + path_file_work.substring(path_file_work.lastIndexOf("\\"))
                        + " is handle." );
            }
        } catch (IOException | NullPointerException ex){
            logger.info("File  " + path_file_work.substring(path_file_work.lastIndexOf("\\")) + "  exists!!!" );
        }

    }
}
