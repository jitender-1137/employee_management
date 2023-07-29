package com.universal.em.dao;

import com.universal.em.entitiy.CompanyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDao extends JpaRepository<CompanyDetail, Long> {
}
