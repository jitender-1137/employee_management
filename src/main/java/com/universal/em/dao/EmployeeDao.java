package com.universal.em.dao;

import com.universal.em.entitiy.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
    boolean existsByCompanyEmpIdAndEnabledTrue(String id);

    Employee getByIdAndEnabledTrue(Long id);

    Employee getByCompanyEmpIdAndEnabledTrue(String empId);

    Employee findByIdAndCompanyEmpIdAndEnabledTrue(Long id, String empId);
}
