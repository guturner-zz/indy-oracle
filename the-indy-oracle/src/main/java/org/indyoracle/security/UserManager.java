package org.indyoracle.security;

import javax.servlet.http.HttpServletRequest;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;

public class UserManager {
	
	public static Account getCurrentUser(HttpServletRequest request) {
		if (AccountResolver.INSTANCE.hasAccount(request)) {
    		Account account = AccountResolver.INSTANCE.getRequiredAccount(request);
	    	if (account != null) {
	    		return account;
	    	}
    	}
		
		return null;
	}
}
