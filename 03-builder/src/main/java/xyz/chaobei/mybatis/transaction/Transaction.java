package xyz.chaobei.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/8
 **/
public interface Transaction {

    /**
     * <p>获得一个数据库连接
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @return java.sql.Connection
     * @since 2022/3/8
     **/
    Connection getConnection() throws SQLException;

    /**
     * <p>提交
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @return void
     * @since 2022/3/8
     **/
    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;
}
