package com.araproje.OgrenciBilgiSistemi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.araproje.OgrenciBilgiSistemi.Interceptors.TokenControlInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer  {
	
	@Bean
	public TokenControlInterceptor tokenControlInterceptor() {
	    return new TokenControlInterceptor();
	}
	@Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(tokenControlInterceptor()).addPathPatterns("/api/rest/**");
    }
}	
