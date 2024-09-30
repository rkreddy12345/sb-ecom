package com.rkecom.config;

import com.rkecom.objects.mapper.CategoryMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapper ();
    }
}
