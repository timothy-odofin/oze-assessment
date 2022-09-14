package oze.career.assessment.model.entity;

import lombok.*;
import oze.career.assessment.model.entity.listener.PatientAuditTrailListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author JIDEX
 */
@Entity
@Table(name = "patient")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(PatientAuditTrailListener.class)
public class Patient extends BaseEntity {
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate dob;
    private LocalDateTime last_visit_date;
    private String code;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private Staff createdBy;
    @Transient
    private String name;
    @Transient
    private Integer age;

}
