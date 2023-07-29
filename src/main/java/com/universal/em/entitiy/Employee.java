package com.universal.em.entitiy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "employee_mail")
    private String employeeMail;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "designation")
    private String designation;

    @Column(name = "company_emp_id")
    private String companyEmpId;

    @Column(name = "department")
    private String department;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    @CreationTimestamp
    private String createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private String updatedAt;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "pan_no")
    private String panNo;

    @Column(name = "doj")
    private String doj;

}
