package FFO.cli;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author tortoise
 * @Date 2025/3/11 0:27
 * @PackageName:cli
 * @ClassName: CommandLineParser
 * @Description:命令行参数处理模块
 * @Version 1.0
 */
public class CommandLineParser {
    private final Map<String, String> params = new HashMap<>();
    private boolean hasError = false;

    public CommandLineParser(String[] args) {
        parseArgs(args);
    }

    private void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-n":
                case "-r":
                case "-e":
                case "-a":
                    if (i + 1 < args.length) {
                        params.put(args[i], args[i + 1]);
                        i++;
                    } else {
                        hasError = true;
                    }
                    break;
                default:
                    hasError = true;
                    break;
            }
        }
        validateArgs();
    }

    private void validateArgs() {
        if (isGenerateMode()) {
            if (!params.containsKey("-n") || !params.containsKey("-r")) {
                hasError = true;
            }
        } else if (isGradeMode()) {
            if (!params.containsKey("-e") || !params.containsKey("-a")) {
                hasError = true;
            }
        } else {
            hasError = true;
        }
    }

    public boolean hasError() {
        return hasError;
    }

    public String getHelpMessage() {
        return "使用方法：\n" +
                "  生成模式：Myapp.exe -n <题目数量> -r <数值范围>\n" +
                "  评分模式：Myapp.exe -e <题目文件> -a <答案文件>";
    }

    public boolean isGenerateMode() {
        return params.containsKey("-n") && params.containsKey("-r");
    }

    public boolean isGradeMode() {
        return params.containsKey("-e") && params.containsKey("-a");
    }

    public int getNumQuestions() {
        return Integer.parseInt(params.getOrDefault("-n", "0"));
    }

    public int getRange() {
        return Integer.parseInt(params.getOrDefault("-r", "0"));
    }

    public String getExerciseFile() {
        return params.getOrDefault("-e", "");
    }

    public String getAnswerFile() {
        return params.getOrDefault("-a", "");
    }
}
