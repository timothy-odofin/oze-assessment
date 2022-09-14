package oze.career.assessment.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
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
public class AppLog extends BaseEntity {
    private String description;
    private String affectedEntity;
    private String processorCode;



}
