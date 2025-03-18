package FFO.cli;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author tortoise
 * @Date 2025/3/12 15:11
 * @PackageName:FFO.cli
 * @ClassName: CommandLineParserTest
 * @Description:
 * @Version 1.0
 */
class CommandLineParserTest {
    @Test
    void testGenerateModeValid() {
        System.out.println("测试生成模式，参数正确...");
        String[] args = {"-n", "10", "-r", "100"};
        CommandLineParser parser = new CommandLineParser(args);

        try {
            assertFalse(parser.hasError(), "参数应无错误");
            assertTrue(parser.isGenerateMode(), "应为生成模式");
            assertEquals(10, parser.getNumQuestions(), "题目数量应为 10");
            assertEquals(100, parser.getRange(), "数值范围应为 100");
            System.out.println("测试通过：生成模式参数正确。");
        } catch (AssertionFailedError e) {
            System.err.println("测试失败：" + e.getMessage());
            throw e; // 重新抛出异常，确保测试标记为失败
        }
    }


}