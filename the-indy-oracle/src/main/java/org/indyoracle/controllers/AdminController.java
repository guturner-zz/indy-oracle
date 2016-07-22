package org.indyoracle.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.indyoracle.annotations.Service;
import org.indyoracle.beans.UserBean;
import org.indyoracle.beans.ValidateResult;
import org.indyoracle.security.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;

/**
 * Controller for the 'Admin Utilities' screen.
 * 
 * @author Guy
 *
 */

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String adminView(HttpServletRequest request, Model model) {
    	// Double check that user is admin:
    	Account account = UserManager.getCurrentUser(request);
    	if (!account.getCustomData().get("role").equals("ADMIN")) {
    		return "home";
    	}
    	
    	return "admin";
    }
    
}
