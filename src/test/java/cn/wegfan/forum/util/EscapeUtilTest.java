package cn.wegfan.forum.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试 {@link EscapeUtil} 工具类
 */
@Slf4j
class EscapeUtilTest {

    @Test
    void testEscapeSqlLike() {
        Map<String, String> testDataSet = new HashMap<>();

        testDataSet.put("___aaa__", "\\_\\_\\_aaa\\_\\_");
        testDataSet.put("%%%", "\\%\\%\\%");
        testDataSet.put("\\ % \\ _ \\ qqq", "\\\\ \\% \\\\ \\_ \\\\ qqq");

        for (Map.Entry<String, String> entry : testDataSet.entrySet()) {
            String original = entry.getKey();
            String actual = EscapeUtil.escapeSqlLike(original);
            String expected = entry.getValue();
            log.info("testing [{}], actual [{}], expected [{}]", original, actual, expected);
            Assertions.assertEquals(expected, actual);
        }
    }

}