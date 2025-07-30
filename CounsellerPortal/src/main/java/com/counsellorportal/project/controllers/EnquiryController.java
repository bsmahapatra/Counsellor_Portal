package com.counsellorportal.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.counsellorportal.project.dto.ViewEnqsFilterRequest;
import com.counsellorportal.project.entity.Enquiry;
import com.counsellorportal.project.service.IEnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
	private IEnquiryService enquiryService;
	
	@PostMapping("/filter-enqs")
	public String filterEnquries(ViewEnqsFilterRequest viewEnqsFilterRequest,HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		
		//Search from binding object 2308
//		ViewEnqsFilterRequest filterReq  = new ViewEnqsFilterRequest();
//		model.addAttribute("viewEnqFilter", filterReq);

		//2308
		List<Enquiry> enqsList = enquiryService.getEnquiriesWithFilter(viewEnqsFilterRequest, counsellorId);
		
		model.addAttribute("enquiries",enqsList);
		
		return "viewEnqsPage";
	}
	
	@GetMapping("/view-enquiries")
	public String getEnquires(HttpServletRequest request,Model model) {
		
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		List<Enquiry> enqList = enquiryService.getAllEnquiries(counsellorId);
		
		model.addAttribute("enquiries",enqList);
		
		//Search from binding object 2308
		ViewEnqsFilterRequest filterReq  = new ViewEnqsFilterRequest();
		model.addAttribute("viewEnqsFilterRequest", filterReq);
		
		
		return "viewEnqsPage";
	}
	
	
	@GetMapping("/enquiry")
	public String addEnquiry(Model model) {
		
		Enquiry enquiry = new Enquiry();
		model.addAttribute("enquiry", enquiry);
		
		return "enquiryForm";
	}
	
	@GetMapping("/editEnq")
	public String editEnquiry(@RequestParam("enqId") Integer enqId,Model model) {
		
		Enquiry enquiry = enquiryService.getEnquiryById(enqId);
		model.addAttribute("enquiry",enquiry);
		return "enquiryForm";
	}
	

	
	@PostMapping("/addEnq")
	public String handleAddEnquiry( Enquiry enquiry, HttpServletRequest req, Model model)throws Exception {
		
		// get existing session obj
		
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		boolean isSaved = enquiryService.addEnquiry(enquiry, counsellorId);
		
		if (isSaved) {
			model.addAttribute("smsg", " Enquiry Added Successfully");
		} else {                 
			model.addAttribute("emsg", " Failed To Add Enquiry");
		}
		
		//To clear the form after adding .......
		enquiry = new Enquiry();
		model.addAttribute("enquiry", enquiry);
		
		return "enquiryForm";
	}

	
	
	
}
