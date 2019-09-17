package by.qulixsystem.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.*;

/**
 * Класс реализующий поток который обрабатывает файл.
 */
public class ThreadFilesParsing extends Thread{
    private HandlingFile fileThread;
    private static Logger logger = Logger.getLogger(FileReport.class.getName());
    private static final class Lock { }
    private static Object lock = new Lock();


    public ThreadFilesParsing() {
    }

    public ThreadFilesParsing(HandlingFile fileThread) {
        this.fileThread = fileThread;
    }

    @Override
     public void run(){
        logger.info("Progress " + getName() + "....");
        if (fileThread != null){
            Handling();
            try {
                sleep(1000);
                synchronized (lock) {
                    while (KeyListenerClass.flagWait) {
                        lock.wait();
                    }
                }
            } catch (InterruptedException ex){
                logger.info("ERROR");
            }

        }
        else {
            synchronized (lock) {
                if (!KeyListenerClass.flagWait)
                    lock.notifyAll();
            }
        }
        logger.info("end " + getName() + ".");
     }

    /**
     *  Write and create report. Replicated copy FileWrite.
     */
    private void Handling(){
        String path_file_work = "";
        try{
            byte[] inf = (fileThread.SharedInformation() +"\n\n"+ fileThread.TableChar() +"\n"+
                    (fileThread.TypeFiles().equals("unicode") ? " ": fileThread.TableWord()) +
                    "\n"+ fileThread.TimeFileProcessing()).getBytes();
            OpenOption[] options = new OpenOption[] { WRITE, TRUNCATE_EXISTING, CREATE};
            path_file_work = fileThread.getManager().getPath().toString();
            Path path = Paths.get(path_file_work
                    .substring(0,path_file_work.indexOf(".")) + ".start");
            if (!Files.exists(path)){
                Files.write(path, inf, options);
                logger.info("File  " + path_file_work.substring(path_file_work.lastIndexOf("\\"))
                        + " is handle." );
            }
        } catch (IOException | NullPointerException ex){
            ex.printStackTrace();
            logger.info("File  " + path_file_work.substring(path_file_work.lastIndexOf("\\")) + "  exists!!!" );
        }
    }



}


