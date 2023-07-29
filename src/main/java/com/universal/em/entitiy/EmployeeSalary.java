package com.universal.em.entitiy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "employee_salary")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "ctc")
    private String ctc;

    @Column(name = "salary")
    private String salary;

    @Column(name = "basic_salary")
    private String basicSalary;

    @Column(name = "hra")
    private String hra;

    @Column(name = "conveyance_allowance")
    private String conveyanceAllowance;

    @Column(name = "performance_based")
    private String performanceBased;

    @Column(name = "statutory_allowance")
    private String statutoryAllowance;

    @Column(name = "created_at")
    @CreationTimestamp
    private String createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private String updatedAt;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "uan_no")
    private String uanNo;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "medical")
    private String medical;

    @Column(name = "txn_date")
    private String txnDate;

    @Column(name = "tds")
    private String tds;
}
