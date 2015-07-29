package com.feedlyclone;

import com.feedlyclone.domain.PersistenceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

@Configuration
@ComponentScan(value = {"com.feedlyclone.service", "com.feedlyclone.mapper"}, excludeFilters = { @Filter(Configuration.class) })
@Import(PersistenceConfiguration.class)
@TestPropertySource(locations="classpath:application-test.properties")
@Profile("test")
public class SpringTestContextConfiguration {

}
