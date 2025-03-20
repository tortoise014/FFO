package FFO.generator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class generate {
    @Test
    public void testAg() {
        List<String> list = ArithmeticGenerator.generate(10,3);
        System.out.println(list);

    }

}
