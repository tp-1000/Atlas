package org.launchcode.Atlas.controller;

import org.launchcode.Atlas.data.UserRepository;
import org.launchcode.Atlas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AtlasController {

    @Autowired
    UserRepository userRepository;

    //string to be used in new session attribute
    private static final String USERSESSIONKEY = "default_user";

    //set a user (if present) into the seesion attributes returns nothong.
    public void setSessionWithUser(HttpSession session, User user){
        session.setAttribute(USERSESSIONKEY, user.getId());
    }

    //get user from session data --> and check USERSESSIONKEY for a user returns user if present or null.
    public User getUserFromSession(HttpSession session) {
        Integer userID = (Integer) session.getAttribute(USERSESSIONKEY);
        if(userID == null){
            return null;
        }

        Optional<User> user = userRepository.findById(userID);
        if(user.isEmpty()){
            return null;
        }

        return user.get();
    }

    @ModelAttribute("validUser")
    public User getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(); //get current session or make new one
        return getUserFromSession(session);
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("API_KEY", System.getenv("API_KEY"));
    }

}
