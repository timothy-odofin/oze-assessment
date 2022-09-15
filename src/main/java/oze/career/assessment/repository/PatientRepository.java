package oze.career.assessment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import oze.career.assessment.model.entity.Patient;


public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Query("select p from Patient p where p.age>=:minAge")
    Page<Patient> listByMinAge(@Param("minAge") Integer minAge, Pageable pageable);
}
