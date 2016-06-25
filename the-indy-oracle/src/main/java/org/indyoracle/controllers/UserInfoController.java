package org.indyoracle.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.indyoracle.beans.UserBean;
import org.indyoracle.security.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;

@Controller
public class UserInfoController {

	private final ArrayList<String> options = new ArrayList<String>() {{
		add("AT&T");
		add("Boost Mobile");
		add("Sprint");
		add("T-Mobile");
		add("Verizon");
		add("Virgin Mobile");
	}};
	
	private UserBean buildUserBean(HttpServletRequest request) {
		Account account = UserManager.getCurrentUser(request);
    	
    	UserBean userBean = new UserBean();
    	userBean.setFirstName(account.getGivenName());
    	userBean.setLastName(account.getSurname());
    	String phoneNumber = "";
    	if (account.getCustomData().get("phoneNumber") != null) {
    		phoneNumber = account.getCustomData().get("phoneNumber").toString();
    	}
    	userBean.setPhoneNumber(phoneNumber);
    	String phoneCarrier = "";
    	if (account.getCustomData().get("phoneCarrier") != null) {
    		phoneCarrier = account.getCustomData().get("phoneCarrier").toString();
    	}
    	userBean.setPhoneCarrier(phoneCarrier);
    	
    	return userBean;
	}
	
	private void setModelAttributes(HttpServletRequest request, Model model) {
		UserBean bean = buildUserBean(request);
		model.addAttribute("userBean", bean);
		model.addAttribute("options", options);
	}
	
    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String userView(HttpServletRequest request, Model model) {
    	setModelAttributes(request, model);
    	return "user_profile";
    }
    
    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public String userSet(@Valid @ModelAttribute UserBean userBean, BindingResult result, HttpServletRequest request, Model model) {
    	if (result.hasErrors()) {
    		model.addAttribute("errors", result.getAllErrors());
    		setModelAttributes(request, model);
    		return "user_profile";
    	}
    	
    	Account account = UserManager.getCurrentUser(request);
    	
    	account.setGivenName(userBean.getFirstName());
    	account.setSurname(userBean.getLastName());
    	
    	CustomData customData = account.getCustomData();
    	customData.put("phoneNumber", userBean.getPhoneNumber());
    	customData.put("phoneCarrier", userBean.getPhoneCarrier());
    	
    	account.save();
    	
    	setModelAttributes(request, model);
    	return "user_profile";
    }
}
