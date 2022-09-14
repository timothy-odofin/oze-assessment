package oze.career.assessment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import oze.career.assessment.exception.RecordNotFoundException;
import oze.career.assessment.mapper.Mapper;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.StaffResponse;
import oze.career.assessment.model.entity.Staff;
import oze.career.assessment.repository.StaffRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static oze.career.assessment.util.AppCode.CREATED;
import static oze.career.assessment.util.AppCode.OKAY;
import static oze.career.assessment.util.MessageUtil.*;
import static oze.career.assessment.util.ParamName.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl  implements StaffService{

private final StaffRepository staffRepository;
    @Override
    public ApiResponse<String> addStaff(StaffRequest payload) {
        staffRepository.save(Mapper.convertObject(payload, Staff.class));
        log.info("Data {}", payload);
        return ApiResponse.<String>builder()
                .code(CREATED)
                .data(String.format(STAFF_UPDATED,CREATE))
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

    @Override
    public ApiResponse<String> deleteStaff(UUID uuid) {
       staffRepository.delete(findStaff(uuid));

        return ApiResponse.<String>builder()
                .message(SUCCESS)
                .code(OKAY)
                .data(DELETE_SUCCESSFUL)
                .build();
    }
    @Override
    public ApiResponse<String> updateStaff(StaffRequest payload, UUID uuid) {
        Staff staff = findStaff(uuid);
        staff.setFirstName(payload.getFirstName());
        staff.setLastName(payload.getLastName());
        staff.setMiddleName(payload.getMiddleName());
        staffRepository.save(staff);
        return ApiResponse.<String>builder()
                .data(String.format(STAFF_UPDATED,UPDATED))
                .code(OKAY)
                .message(SUCCESS)
                .build();
    }

    @Override
    public ApiResponse<StaffResponse> retrieveStaff(UUID uuid) {
        return ApiResponse.<StaffResponse>builder()
                .code(OKAY)
                .data(Mapper.convertObject(findStaff(uuid),StaffResponse.class))
                .message(SUCCESS)
                .build();
    }

    @Override
    public ApiResponse<List<StaffResponse>> listStaff(int page, int size) {
        List<Staff> staffList = staffRepository.findAll(PageRequest.of(page,size, Sort.by(SORTING_COL).descending())).toList();
        if(staffList.isEmpty())
            throw new RecordNotFoundException(RECORD_NOT_FOUND);
        return ApiResponse.<List<StaffResponse>>builder()
                .message(SUCCESS)
                .data(Mapper.convertList(staffList,StaffResponse.class))
                .code(OKAY)
                .build();
    }
}
