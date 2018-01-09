package silen.scheduler.ui.actions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeAction {
	
	@RequestMapping("/index")
	public String hello() {
		return "index";
	}
}
