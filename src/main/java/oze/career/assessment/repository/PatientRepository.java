package oze.career.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import oze.career.assessment.model.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
