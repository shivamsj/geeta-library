package com.geetalibrary.backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.RdsUtilities;

@Configuration
@Profile("aws")
@ConditionalOnProperty(name = "app.aws.iam-db-auth", havingValue = "true")
public class AwsIamDatabaseConfig {

    @Bean
    public DataSource dataSource(
        @Value("${app.aws.database.host}") String host,
        @Value("${app.aws.database.port:5432}") int port,
        @Value("${app.aws.database.name:postgres}") String database,
        @Value("${app.aws.database.username:postgres}") String username,
        @Value("${app.aws.region:${AWS_REGION:ap-south-1}}") String region,
        @Value("${DB_POOL_SIZE:2}") int maxPoolSize,
        @Value("${DB_MIN_IDLE:0}") int minIdle
    ) {
        HikariConfig config = new HikariConfig();
        config.setDataSource(new IamPostgresDataSource(host, port, database, username, region));
        config.setMaximumPoolSize(maxPoolSize);
        config.setMinimumIdle(minIdle);
        config.setIdleTimeout(30_000);
        config.setMaxLifetime(8 * 60_000);
        config.setConnectionTimeout(20_000);
        return new HikariDataSource(config);
    }

    private static final class IamPostgresDataSource extends PGSimpleDataSource {
        private final String host;
        private final int port;
        private final String username;
        private final RdsUtilities rdsUtilities;

        private IamPostgresDataSource(String host, int port, String database, String username, String region) {
            this.host = host;
            this.port = port;
            this.username = username;
            this.rdsUtilities = RdsUtilities.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
            setURL("jdbc:postgresql://" + host + ":" + port + "/" + database + "?sslmode=require");
            setUser(username);
        }

        @Override
        public Connection getConnection() throws SQLException {
            return super.getConnection(username, token(username));
        }

        @Override
        public Connection getConnection(String user, String password) throws SQLException {
            return super.getConnection(user, token(user));
        }

        private String token(String user) {
            return rdsUtilities.generateAuthenticationToken(builder -> builder
                .hostname(host)
                .port(port)
                .username(user));
        }
    }
}
