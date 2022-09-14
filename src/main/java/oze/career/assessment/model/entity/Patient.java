package oze.career.assessment.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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
public class Patient extends BaseEntity {
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDate dob;
    private LocalDateTime last_visit_date;
    private String code;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private Staff createdBy;

    public String getFullName() {
        return this.firstName + " " + this.lastName;

    }

}
