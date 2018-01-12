package silen.scheduler.ui.valuebean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserAppConfig {

	private String userExtLibs;

	private String version;

	public String getVersion() {
		return version;
	}

	@Value("${version}")
	public void setVersion(String version) {
		this.version = version;
	}

	public String getUserExtLibs() {
		return userExtLibs;
	}

	@Value("${user.extlibs}")
	public void setUserExtLibs(String userExtLibs) {
		this.userExtLibs = userExtLibs;
	}
}
