package com.counsellorportal.project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.counsellorportal.project.entity.Enquiry;

public interface EnquiryRepository extends JpaRepository<Enquiry, Integer>{
	
	@Query(value = "select * from enquiries_tbl where counsellor_id=:counsellorId", nativeQuery = true)
	public List<Enquiry> getEnquiresByCousellorId(Integer counsellorId);

}
