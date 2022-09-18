package oze.career.assessment.model.entity.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import oze.career.assessment.model.entity.Staff;
import oze.career.assessment.util.AppUtil;

import javax.persistence.*;
import java.util.UUID;

@Component
@Slf4j
public class StaffAuditTrailListener {

    @PrePersist
    private void beforeCreate(Staff staff) {
        if (staff.getUuid() == null)
            staff.setUuid(UUID.randomUUID());
        log.info("[USER AUDIT] About to add a user {}", staff);
    }

    @PostPersist
    private void afterCreate(Staff staff) {
        log.info("[USER AUDIT] new user added {}", staff);
    }

    @PostUpdate
    private void afterAnyUpdate(Staff staff) {
        log.info("[USER AUDIT] update complete for user: " + staff.getId());
    }


    @PostLoad
    private void afterLoad(Staff staff) {
        staff.setName(staff.getFirstName().concat(" ")
                .concat(AppUtil.evaluate(staff.getMiddleName()))
                .concat(" ").concat(staff.getLastName()));
        staff.setRegistrationDate(staff.getDateCreated());
    }
}
