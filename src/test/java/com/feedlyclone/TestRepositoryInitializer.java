package com.feedlyclone;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@TestPropertySource(locations="classpath:application-test.properties")
public class TestRepositoryInitializer {

    /*@Autowired
    private DataSource dataSource;
    @Autowired
    private String hibernateDialect;
    @Value("$")
    private String hibernateSchemaStrategy;
    @Value("$")
    private String hibernateLogSql;
    @Value("$")
    private String hibernateFormatSql;
    @Value("$")
    private String hibernateUseSqlCommands;

    @Bean
    public AnnotationSessionFactoryBean hibernate3AnnotatedSessionFactory(){
        AnnotationSessionFactoryBean sessionFactoryBean = new AnnotationSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("com.feedlyclone.domain");
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", hibernateDialect);
        jpaProperties.put("hibernate.hbm2ddl.auto", hibernateSchemaStrategy);
        jpaProperties.put("hibernate.show_sql", hibernateLogSql);
        jpaProperties.put("hibernate.format_sql", hibernateFormatSql);
        jpaProperties.put("hibernate.use_sql_comments", hibernateUseSqlCommands);
        sessionFactoryBean.setHibernateProperties(jpaProperties);
        return sessionFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
    }*/


}
