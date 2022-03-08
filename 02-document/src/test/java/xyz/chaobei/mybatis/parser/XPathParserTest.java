package xyz.chaobei.mybatis.parser;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/4
 **/
class XPathParserTest {

    private final Logger logger = LoggerFactory.getLogger(XPathParserTest.class);

    @Test
    void createDocument() throws FileNotFoundException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("config.xml");
        logger.info("inputSteam ={}", stream == null);

        XPathParser xPathParser = new XPathParser(stream);
        Document document = xPathParser.getDocument();

        logger.info("inputSteam getXmlVersion={}", document.getXmlVersion());
        logger.info("inputSteam configuration={}", document.getElementsByTagName("configuration").item(0));
    }


}