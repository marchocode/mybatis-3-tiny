package xyz.chaobei.mybatis.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.*;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * @description: 将字节流解析为document对象, 主要用到了
 * {@link  }
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/4
 **/
public class XPathParser {

    private final Logger logger = LoggerFactory.getLogger(XPathParser.class);

    private final Document document;
    /**
     * 是否需要验证
     */
    private boolean validation;

    private XPath xPath;

    public XPathParser(InputStream inputStream) {
        this(inputStream, false, null, null);
    }

    /**
     * <p>
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param inputStream    文件输入流
     * @param validation     是否验证，默认true
     * @param properties     键值对字段
     * @param entityResolver xml文件整理配置
     * @return
     * @since 2022/3/4
     **/
    public XPathParser(InputStream inputStream, boolean validation, Properties properties, EntityResolver entityResolver) {
        this.validation = validation;
        this.xPath = XPathFactory.newInstance().newXPath();
        this.document = createDocument(new InputSource(inputStream));
    }

    /**
     * <p>将输入流解析为document对象的主要方法
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param inputStream 输入文件流
     * @return org.w3c.dom.Document
     * @since 2022/3/4
     **/
    public Document createDocument(InputSource inputStream) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(this.validation);

            /**
             *     private boolean validating = false;
             *     private boolean namespaceAware = false;
             *     private boolean whitespace = false;
             *     private boolean expandEntityRef = true;
             *     private boolean ignoreComments = false;
             *     private boolean coalescing = false;
             */

            // 命名空间支持
            factory.setNamespaceAware(false);
            // 忽略注释
            factory.setIgnoringComments(true);
            // 忽略元素中间的空格
            factory.setIgnoringElementContentWhitespace(false);
            // CDATA 转换为临近元素
            factory.setCoalescing(false);
            // 自动展开节点
            factory.setExpandEntityReferences(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            //builder.setEntityResolver();
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    throw exception;
                }
            });
            return builder.parse(inputStream);
        } catch (Exception e) {
            logger.error("createDocument is error", e);
            throw new RuntimeException("create document is error", e);
        }

    }

    public Document getDocument() {
        return document;
    }

    /**
     * <p>将表达式解析为节点
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param expression 表达式
     * @return xyz.chaobei.mybatis.parser.XNode
     * @since 2022/3/7
     **/
    public XNode evalNode(String expression) {
        return evalNode(document, expression);
    }

    /**
     * <p>从一个XML文档中通过表达式抽取一个节点
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param document
     * @return xyz.chaobei.mybatis.parser.XNode
     * @since 2022/3/7
     **/
    public XNode evalNode(Object document, String expression) {

        Node node = (Node) this.evaluate(expression, document, XPathConstants.NODE);

        if (Objects.isNull(node)) {
            return null;
        }

        return new XNode(this, node);
    }

    /***
     * <p>将一个XML对象 {@link Document} 转换为需要的类型，比如Node,或者String
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param expression 表达式
     * @param root 根元素
     * @param returnType 返回类型 {@link XPathConstants}
     * @return java.lang.Object
     * @since 2022/3/7
     **/
    private Object evaluate(String expression, Object root, QName returnType) {
        try {
            return xPath.evaluate(expression, root, returnType);
        } catch (XPathExpressionException e) {
            logger.error("Xpath evaluate error", e);
            throw new RuntimeException("Xpath evaluate error");
        }
    }

}
