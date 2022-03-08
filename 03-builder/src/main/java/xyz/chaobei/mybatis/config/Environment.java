package xyz.chaobei.mybatis.config;

import xyz.chaobei.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @description:
 * @author: <a href='mailto:maruichao52@gmail.com'>MRC</a>
 * @since 2022/3/8
 **/
public class Environment {

    private final String id;

    private final TransactionFactory transactionFactory;

    private final DataSource dataSource;

    public Environment(String id, TransactionFactory transactionFactory, DataSource dataSource) {

        Objects.requireNonNull(id, "Environment id must not be null");
        Objects.requireNonNull(transactionFactory, "Environment transactionFactory must not be null");
        Objects.requireNonNull(dataSource, "Environment dataSource must not be null");

        this.id = id;
        this.transactionFactory = transactionFactory;
        this.dataSource = dataSource;
    }

    public static class Builder {

        private final String id;
        private TransactionFactory transactionFactory;
        private DataSource dataSource;

        public Builder(String id) {
            this.id = id;
        }

        public Builder transactionFactory(TransactionFactory factory) {
            this.transactionFactory = factory;
            return this;
        }

        public Builder dataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public Environment build() {
            return new Environment(id, transactionFactory, dataSource);
        }

    }

    public String getId() {
        return id;
    }

    public TransactionFactory getTransactionFactory() {
        return transactionFactory;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
