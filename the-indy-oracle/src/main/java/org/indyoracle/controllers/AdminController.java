package org.indyoracle.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.indyoracle.security.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stormpath.sdk.account.Account;

/**
 * Controller for the 'Admin Utilities' screen.
 * 
 * @author Guy
 *
 */

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String adminView(HttpServletRequest request, HttpSession session, Model model) {
    	// Double check that user is admin:
    	Account account = UserManager.getCurrentUser(session);
    	if (!account.getCustomData().get("role").equals("ADMIN")) {
    		return "home";
    	}
    	
    	return "admin";
    }
    
}
