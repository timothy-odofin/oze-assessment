package oze.career.assessment.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author JIDEX
 */
@Entity
@Table(name = "app_log")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppLog extends BaseEntity {
    @Type(type="text")
    private String description;
    @Type(type="text")
    private String affectedEntity;
    private String processorCode;

}
