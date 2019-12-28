package com.araproje.OgrenciBilgiSistemi.Interceptors;

import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.HandlerInterceptor;

import com.araproje.OgrenciBilgiSistemi.model.User;
import com.araproje.OgrenciBilgiSistemi.payload.IpBlock;
import com.araproje.OgrenciBilgiSistemi.security.JwtTokenProvider;
import com.araproje.OgrenciBilgiSistemi.util.MessageConstants;

public class StudentControlInterceptor implements HandlerInterceptor{
	@Value("${app.securityValue}")
	private boolean securityValue;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	Logger LOGGER=LoggerFactory.getLogger(StudentControlInterceptor.class);
	
	Map<String, IpBlock> userList = new ConcurrentHashMap<>();
	
	@Scheduled(fixedRate = 60000)
	public void scheduleTaskWithFixedRate() {
	    for(IpBlock i : userList.values()) {
	    	i.increaseCount(10);
	    }
		System.out.println("Token sayıları artırıldı.");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Date currentDate = new Date(System.currentTimeMillis());
		String header = request.getHeader("Authorization");
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
	       if (ipAddress == null) {  
	         ipAddress = request.getRemoteAddr();  
	   }
		System.out.println("IP = "+ipAddress);
		
		if(header == null || !header.startsWith("Bearer ")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, MessageConstants.EMPTY_TOKEN);
			return false;
		}
		else {
			String jwt = header.substring(7);
			User u = jwtTokenProvider.getUserFromJWT(jwt);
		   	 try {
		   		 jwtTokenProvider.validateToken(jwt);
		   	 }catch(Exception ex) {
		   		 response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		   		return false;
		   	 }
		   	 if(securityValue){
		   		if(userList.containsKey(u.getUserId())) {
			   		 IpBlock temp = userList.get(u.getUserId());
			   		 // TOKEN DEĞERİNİ ARTTIRMA
			   		 if(temp.getCount() <= 0) {
			   			LOGGER.warn(u.getUserId()+" numaralı öğrenci çok fazla istek atmaktadır. Saatlik istek sınırını aşmıştır. "
			   					+ " IP = "+ipAddress);
	   					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sisteme çok fazla istek atmaktasınız."
	   							+ " 1 dakika sonra tekrar deneyiniz.");
			   		 }
			   		 temp.decreaseCount();
			   		 if(temp.isBanned()) {
			   			long diffInMillies = currentDate.getTime() - temp.getLastRequest().getTime();
			   			temp.setLastRequest(new Date(System.currentTimeMillis()));
			   			if(diffInMillies < 200) {
			   				temp.increaseWarningCount();
			   				if(temp.getBanCount() <3) {
			   					LOGGER.warn(u.getUserId()+" numaralı öğrenci"+ " çok sık istek göndermektedir."
			   							+ " IP = "+ipAddress);
			   					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sisteme çok sık istek atmaktasınız. "
					   					+ temp.getBannedUntil().toString()+ " tarihine kadar sistemden uzaklaştırıldınız.");
			   				}
			   				else {
			   					LOGGER.warn(u.getUserId()+" numaralı öğrenci"+ " çok sık istek gönderdiği için 30 dk lığına"
			   							+ " sunucudan uzaklaştırılmıştır."+" IP = "+ipAddress);
			   					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sisteme çok sık istek atmaktasınız. "
					   					+ temp.getBannedUntil().toString()+ " tarihine kadar sistemden uzaklaştırıldınız.");
			   				}
			   				return false;
			   			}
			   			else {
			   				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sisteme çok sık istek atmaktasınız. "
				   					+ temp.getBannedUntil().toString()+ " tarihine kadar sistemden uzaklaştırıldınız.");
					   		return false;
			   			}
			   		 }
			   		 else {
			   			long diffInMillies = currentDate.getTime() - temp.getLastRequest().getTime();
			   			temp.setLastRequest(new Date(System.currentTimeMillis()));
			   			if(diffInMillies < 200) {
			   				temp.increaseWarningCount();
			   				userList.put(u.getUserId(), temp);
			   				/*response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sisteme çok sık istek atmaktasınız. ");
			   				return false;*/
			   			}
			   		 }
			   	 }
			   	 else {
			   		 IpBlock temp = new IpBlock();
			   		 temp.setLastRequest(new Date(System.currentTimeMillis()));
			   		 temp.decreaseCount();
			   		 userList.put(u.getUserId(), temp);
			   	 }
		   	 }
		   	if(! u.getRole().equalsIgnoreCase("Student")) {
		   		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bu sayfayı görüntüleyebilmek için gerekli yetkiye sahip değilsiniz.");
		   		return false;
		   	 }
		   	 request.setAttribute("uid", u.getUserId());
		   	 return true;
		     
		}
	}
	
}
