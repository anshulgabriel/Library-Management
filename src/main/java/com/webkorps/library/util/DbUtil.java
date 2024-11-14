package com.webkorps.library.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class DbUtil {

    private static final HikariDataSource dataSource;
    
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/library_management"); 
        config.setUsername("root"); 
        config.setPassword("WebKorps@123"); 
        config.setDriverClassName("com.mysql.cj.jdbc.Driver"); 
        config.setMaximumPoolSize(10); 
        config.setMinimumIdle(2); 

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
