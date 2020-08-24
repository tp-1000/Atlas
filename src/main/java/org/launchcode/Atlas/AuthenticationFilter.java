package org.launchcode.Atlas;

import org.launchcode.Atlas.controller.LoginController;
import org.launchcode.Atlas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    LoginController loginController;

    List<String> allowList = Arrays.asList("/login", "/register");

    //is beginning of path on Allow_List
    public Boolean isInAllowList(String path) {
        for(String pathRoot: allowList) {
            if(path.startsWith(pathRoot)){
                return true;
            }
        }
        return false;
    };


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession(); //get current session or make new one
        User user = loginController.getUserFromSession(session); //look for user from session, if present then login process worked (could be null)

        //checks request message and if the path is Allowed then go ahead
        if(isInAllowList(request.getRequestURI())){
            return true;
        }
        //if path is not in allowList allowed better check if user was authenticated.
        if(user != null){
            return true;
        }

        // failed the user test and did not request an allowed page... false
        response.sendRedirect("login");
        return false;
    }
}
