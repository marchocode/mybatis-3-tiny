package xyz.chaobei.mybatis.builder.xml;

import xyz.chaobei.mybatis.builder.BaseBuilder;
import xyz.chaobei.mybatis.config.Configuration;
import xyz.chaobei.mybatis.config.Environment;
import xyz.chaobei.mybatis.datasource.DataSourceFactory;
import xyz.chaobei.mybatis.parser.XNode;
import xyz.chaobei.mybatis.parser.XPathParser;
import xyz.chaobei.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/7
 **/
public class XMLConfigBuilder extends BaseBuilder {

    /**
     * 是否进行构建过的标志
     */
    private boolean parsed;
    /**
     * 包含document 对象的xml解析
     */
    private final XPathParser parser;

    /**
     * 数据库默认环境ID
     */
    private String environment;

    /***
     * <p>将文件输入流转换为XPathParser 并且初始化 {@link Configuration}
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param inputStream 文件输入流
     * @return
     * @since 2022/3/7
     **/
    public XMLConfigBuilder(InputStream inputStream) {
        this(new XPathParser(inputStream));
    }

    private XMLConfigBuilder(XPathParser parser) {
        super(new Configuration());
        this.parser = parser;
        // 还没构建
        this.parsed = false;
    }

    /***
     * <p>将XML输入的XML文件转换为 {@link Configuration}
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @return xyz.chaobei.mybatis.config.Configuration
     * @since 2022/3/7
     **/
    public Configuration parse() {

        // 查看是否已经构建过了，如果构建过了，就报错
        if (parsed) {
            throw new RuntimeException("Each Configuration can only be parse once");
        }

        this.parsed = true;
        this.parseConfiguration(parser.evalNode("/configuration"));

        return this.configuration;
    }

    /**
     * <p>将xml里面主要的configuration 节点的数据转换为配置{@link Configuration}
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param configuration configuration节点
     * @return void
     * @since 2022/3/7
     **/
    private void parseConfiguration(XNode configuration) {
        try {
            // 数据库连接配置
            this.environmentsElement(configuration.evalNode("/environments"));
            // 解析Mapper
            this.mapperElement(configuration.evalNode("/mappers"));
        } catch (Exception e) {

        }
    }


    private void mapperElement(XNode mappers) {

    }

    /***
     * <p>
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param environments {@code environments} 节点
     * @return void
     * @since 2022/3/7
     **/
    private void environmentsElement(XNode environments) throws Exception {

        // 默认环境变量
        this.environment = environments.getStringAttribute("default");

        // 获得子元素 environment
        for (XNode environment : environments.getChildren()) {

            String id = environment.getStringAttribute("id");

            if (Objects.isNull(this.environment)) {
                throw new RuntimeException("default environment is null");
            }
            if (Objects.isNull(id)) {
                throw new RuntimeException("environment statement attribute id must have val");
            }
            if (!this.environment.equals(id)) {
                continue;
            }

            // 事务工厂
            TransactionFactory transactionFactory = this.transactionManagerElement(environment.evalNode("/transactionManager"));
            DataSourceFactory dataSourceFactory = this.dataSourceElement(environment.evalNode("/dataSource"));
            DataSource dataSource = dataSourceFactory.getDataSource();

            // 环境包装
            Environment envi = new Environment.Builder(id)
                    .transactionFactory(transactionFactory)
                    .dataSource(dataSource)
                    .build();

            super.configuration.setEnvironment(envi);
        }

    }
    /**
     * <p>通过DataSource节点创建一个数据源工厂
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param dataSource 节点
     * @return xyz.chaobei.mybatis.datasource.DataSourceFactory
     * @since 2022/3/8
     **/
    private DataSourceFactory dataSourceElement(XNode dataSource) throws Exception {

        String type = dataSource.getStringAttribute("type");
        Properties properties = dataSource.getChildrenAsProperties();

        DataSourceFactory dataSourceFactory = (DataSourceFactory) this.resolveClass(type).newInstance();
        dataSourceFactory.setProperties(properties);

        return dataSourceFactory;
    }


    private TransactionFactory transactionManagerElement(XNode xNode) throws Exception {

        if (Objects.isNull(xNode)) {
            throw new RuntimeException("environment statement must have a transactionManagerElement");
        }
        // 获取事务的类型
        String type = xNode.getStringAttribute("type");
        TransactionFactory transactionFactory = (TransactionFactory) this.resolveClass(type).newInstance();

        return transactionFactory;
    }

    /**
     * <p>将类型转换为对象
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param type 对象的类型
     * @return java.lang.Object
     * @since 2022/3/8
     **/
    private Class<?> resolveClass(String type) {
        return resolveAlias(type);
    }
}
