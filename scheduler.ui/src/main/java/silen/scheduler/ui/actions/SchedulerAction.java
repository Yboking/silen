package silen.scheduler.ui.actions;


import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import silen.scheduler.ui.server.UIConnector;
import silen.scheduler.ui.valuebean.UserAppConfig;



@Controller
public class SchedulerAction {
	private Logger logger = LogManager.getLogger(this.getClass());

	private UserAppConfig uac;

	@Resource
	public void setWebConfig(UserAppConfig userAppConfig) {
		this.uac = userAppConfig;
		init();
	}

	private void init() {

		logger.info(" UserAppConfig userAppConfig : " + uac.getUserExtLibs());
//		MasterSystem.main(new String[] { String.format("%s=%s",
//				ConfigKeys.USER_EXT_LIBS, uac.getUserExtLibs()) });

		
		UIConnector.main(null);
	}

}
