package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "datasource", ignoreUnknownFields = false)
public class DatasourceProperties {

    public static class Datasource {
        public static class Resource {
            private String driverClassName;
            private String Url;
            private String Username;
            private String Password;

            public String getDriverClassName() {
                return driverClassName;
            }

            public void setDriverClassName(String driverClassName) {
                this.driverClassName = driverClassName;
            }

            public String getUrl() {
                return Url;
            }

            public void setUrl(String url) {
                Url = url;
            }

            public String getUsername() {
                return Username;
            }

            public void setUsername(String username) {
                Username = username;
            }

            public String getPassword() {
                return Password;
            }

            public void setPassword(String password) {
                Password = password;
            }
        }

        public static class Flyway {
            private String locations;

            public String getLocations() {
                return locations;
            }

            public void setLocations(String locations) {
                this.locations = locations;
            }
        }

        private String jndiName;
        private String expectedType;
        private Flyway flyway;
        private Resource resource;

        public String getJndiName() {
            return jndiName;
        }

        public void setJndiName(String jndiName) {
            this.jndiName = jndiName;
        }

        public String getExpectedType() {
            return expectedType;
        }

        public void setExpectedType(String expectedType) {
            this.expectedType = expectedType;
        }

        public Flyway getFlyway() {
            return flyway;
        }

        public void setFlyway(Flyway flyway) {
            this.flyway = flyway;
        }

        public Resource getResource() {
            return resource;
        }

        public void setResource(Resource resource) {
            this.resource = resource;
        }
    }

    private Datasource db1;
    private Datasource db2;

    public Datasource getDb1() {
        return db1;
    }

    public void setDb1(Datasource db1) {
        this.db1 = db1;
    }

    public Datasource getDb2() {
        return db2;
    }

    public void setDb2(Datasource db2) {
        this.db2 = db2;
    }
}