package com.araproje.OgrenciBilgiSistemi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.araproje.OgrenciBilgiSistemi.Interceptors.StudentControlInterceptor;
import com.araproje.OgrenciBilgiSistemi.Interceptors.AdminControlInterceptor;
import com.araproje.OgrenciBilgiSistemi.Interceptors.CommonControlInterceptor;
import com.araproje.OgrenciBilgiSistemi.Interceptors.InstructorControlInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer  {
	
	@Bean
	public AdminControlInterceptor adminControlInterceptor() {
	    return new AdminControlInterceptor();
	}
	
	@Bean
	public StudentControlInterceptor studentControlInterceptor() {
	    return new StudentControlInterceptor();
	}
	
	@Bean
	public InstructorControlInterceptor instructorControlInterceptor() {
	    return new InstructorControlInterceptor();
	}
	
	@Bean
	public CommonControlInterceptor commonControlInterceptor() {
	    return new CommonControlInterceptor();
	}
	
	
	@Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(adminControlInterceptor()).addPathPatterns("/api/rest/admin/**");
        registry.addInterceptor(studentControlInterceptor()).addPathPatterns("/api/rest/student/**");
        registry.addInterceptor(instructorControlInterceptor()).addPathPatterns("/api/rest/instructor/**");
        registry.addInterceptor(commonControlInterceptor()).addPathPatterns("/api/rest/common/**");
    }
}	
