package by.qulixsystem.practice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void validationArguments()
    {
        String[] args = {};
        FileReport fileReport = new FileReport(args);
        assertFalse(fileReport.handlingFiles());
    }

    @Test
    public void startProgramWithOneArgument()
    {
        String[] args = {System.getProperty("user.dir") + "\\src\\test\\java\\by\\qulixsystem\\practice\\files"};
        FileReport fileReport = new FileReport(args);
        assertTrue(fileReport.handlingFiles());
    }


    @Test
    public void startProgramWithMask()
    {
        String[] args = {System.getProperty("user.dir") + "\\src\\test\\java\\by\\qulixsystem\\practice\\files",
                "-mask-.txt"};
        FileReport fileReport = new FileReport(args);
        assertTrue(fileReport.handlingFiles());
    }

    @Test
    public void startProgramWithThread()
    {
        String[] args = {System.getProperty("user.dir") + "\\src\\test\\java\\by\\qulixsystem\\practice\\files",
                "-thread-3"};
        FileReport fileReport = new FileReport(args);
        assertTrue(fileReport.handlingFiles());

    }

    @Test
    public void startProgramWithTwiceArgument()
    {
        String[] args = {System.getProperty("user.dir") + "\\src\\test\\java\\by\\qulixsystem\\practice\\files",
                "-mask-.txt", "-thread-3"};
        FileReport fileReport = new FileReport(args);
        assertTrue(fileReport.handlingFiles());
    }

}
