package com.example.SpringBootMongoDBAssessment.Configuration;

import com.example.SpringBootMongoDBAssessment.Service.TokenService;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// JWT Auth
@Configuration
public class JwtFilter extends GenericFilterBean {

    private final TokenService tokenService;

    JwtFilter(TokenService tokenService)
    {
        this.tokenService= tokenService;
    }


    // Function checks auth every incoming request
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;




        String token = httpServletRequest.getHeader("Authorization"); // Gets the auth header

        //Approves requests from the controller
        if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod()))
        {
            httpServletResponse.sendError(HttpServletResponse.SC_OK, "Success");  //Response
            return;
        }

        //Check if the request URI contains "admin" to allow it without a JWT token
        if (allowReqWithoutToken(httpServletRequest))
        {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK, "Success");
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
        else
        {
            ObjectId userId = new ObjectId(tokenService.getUserToken(token));
            httpServletRequest.setAttribute("userId",userId);
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    //check if the request can be allowed without a token
    public boolean allowReqWithoutToken(HttpServletRequest httpServletRequest){
        if (httpServletRequest.getRequestURI().contains("admin"))
        {
            return  true;
        }
        return  false;
    }
}