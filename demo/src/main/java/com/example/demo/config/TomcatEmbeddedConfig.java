package com.example.demo.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Profile("embedded")
public class TomcatEmbeddedConfig {

    @Autowired
    private DatasourceProperties datasourceProperties;

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
                tomcat.enableNaming();

                return super.getTomcatEmbeddedServletContainer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                context.getNamingResources().addResource(createContextResource(datasourceProperties.getDb1()));
                context.getNamingResources().addResource(createContextResource(datasourceProperties.getDb2()));
            }

            private ContextResource createContextResource(DatasourceProperties.Datasource ds) {
                ContextResource resource = new ContextResource();
                resource.setName(ds.getJndiName());
                resource.setType(DataSource.class.getName());
                resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
                resource.setProperty("driverClassName", ds.getResource().getDriverClassName());
                resource.setProperty("url", ds.getResource().getUrl());
                resource.setProperty("username", ds.getResource().getUsername());
                resource.setProperty("password", ds.getResource().getPassword());
                return resource;
            }
        };
    }


}
