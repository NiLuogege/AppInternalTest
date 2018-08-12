package com.airent.appinternaltest;

import com.airent.appinternaltest.utils.uploadProgress.CustomMultipartResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartResolver;

@SpringBootApplication
@MapperScan("com.airent.appinternaltest.mapper")
/*
 * 将 spring 默认的文件上传处理类取消自动配置，这一步很重要，没有这一步，当multipartResolver重新指向了我们定义好
 * 的新的文件上传处理类后，前台传回的 file 文件在后台获取会是空，加上这句话就好了，推测不加这句话，spring 依然
 * 会先走默认的文件处理流程并修改request对象，再执行我们定义的文件处理类。（这只是个人推测）
 * exclude表示自动配置时不包括Multipart配置
 */
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class AppinternaltestApplication extends SpringBootServletInitializer {

    /**
     * tomcat 需要
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppinternaltestApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AppinternaltestApplication.class, args);
    }

    /*
     * 将 multipartResolver 指向我们刚刚创建好的继承 CommonsMultipartResolver 类的自定义文件上传处理类
    */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CustomMultipartResolver multipartResolver = new CustomMultipartResolver();
        return multipartResolver;
    }
}
