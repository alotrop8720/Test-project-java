package by.qulixsystem.practice;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс с доп библиотекой, который ловит нажатия на клавиши.
 */
public class KeyListenerClass implements NativeKeyListener{

    private static Logger logger = Logger.getLogger(KeyListenerClass.class.getName());

    /**
     * 	GlobalScreen is enabled. Listen user.
     */
    public static void StartGlobalScreen(){
        try {
            Logger log = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            log.setLevel(Level.OFF);
            GlobalScreen.registerNativeHook();

        }
        catch (NativeHookException ex) {
            logger.warning("There was a problem registering the native hook.");
            ex.printStackTrace();
        }
    }


    boolean flagPress = false;
    public static boolean flagWait = false;
    /**
     * Esc/space is press.
     * @param e keypress;
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
       logger.info("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
                System.exit(0);

            } catch (NativeHookException ex) {
                logger.warning("There was a problem unregister the native hook.");
                ex.printStackTrace();
            }
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_SPACE) {
            if (!flagPress){
                flagPress = true;
                logger.info("Press: Stop");
                flagWait = true;
            }
            else{
                flagPress = false;
                logger.info("Press: Start");
                flagWait = false;
                new Thread(new ThreadFilesParsing()).start();
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

}
