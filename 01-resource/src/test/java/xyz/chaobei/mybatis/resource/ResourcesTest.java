package xyz.chaobei.mybatis.resource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/4
 **/
public class ResourcesTest {

    private Logger logger = LoggerFactory.getLogger(ResourcesTest.class);

    @Test
    public void getResourceAsStream() {
        InputStream inputStream = Resources.getResourceAsStream("test.txt");
        logger.info("inputStream={}", inputStream == null);
    }
}