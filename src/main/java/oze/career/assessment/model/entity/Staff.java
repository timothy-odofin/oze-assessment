package oze.career.assessment.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import oze.career.assessment.model.entity.listener.StaffEvent;

import javax.persistence.*;
import java.util.UUID;

/**
 *
 * @author JIDEX
 */
@Entity
@Table(name = "staff_profile")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(StaffEvent.class)
public class Staff extends BaseEntity {
    private String firstName;
    private String lastName;
    private String middleName;
    @Id
    @GenericGenerator(name = "string_based_custom_sequence", strategy = "oze.career.assessment.config.CustomUserIdGenerator")
    @GeneratedValue(generator = "string_based_custom_sequence")
    @Column(name = "uuid", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID uuid;
    @Transient
    private String name;
    private String photoImage;

}
