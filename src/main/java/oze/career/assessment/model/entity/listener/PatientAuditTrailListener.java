package oze.career.assessment.model.entity.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import oze.career.assessment.model.entity.Patient;
import oze.career.assessment.util.AppUtil;
import oze.career.assessment.util.CodeUtil;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;

@Component
@Slf4j
public class PatientAuditTrailListener {

    @PrePersist
    private void beforeCreate(Patient patient) {
        patient.setPatientCode(CodeUtil.generateCode());
        log.info("[USER AUDIT] About to add a patient {}", patient);
    }
    @PostPersist
    private void afterCreate(Patient patient) {
        log.info("[PATIENT AUDIT] new patient added {}", patient);
    }
    @PostUpdate
    private void afterAnyUpdate(Patient patient) {
        log.info("[PATIENT AUDIT] update complete for patient: " + patient.getId());
    }


    @PostLoad
    private void afterLoad(Patient patient) {
        patient.setName(patient.getFirstName().concat(" ")
                .concat(AppUtil.evaluate(patient.getMiddleName()))
                .concat(" ").concat(patient.getLastName()));

    }
}
