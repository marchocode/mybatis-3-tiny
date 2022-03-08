package xyz.chaobei.mybatis.config;

import xyz.chaobei.mybatis.datasource.mysql.MysqlDataSourceFactory;
import xyz.chaobei.mybatis.transaction.jdbc.JdbcTransactionFactory;
import xyz.chaobei.mybatis.type.TypeAliasRegistry;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/7
 **/
public class Configuration {
    /**
     * 别名和Class的关联注册
     */
    private TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    /**
     * 环境
     */
    private Environment environment;


    public Configuration() {
        // JDBC事务工厂
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        // 外部接入的 MysqlDataSource
        typeAliasRegistry.registerAlias("MYSQL", MysqlDataSourceFactory.class);
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }


    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
