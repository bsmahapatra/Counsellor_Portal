package com.counsellorportal.project.service;

import com.counsellorportal.project.dto.DashboardResponse;
import com.counsellorportal.project.entity.Counsellor;

public interface ICounsellorService {
	
	public Counsellor findByEmail(String email);
	
	public boolean register(Counsellor counsellor);
	
	public Counsellor login(String email, String pwd);
	
	public DashboardResponse getDashboardInfo(Integer counsellorId);


}
