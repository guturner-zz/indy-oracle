package org.indyoracle.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.tenant.Tenant;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
    	return "home";
    }
    
    @RequestMapping("/users")
    public String listUsers(Model model) {
    	// Retrieves the object that talks to Stormpath from API details:
    	ClientBuilder cBuilder = Clients.builder();
    	Client client = cBuilder.build();
    	
    	Tenant tenant = client.getCurrentTenant();
    	ApplicationList applications = tenant.getApplications(
    										Applications.where(Applications.name().eqIgnoreCase("indy-oracle")));
    	
    	Application app = applications.iterator().next();
    	
    	AccountList accounts = app.getAccounts();
    	model.addAttribute("users", accounts);
    	
    	return "list_users";
    }
}
