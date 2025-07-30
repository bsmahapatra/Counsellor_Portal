package com.counsellorportal.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.counsellorportal.project.entity.Counsellor;

public interface CounsellorRepository extends JpaRepository<Counsellor, Integer>{
	
	//select * from counsellor_tbl where email=:email
	public Counsellor findByEmail(String email);
	
	// select * from  counsellor_tbl where email=:email and pwd=:pwd
	public Counsellor findByEmailAndPwd(String email, String pwd);

}
