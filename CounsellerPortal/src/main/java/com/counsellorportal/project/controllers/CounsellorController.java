package com.counsellorportal.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.counsellorportal.project.dto.DashboardResponse;
import com.counsellorportal.project.entity.Counsellor;
import com.counsellorportal.project.service.ICounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller

public class CounsellorController {

	@Autowired
	private ICounsellorService counsellorService;

	@GetMapping("/")
	public String index(Model model) {

		Counsellor cobj = new Counsellor();

		// sending data from controller to UI
		model.addAttribute("counsellor", cobj);

		// returning view name
		return "index";
	}

	
	
	@PostMapping("/login")
	public String handleLogin(Counsellor counsellor, Model model, HttpServletRequest request) {

		Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());
		if (c == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			return "index";
		} else {

			// valid Login, Store counsellorId in session for future purpose
			HttpSession session = request.getSession(true);
			session.setAttribute("counsellorId", c.getCounsellorId());

			return "redirect:/dashboard";
		}
	}
	
	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req, Model model) {
		
		// get existing seesion obj
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		DashboardResponse dbresObj = counsellorService.getDashboardInfo(counsellorId);
		model.addAttribute("dashboardInfo", dbresObj);
		
		return "dashboard";
	}
	
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		Counsellor cobj = new Counsellor();
		
		// sending data from controller to UI
		model.addAttribute("counsellor",cobj);
		return "register";
	}
	
	
	
	@PostMapping("/register")
	public String handleRegistration(Counsellor counsellor, Model model) {
		
		
		Counsellor byEmail = counsellorService.findByEmail(counsellor.getEmail());		
		if( (byEmail != null)) {
			model.addAttribute("emsg", "Mail is Already Associated with some other person, plz use another one");
			return "register"; 
		}
		
		boolean isRegistered = counsellorService.register(counsellor);
		
		if(isRegistered) {
			//success
			model.addAttribute("smsg", "Registration Success...!!!");
		} else {
			// error 
			model.addAttribute("emsg", "Registration Failed.....!!");
		}
		
		return "register";
	}
	
	
	

	 @GetMapping("/logout")
	 public String logout(HttpServletRequest req) {
		 // get existing seesion and invalidate it
		 HttpSession session = req.getSession(false);
		 session.invalidate();
		 
		 //redirect to login page
		 return "redirect:/";
		
	}
}
