package xyz.chaobei.mybatis.datasource.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import xyz.chaobei.mybatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/8
 **/
public class MysqlDataSourceFactory implements DataSourceFactory {

    private final MysqlDataSource dataSource;

    public MysqlDataSourceFactory() {
        this.dataSource = new MysqlDataSource();
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    @Override
    public void setProperties(Properties properties) {
        this.dataSource.setUser(properties.getProperty("username"));
        this.dataSource.setPassword(properties.getProperty("password"));
        this.dataSource.setUrl(properties.getProperty("url"));
    }
}
