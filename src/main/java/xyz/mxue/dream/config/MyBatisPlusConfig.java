package xyz.mxue.dream.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2> MyBatisPlusConfig MyBatis Plus Config </h2>
 *
 * @author mxuexxmy
 * @date 2022/6/11 23:14
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * 配置拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据权限拦截器
        //interceptor.addInnerInterceptor(new DataAccessControlInterceptor());
        // 分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

}
