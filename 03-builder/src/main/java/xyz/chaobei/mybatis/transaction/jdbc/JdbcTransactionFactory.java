package xyz.chaobei.mybatis.transaction.jdbc;

import xyz.chaobei.mybatis.enums.TransactionIsolationLevel;
import xyz.chaobei.mybatis.transaction.Transaction;
import xyz.chaobei.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/8
 **/
public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public Transaction newTransaction(Connection connection) {
        return new JdbcTransaction(connection);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(dataSource, level.getLevel(), autoCommit);
    }
}
