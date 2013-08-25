package com.asptttoulousenatation.web.creneau;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes
public class CreneauListController {

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
}
