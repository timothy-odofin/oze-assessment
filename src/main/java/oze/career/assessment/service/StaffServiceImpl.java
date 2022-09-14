package oze.career.assessment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import oze.career.assessment.exception.RecordNotFoundException;
import oze.career.assessment.mapper.Mapper;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.entity.Staff;
import oze.career.assessment.repository.StaffRepository;

import java.util.Optional;
import java.util.UUID;

import static oze.career.assessment.util.AppCode.CREATED;
import static oze.career.assessment.util.MessageUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl  implements StaffService{

private final StaffRepository staffRepository;
    @Override
    public ApiResponse<String> addStaff(StaffRequest payload) {
        staffRepository.save(Mapper.convertObject(payload, Staff.class));
        return ApiResponse.<String>builder()
                .code(CREATED)
                .data(STAFF_UPDATED)
                .message(SUCCESS)
                .build();
    }

    @Override
    public Staff findStaff(UUID uuid) {
    Optional<Staff> staffOptional = staffRepository.findByUuid(uuid);
    if(staffOptional.isEmpty())
        throw new RecordNotFoundException(RECORD_NOT_FOUND);
    return staffOptional.get();
    }
}
