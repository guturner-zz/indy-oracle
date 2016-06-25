package org.indyoracle.security;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.sdk.servlet.account.AccountResolver;

/**
 * Helper class for accessing Stormpath account properties.
 * 
 * @author Guy
 *
 */
public class UserManager {
	
	/**
	 * Retrieves the currently authenticated user from Stormpath.
	 * 
	 * @param request
	 * @return the currently authenticated Stormpath account OR null if no user is logged in.
	 */
	public static Account getCurrentUser(HttpServletRequest request) {
		if (AccountResolver.INSTANCE.hasAccount(request)) {
    		Account account = AccountResolver.INSTANCE.getRequiredAccount(request);
	    	if (account != null) {
	    		return account;
	    	}
    	}
		
		return null;
	}
	
	public static Account getAccountByEmail(HttpServletRequest request, String email) {
		Application app = AccountManager.getApplication();
		
		for (Account a : app.getAccounts()) {
			if (a.getEmail().equals(email)) {
				return a;
			}
		}
		
		return null;
	}
	
	public static boolean isEmailInUse(HttpServletRequest request, String email) {
		Application app = AccountManager.getApplication();
		
		for (Account a : app.getAccounts()) {
			if (a.getEmail().equals(email)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean updateUser(Account account, String firstName, String lastName, String phoneNumber, String phoneCarrier) {
		try {
			account.setGivenName(firstName);
	    	account.setSurname(lastName);
	    	
	    	CustomData customData = account.getCustomData();
	    	customData.put("phoneNumber", phoneNumber);
	    	customData.put("phoneCarrier", phoneCarrier);
	    	
	    	account.save();
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
		
		return true;
	}
	
	public static Account createNewAccount(String firstName, String lastName, String email, String password, String approverEmail, String approverId) {
		Account account = null;
		
		try {
			ClientBuilder builder = Clients.builder(); 
			Client client = builder.build();
			Application app = AccountManager.getApplication();
			
			account = client.instantiate(Account.class)
					.setGivenName(firstName)
					.setEmail(email)
				    .setSurname(lastName)
				    .setPassword(password);

			CustomData customData = account.getCustomData();
			customData.put("approverEmail", approverEmail);
			customData.put("approverId", approverId);
			customData.put("role", "USER");
			
			app.createAccount(account);
			
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
		
		return account;
	}
	
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}
}
