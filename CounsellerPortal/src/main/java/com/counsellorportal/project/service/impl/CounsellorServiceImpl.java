package com.counsellorportal.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.counsellorportal.project.dto.DashboardResponse;
import com.counsellorportal.project.entity.Counsellor;
import com.counsellorportal.project.entity.Enquiry;
import com.counsellorportal.project.repo.CounsellorRepository;
import com.counsellorportal.project.repo.EnquiryRepository;
import com.counsellorportal.project.service.ICounsellorService;

@Service
public class CounsellorServiceImpl implements ICounsellorService {

	@Autowired
	private CounsellorRepository counsellorRepo;

	@Autowired
	private EnquiryRepository enquiryRepo;
	
	@Override
	public Counsellor findByEmail(String email) {
		return counsellorRepo.findByEmail(email);
		
	}

	@Override
	public boolean register(Counsellor counsellor) {
		

		Counsellor savedCounsellor = counsellorRepo.save(counsellor);

		if (null != savedCounsellor.getCounsellorId()) {
			return true;
		}
		return false;
	}

	@Override
	public Counsellor login(String email, String pwd) {

		Counsellor counsellor = counsellorRepo.findByEmailAndPwd(email, pwd);

		return counsellor;
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer counsellorId) {

		DashboardResponse response = new DashboardResponse();

		List<Enquiry> enqList = enquiryRepo.getEnquiresByCousellorId(counsellorId);

		int totalEnq = enqList.size();

		int enrolledEnqs = enqList.stream()
								  .filter(e -> e.getEnqStatus().equals("Enrolled"))
								  .collect(Collectors.toList())
								  .size();
		
		int lostEnqs = enqList.stream()
							  .filter(e -> e.getEnqStatus().equals("Lost"))
							  .collect(Collectors.toList())
							  .size();
		
		int openEnqs = enqList.stream()
				   			  .filter(e -> e.getEnqStatus().equals("Open"))
				   			  .collect(Collectors.toList())
				   			  .size();


		response.setTotalEnqs(totalEnq);
		response.setEnrolledEnqs(enrolledEnqs);
		response.setLostEnqs(lostEnqs);
		response.setOpenEnqs(openEnqs);
		
		return response;
	}



}
