package xyz.litewars.litewars.api.database.hikaricp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPSupport {
    public HikariDataSource dataSource;
    public HikariCPConfig config;
    private final boolean isSQLite;
    public HikariCPSupport(HikariCPConfig config, boolean isSQLite) {
        this.config = config;
        this.isSQLite = isSQLite;
    }
    public void start () {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.config.getUrl());
        if (isSQLite) {
            config.setDriverClassName("org.sqlite.JDBC");
        } else {
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");//不对啊，我用的时候可以热重载的
            config.setUsername(this.config.getUserName());
            config.setPassword(this.config.getPassWord());
        }
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        this.dataSource = new HikariDataSource(config);
    }
    public void stop () {
        if (!this.dataSource.isClosed()) this.dataSource.close();
    }
    public HikariDataSource getDataSource () {
        return this.dataSource;
    }
}
