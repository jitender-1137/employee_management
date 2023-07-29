package com.universal.em.dao;

import com.universal.em.entitiy.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSalaryDao extends JpaRepository<EmployeeSalary, Long> {
    boolean existsByIdAndEmployeeIdAndEnabledTrue(Long id, String empId);

    EmployeeSalary getByIdAndEmployeeIdAndEnabledTrue(Long id, String empId);

    EmployeeSalary findByEmployeeIdAndEnabledTrue(String empId);

    boolean existsByEmployeeIdAndEnabledTrue(String id);
}
