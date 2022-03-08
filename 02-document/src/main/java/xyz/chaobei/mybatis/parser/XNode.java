package xyz.chaobei.mybatis.parser;

import com.sun.corba.se.spi.orb.PropertyParser;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/7
 **/
public class XNode {
    /**
     * 引用document对象
     */
    private final XPathParser parser;
    /**
     * w3c的node对象
     */
    private final Node node;
    /**
     * 当前节点的一些属性 比如
     * {@code <mapper url='xxx'></mapper>}
     */
    private Properties attributes;

    public XNode(XPathParser parser, Node node) {
        this.parser = parser;
        this.node = node;
        // 解析当前节点的属性
        this.parseAttributes(node);
    }

    /**
     * <p>解析当前节点的属性
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param node
     * @return void
     * @since 2022/3/7
     **/
    private void parseAttributes(Node node) {

        this.attributes = new Properties();

        NamedNodeMap namedNodeMap = node.getAttributes();
        if (Objects.isNull(namedNodeMap)) {
            return;
        }

        for (int i = 0; i <= namedNodeMap.getLength(); i++) {
            Node attr = namedNodeMap.item(i);
            String value = attr.getNodeValue();
            this.attributes.put(attr.getNodeName(), value);
        }

    }

    /***
     * <p>调用{@link XPathParser} 的解析方法
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param expression 调用
     * @return xyz.chaobei.mybatis.parser.XNode
     * @since 2022/3/7
     **/
    public XNode evalNode(String expression) {
        return parser.evalNode(expression);
    }


    public String getStringAttribute(String attribute) {
        return getStringAttribute(attribute, null);
    }

    /**
     * <p>获得一个属性
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param attribute  属性名称
     * @param defaultVal 默认值
     * @return java.lang.String
     * @since 2022/3/7
     **/
    public String getStringAttribute(String attribute, String defaultVal) {
        String value = this.attributes.getProperty(attribute);
        return Objects.isNull(value) ? defaultVal : value;
    }

    /**
     * <p>获得当前节点的所有子元素，并且转换为XNode对象
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @return java.util.List<xyz.chaobei.mybatis.parser.XNode>
     * @since 2022/3/7
     **/
    public List<XNode> getChildren() {

        List<XNode> children = new LinkedList<>();

        NodeList nodeList = node.getChildNodes();
        if (Objects.isNull(nodeList) || nodeList.getLength() == 0) {
            return children;
        }

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            // 判断节点类型
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                children.add(new XNode(this.parser, node));
            }
        }

        return children;
    }

    /**
     * <p>把当前节点的下级所有节点都拿name和value属性
     * 当key和value 都不为空的时候才放入集合
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @return java.util.Properties
     * @since 2022/3/8
     **/
    public Properties getChildrenAsProperties() {

        Properties properties = new Properties();

        for (XNode xNode : getChildren()) {

            String name = xNode.getStringAttribute("name");
            String value = xNode.getStringAttribute("value");

            if (Objects.nonNull(name) && Objects.nonNull(value)) {
                properties.put(name, value);
            }

        }
        return properties;
    }
}
