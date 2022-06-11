package xyz.mxue.dream.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <h2> SpringMvcConfig Spring MVC Config Info </h2>
 *
 * @author mxuexxmy
 * @date 2022/6/11 23:17
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"xyz.mxue"})
@MapperScan(basePackages={"xyz.mxue.dream.mapper"})
public class SpringMvcConfig {

}
