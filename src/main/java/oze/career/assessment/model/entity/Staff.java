package oze.career.assessment.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import oze.career.assessment.model.entity.listener.StaffAuditTrailListener;

import javax.persistence.*;
import java.util.UUID;
/**
 *
 * @author JIDEX
 */
@Entity
@Table(name = "staff")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(StaffAuditTrailListener.class)
public class Staff extends BaseEntity {
    private String firstName;
    private String lastName;
    private String middleName;


    @Column(name = "uuid", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID uuid;
    @Transient
    private String name;
    private String photoImage;

}
