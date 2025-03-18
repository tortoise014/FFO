package FFO.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author tortoise
 * @Date 2025/3/11 0:26
 * @PackageName:file
 * @ClassName: FileHandler
 * @Description:文件读写模块
 * @Version 1.0
 */
public class FileHandler {
    /**
     * 将内容写入指定文件。
     *
     * @param fileName 文件名（可以是相对路径或绝对路径）
     * @param content  要写入的内容列表
     */
    public static void writeFile(String fileName, List<String> content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : content) {
                writer.write(line);
                writer.newLine(); // 换行
            }
            System.out.println("文件写入成功：" + fileName);
        } catch (IOException e) {
            System.err.println("文件写入失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 从指定文件中读取内容并返回列表。
     *
     * @param fileName 文件名（可以是相对路径或绝对路径）
     * @return 文件内容的列表
     */
    public static List<String> readFile(String fileName) {
        List<String> content = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.add(line);
            }
            System.out.println("文件读取成功：" + fileName);
        } catch (IOException e) {
            System.err.println("文件读取失败：" + e.getMessage());
            e.printStackTrace();
        }
        return content;
    }
}

