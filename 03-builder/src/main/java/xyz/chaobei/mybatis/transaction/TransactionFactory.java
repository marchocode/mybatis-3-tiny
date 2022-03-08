package xyz.chaobei.mybatis.transaction;

import xyz.chaobei.mybatis.enums.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/8
 **/
public interface TransactionFactory {

    /**
     * <p>直接使用现有连接创建一个事务
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param connection 数据库连接
     * @return xyz.chaobei.mybatis.transaction.Transaction
     * @since 2022/3/8
     **/
    Transaction newTransaction(Connection connection);

    /**
     * <p>
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @param dataSource 数据源
     * @param level      事务等级
     * @param autoCommit 是否自动提交
     * @return xyz.chaobei.mybatis.transaction.Transaction
     * @since 2022/3/8
     **/
    Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);

}
