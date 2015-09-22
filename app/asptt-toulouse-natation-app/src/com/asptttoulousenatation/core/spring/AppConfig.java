package com.asptttoulousenatation.core.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan("com.asptttoulousenatation.core.web")
@Import({ WebSecurityConfig.class })
public class AppConfig {

}
