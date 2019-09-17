package by.qulixsystem.practice;

import org.jnativehook.GlobalScreen;

import java.io.File;

public class App {

    public static void main( String[] args ) {

        KeyListenerClass.StartGlobalScreen();
        GlobalScreen.addNativeKeyListener(new KeyListenerClass());

        FileReport f  =  new FileReport(args);

        if (f.handlingFiles()){
            System.exit(0);
        }

    }

}
