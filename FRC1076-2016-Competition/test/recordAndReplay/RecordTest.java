package recordAndReplay;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import org.junit.Test;
import org.usfirst.frc.team1076.robot.controllers.RecordController;
import org.usfirst.frc.team1076.test.mock.MockDriverInput;
import org.usfirst.frc.team1076.test.mock.MockOperatorInput;
import org.usfirst.frc.team1076.test.mock.MockRobot;

public class RecordTest {

    MockRobot robot;
    MockDriverInput driver = new MockDriverInput();
    MockOperatorInput operator = new MockOperatorInput();
    
    @Test
    public void createEmptyFileTest() {
        File file = new File("empty_test");
        RecordController record = new RecordController(file, driver, operator);
        record.startRecording();
        record.stopRecording();
        assertTrue("File should exist.", file.exists());
        assertTrue("Should be able to read the file.", file.canRead());
        assertTrue("Should be able to write to the file", file.canWrite());
        assertEquals("File name should be 'empty_test'", file.getName(), "empty_test");
    }
    
    @Test
    public void createSingleFrameTest() throws IOException, ClassNotFoundException {
        File file = new File("one_frame_test");
        RecordController record = new RecordController(file, driver, operator);
        record.startRecording();
        // Make some simple possible inputs
        driver.left = 1;
        driver.right = 1;
        operator.arm = 1;
        record.recordFrame();
        record.stopRecording();
        assertTrue("File should exist", file.exists());

        FileInputStream fis = new FileInputStream("one_frame_test");
        ObjectInputStream ois = new ObjectInputStream(fis);
        LinkedList<Object[]> frames = (LinkedList<Object[]>) ois.readObject();
        assertEquals("Exactly one frame should be in the output",
                1, frames.size());
        ois.close();
    }

    @Test 
    public void recorderThrowsExceptionTest() {
        File file = new File("test");
        RecordController record = new RecordController(file, driver, operator);
        assertFalse(record.isRecording());
        try {
            record.recordFrame();
        } catch (RuntimeException e) {  }
    }

}
