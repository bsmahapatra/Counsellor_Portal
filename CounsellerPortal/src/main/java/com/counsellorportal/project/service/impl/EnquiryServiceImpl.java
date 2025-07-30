package com.counsellorportal.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.counsellorportal.project.dto.ViewEnqsFilterRequest;
import com.counsellorportal.project.entity.Counsellor;
import com.counsellorportal.project.entity.Enquiry;
import com.counsellorportal.project.repo.CounsellorRepository;
import com.counsellorportal.project.repo.EnquiryRepository;
import com.counsellorportal.project.service.IEnquiryService;

import io.micrometer.common.util.StringUtils;

@Service
public class EnquiryServiceImpl implements IEnquiryService {
	
	@Autowired
	private EnquiryRepository enquiryRepo;
	
	@Autowired
	private CounsellorRepository counsellorRepo;

	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception {
		
		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);
		
		if(counsellor == null) {
			throw new Exception("No Counsellor Found");
		}
		
		// Associating counsellor to enquiry
		enq.setCounsellor(counsellor);
		Enquiry save = enquiryRepo.save(enq);
		if(save.getEnquiryId() != null){
			return true;
		}
		
		return false;
	}

	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {
		
		return  enquiryRepo.getEnquiresByCousellorId(counsellorId);
	
	}

	@Override
	public List<Enquiry> getEnquiriesWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId) {
		
		
		// QBE implementation (Dynamic Query Preparation)
		Enquiry enq = new Enquiry(); // entity
		
		if (StringUtils.isNotEmpty(filterReq.getClassMode())) {
			enq.setClassMode(filterReq.getClassMode());
		}
		
		if (StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enq.setCourseName(filterReq.getCourseName());
		}
		
		if (StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
			enq.setEnqStatus(filterReq.getEnqStatus());
		}
		
		Counsellor c = counsellorRepo.findById(counsellorId).orElse(null);
		enq.setCounsellor(c);
		
		Example<Enquiry> of = Example.of(enq); // dynamic query
		
		List<Enquiry> enqList = enquiryRepo.findAll(of);
		
		
		return enqList;
	}

	@Override
	public Enquiry getEnquiryById(Integer enqId) {
	
		return enquiryRepo.findById(enqId).orElse(null);
		
	}

}
