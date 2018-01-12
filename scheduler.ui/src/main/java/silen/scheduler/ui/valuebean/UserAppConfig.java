package silen.scheduler.ui.valuebean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserAppConfig {

	
	private String userExtLibs;

	public String getUserExtLibs() {
		return userExtLibs;
	}

	
	 @Value("${user.extlibs}")
	public void setUserExtLibs(String userExtLibs) {
		this.userExtLibs = userExtLibs;
	}
}
