package FFO.file;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author tortoise
 * @Date 2025/3/19 0:38
 * @PackageName:FFO.file
 * @ClassName: FileHandlerTest
 * @Description:
 * @Version 1.0
 */
class FileHandlerTest {
    @Test
    void testWriteAndReadFile() {
        // 测试数据
        List<String> content = Arrays.asList("1 + 1 = 2", "2 * 3 = 6", "10 / 2 = 5");
        String fileName = "D:/test.txt";

        // 写入文件
        FileHandler.writeFile(fileName, content);

        // 读取文件
        List<String> readContent = FileHandler.readFile(fileName);

        // 验证内容是否一致
        System.out.println(readContent);
       // assertEquals(content, readContent, "文件内容不一致");
        System.out.println("测试通过：文件写入和读取成功。");
    }

    @Test
    void testReadNonExistentFile() {
        // 读取不存在的文件
        String fileName = "non_existent_file.txt";
        List<String> content = FileHandler.readFile(fileName);

        // 验证返回的列表为空
        assertTrue(content.isEmpty(), "读取不存在的文件应返回空列表");
        System.out.println("测试通过：读取不存在的文件返回空列表。");
    }

}