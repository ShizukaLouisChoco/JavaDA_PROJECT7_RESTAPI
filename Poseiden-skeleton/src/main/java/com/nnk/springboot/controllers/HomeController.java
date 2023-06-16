package com.nnk.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getAuthorities().toString().equals("[ADMIN]")) {
		log.info("redirection to /admin/home");
			return "redirect:/admin/home";
		}
		log.info("redirection to /bidList/list");
		return "redirect:/bidList/list";
	}

	@RequestMapping("/admin/home")
	public String adminHome()
	{
		log.info("heading to /admin/home");
		return "home";
	}


}
