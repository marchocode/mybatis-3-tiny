package xyz.chaobei.mybatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/8
 **/
public interface DataSourceFactory {
    /**
     * <p>获取数据源
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @return javax.sql.DataSource
     * @since 2022/3/8
     **/
    DataSource getDataSource();

    /**
     * <p>设置数据源需要的参数
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param properties
     * @return void
     * @since 2022/3/8
     **/
    void setProperties(Properties properties);
}
