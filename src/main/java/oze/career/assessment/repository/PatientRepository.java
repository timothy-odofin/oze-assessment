package oze.career.assessment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import oze.career.assessment.model.entity.Patient;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Query("select p from Patient p where p.age>=:minAge")
    Page<Patient> listByMinAge(@Param("minAge") Integer minAge, Pageable pageable);
    Optional<Patient> findByPatientCode(String patientCode);
    @Modifying
    @Transactional
    @Query(value="delete from Patient patient where (patient.lastVisitDate between :dateFrom and :dateTo) or (patient.lastVisitDate=:dateFrom or patient.lastVisitDate=:dateTo)")
    void deletePatient(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo")LocalDate dateTo);

    @Query(value="select patient from Patient patient where (patient.lastVisitDate between :dateFrom and :dateTo) or (patient.lastVisitDate=:dateFrom or patient.lastVisitDate=:dateTo)")
    Page<Patient> checkPatientExistence(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo")LocalDate dateTo, Pageable pageable);
}
