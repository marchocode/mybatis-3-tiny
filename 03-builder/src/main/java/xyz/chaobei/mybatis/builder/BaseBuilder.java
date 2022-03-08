package xyz.chaobei.mybatis.builder;

import xyz.chaobei.mybatis.config.Configuration;
import xyz.chaobei.mybatis.type.TypeAliasRegistry;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/7
 **/
public abstract class BaseBuilder {

    // 主要的配置
    protected final Configuration configuration;

    // 类型配置
    protected final TypeAliasRegistry typeAliasRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = configuration.getTypeAliasRegistry();
    }

    protected Class<?> resolveAlias(String alias) {
        return typeAliasRegistry.resolveAlias(alias);
    }

}
