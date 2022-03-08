package xyz.chaobei.mybatis.transaction.jdbc;

import xyz.chaobei.mybatis.enums.TransactionIsolationLevel;
import xyz.chaobei.mybatis.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/8
 **/
public class JdbcTransaction implements Transaction {

    private Connection connection;

    private DataSource dataSource;

    private boolean autoCommit;

    private Integer level;

    public JdbcTransaction(Connection connection) {
        this.connection = connection;
    }

    public JdbcTransaction(DataSource dataSource, int level, boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
        this.level = level;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return Objects.isNull(this.connection) ? this.openConnection() : this.connection;
    }

    /**
     * <p>通过数据源获取一个连接
     * <p>author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
     *
     * @return java.sql.Connection
     * @since 2022/3/8
     **/
    protected Connection openConnection() throws SQLException {

        this.connection = dataSource.getConnection();

        if (this.connection.getAutoCommit() != this.autoCommit) {
            this.connection.setAutoCommit(this.autoCommit);
        }

        if (Objects.nonNull(level)) {
            this.connection.setTransactionIsolation(this.level);
        }

        return this.connection;
    }


    @Override
    public void commit() throws SQLException {
        this.connection.commit();
    }

    @Override
    public void rollback() throws SQLException {
        this.connection.rollback();
    }

    @Override
    public void close() throws SQLException {
        this.connection.close();
    }
}
