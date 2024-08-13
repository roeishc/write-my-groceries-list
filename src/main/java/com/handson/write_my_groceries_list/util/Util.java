package com.handson.write_my_groceries_list.util;

import com.handson.write_my_groceries_list.jwt.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();


    public static String getUserName(HttpServletRequest request){

        final String requestTokenHeader = request.getHeader("Authorization");
        String userName = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else if (!(request.getMethod().equals("OPTIONS")) && !request.getRequestURI().contains("actuator")){
            logger.warn("JWT Token does not begin with Bearer String");
        }

        return userName;

    }

}
