import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;

public class Init {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/javalab_hw12";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public static JdbcTemplate init() throws Exception {
        Class.forName("org.postgresql.Driver");
        HikariConfig config = new HikariConfig();
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setJdbcUrl(DB_URL);
        HikariDataSource dataSource = new HikariDataSource(config);
        return new JdbcTemplate(dataSource);
    }

}
