package com.webkorps.library.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class DbUtil {

    private static final HikariDataSource dataSource;
    
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/library_management"); // Database URL
        config.setUsername("root"); // Database username
        config.setPassword("WebKorps@123"); // Database password
        config.setDriverClassName("com.mysql.cj.jdbc.Driver"); // MySQL JDBC driver
        config.setMaximumPoolSize(10); // Maximum number of connections
        config.setMinimumIdle(2); // Minimum number of idle connections

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
