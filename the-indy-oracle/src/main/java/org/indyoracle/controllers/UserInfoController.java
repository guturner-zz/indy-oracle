package org.indyoracle.controllers;

import javax.servlet.http.HttpServletRequest;

import org.indyoracle.beans.UserBean;
import org.indyoracle.security.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;

@Controller
public class UserInfoController {

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String userView(HttpServletRequest request, Model model) {
    	Account account = UserManager.getCurrentUser(request);
    	
    	UserBean userBean = new UserBean();
    	userBean.setFirstName(account.getGivenName());
    	userBean.setLastName(account.getSurname());
    	String phoneNumber = "";
    	if (account.getCustomData().get("phoneNumber") != null) {
    		phoneNumber = account.getCustomData().get("phoneNumber").toString();
    	}
    	userBean.setPhoneNumber(phoneNumber);
    	
    	model.addAttribute("userBean", userBean);
    	
    	return "user_profile";
    }
    
    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public String userSet(@ModelAttribute UserBean userBean, HttpServletRequest request, Model model) {
    	Account account = UserManager.getCurrentUser(request);
    	
    	account.setGivenName(userBean.getFirstName());
    	account.setSurname(userBean.getLastName());
    	
    	CustomData customData = account.getCustomData();
    	customData.put("phoneNumber", userBean.getPhoneNumber());
    	
    	account.save();
    	
    	return "user_profile";
    }
}
