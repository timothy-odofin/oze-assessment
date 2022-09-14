package oze.career.assessment.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

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
public class Staff extends BaseEntity {
    private String firstName;
    private String lastName;
    @Id
    @GenericGenerator(name = "string_based_custom_sequence", strategy = "oze.career.assessment.config.CustomUserIdGenerator")
    @GeneratedValue(generator = "string_based_custom_sequence")
    @Column(name = "uuid", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID uuid;
    private String userCategory;
    public String getFullName(){
        return this.firstName+" "+this.lastName;

    }

}
