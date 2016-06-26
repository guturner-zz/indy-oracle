package org.indyoracle.controllers;

import javax.servlet.http.HttpServletRequest;

import org.indyoracle.security.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;

/**
 * Controller for the 'Home' screen.
 * 
 * @author Guy
 *
 */

@Controller
public class MainController {

	/**
	 * Sets up the 'Profile Progress' progress bar on home page.
	 * 
	 * @param request
	 * @param model
	 */
	private void initProgressBar(HttpServletRequest request, Model model) {
		Account account = UserManager.getCurrentUser(request);
		
		// Only add a progress bar if logged in:
    	if (account != null) {
	    	String percentage = "66";
	    	CustomData data = account.getCustomData();
	    	
	    	// Profile 100% complete if:
	    	if (data.get("phoneNumber") != null && data.get("phoneCarrier") != null) {
	    		percentage = "100";
	    	}
	    	// Profile 75% complete if:
	    	else if (data.get("phoneNumber") != null || data.get("phoneCarrier") != null) {
	    		percentage = "75";
	    	}
	    	
	    	model.addAttribute("percentage", percentage);
	    	if (percentage.equals("100")) {
	    		model.addAttribute("profileDone", true);
	    	}
    	}
	}
	
    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
    	initProgressBar(request, model);
    	
    	return "home";
    }
    
}
