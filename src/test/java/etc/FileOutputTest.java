package etc;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


//@SpringBootTest(properties = "classpath:application-test.properties")
public class FileOutputTest {

    @Test
    void fileGenerateTest() throws IOException {
        String userHome = System.getProperty("user.home");
        assertEquals("/Users/joel.silver", userHome);
        String path =  userHome + "/kakao/temp/";
//        String filename = "test.txt";
        String filename = "test.pdf";
        File folder = new File(path);
        if (!folder.exists()) {
            boolean isMade = folder.mkdir();
            System.out.println("디렉터리가 생성되었습니다." + isMade);
        }
        File file = new File(path + filename);
        try {
            final FileOutputStream outputStream = new FileOutputStream(file);
            int i = 0;
            while (true) {
                if (i > 5) break;
                byte[] bytes = "hello world\n ".getBytes();
                outputStream.write(bytes);
                i++;
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
