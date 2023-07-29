package com.universal.em.entitiy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "companay_name")
    private String companyName;

    @Column(name = "company_mail")
    private String companyMail;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "company_logo")
    private String companyLogo;
}
