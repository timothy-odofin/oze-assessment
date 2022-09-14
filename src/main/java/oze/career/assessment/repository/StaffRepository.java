package oze.career.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import oze.career.assessment.model.entity.Staff;

import java.util.Optional;
import java.util.UUID;

import static oze.career.assessment.util.ParamName.UUID;

public interface StaffRepository extends JpaRepository<Staff,Long> {
    @Query("select st from Staff st where st.uuid=:uuid")
    Optional<Staff> findByUuid(@Param(UUID) UUID uuid);
}
