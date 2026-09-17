package com.mycompany.myapp;

import com.mycompany.myapp.config.AsyncSyncConfiguration;
import com.mycompany.myapp.config.EmbeddedSQL;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { JdTestApp.class, AsyncSyncConfiguration.class, com.mycompany.myapp.config.JacksonHibernateConfiguration.class })
@EmbeddedSQL
public @interface IntegrationTest {}
