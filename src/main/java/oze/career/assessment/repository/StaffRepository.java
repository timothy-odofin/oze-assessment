package oze.career.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import oze.career.assessment.model.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff,Long> {
}
