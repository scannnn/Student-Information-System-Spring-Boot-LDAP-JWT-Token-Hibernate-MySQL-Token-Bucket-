package com.araproje.OgrenciBilgiSistemi.Interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.araproje.OgrenciBilgiSistemi.security.JwtTokenProvider;
import com.araproje.OgrenciBilgiSistemi.util.MessageConstants;

public class InstructorControlInterceptor implements HandlerInterceptor{

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String header = request.getHeader("Authorization");
		
		if(header == null || !header.startsWith("Bearer ")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, MessageConstants.EMPTY_TOKEN);
			return false;
		}
		else {
			String jwt = header.substring(7);
		   	 try {
		   		 jwtTokenProvider.validateToken(jwt);
		   	 }catch(Exception ex) {
		   		 response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		   		return false;
		   	 }
		   	 if(! jwtTokenProvider.getUserFromJWT(jwt).getRole().equalsIgnoreCase("Instructor")) {
		   		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bu sayfayı görüntüleyebilmek için gerekli yetkiye sahip değilsiniz.");
		   		return false;
		   	 }
		   	 return true;
		     
		}
	}
}
