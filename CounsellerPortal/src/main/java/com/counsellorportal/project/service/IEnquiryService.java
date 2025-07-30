package com.counsellorportal.project.service;

import java.util.List;

import com.counsellorportal.project.dto.ViewEnqsFilterRequest;
import com.counsellorportal.project.entity.Enquiry;

public interface IEnquiryService {
	
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception;
	public List<Enquiry> getAllEnquiries(Integer counsellorId);
	public List<Enquiry> getEnquiriesWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId);
	public Enquiry getEnquiryById(Integer enqId);

}
