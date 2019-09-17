package by.qulixsystem.practice;

import org.junit.Test;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class UtilsTest {


    @Test
    public void getСontentsDirectoryTest()
    {
        Utils utils = new Utils();
        List<Path> list = utils.getСontentsDirectory(System.getProperty("user.dir")
                + "\\src\\test\\java\\by\\qulixsystem\\practice\\files");
        assertNotNull(list);
    }

    @Test
    public void getCountThreadTest()
    {
        Utils utils = new Utils();
        assertEquals(utils.getCountThread("-thread-80"),80);
    }

    @Test
    public void isExistsTest()
    {
        Utils utils = new Utils();
        String path = System.getProperty("user.dir")
                + "\\src\\test\\java\\by\\qulixsystem\\practice\\text.start";
        HandlingFile handlingFile = new HandlingFile(new FileManager(Paths.get(path)));
        assertTrue(utils.isExists(handlingFile));
    }


}
