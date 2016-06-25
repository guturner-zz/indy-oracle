package org.indyoracle.controllers;

import javax.servlet.http.HttpServletRequest;

import org.indyoracle.security.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
    	Account account = UserManager.getCurrentUser(request);
    	
    	if (account != null) {
	    	String percentage = "66";
	    	CustomData data = account.getCustomData();
	    	if (data.get("phoneNumber") != null && data.get("phoneCarrier") != null) {
	    		percentage = "100";
	    	}
	    	else if (data.get("phoneNumber") != null || data.get("phoneCarrier") != null) {
	    		percentage = "75";
	    	}
	    	
	    	model.addAttribute("percentage", percentage);
	    	if (percentage.equals("100")) {
	    		model.addAttribute("profileDone", true);
	    	}
    	}
    	
    	return "home";
    }
    
}
