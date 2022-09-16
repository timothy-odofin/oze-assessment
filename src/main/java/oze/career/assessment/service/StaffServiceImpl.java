package oze.career.assessment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import oze.career.assessment.exception.RecordNotFoundException;
import oze.career.assessment.mapper.Mapper;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.StaffResponse;
import oze.career.assessment.model.entity.Staff;
import oze.career.assessment.repository.StaffRepository;
import oze.career.assessment.util.AppUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                .code(HttpStatus.CREATED)
                .data(String.format(STAFF_UPDATED,CREATE))
                .message(SUCCESS)
                .build();
    }

    @Override
    public Staff validateStaff(UUID uuid) {
    Optional<Staff> staffOptional = staffRepository.findByUuid(uuid);
    if(staffOptional.isEmpty())
        throw new RecordNotFoundException(INVALID_STAFF_UUID);
    return staffOptional.get();
    }

    @Override
    public ApiResponse<String> deleteStaff(UUID uuid) {
       staffRepository.delete(validateStaff(uuid));

        return ApiResponse.<String>builder()
                .message(SUCCESS)
                .code(HttpStatus.OK)
                .data(DELETE_SUCCESSFUL)
                .build();
    }
    @Override
    public ApiResponse<String> updateStaff(StaffRequest payload, UUID uuid) {
        Staff staff = validateStaff(uuid);
        staff.setFirstName(payload.getFirstName());
        staff.setLastName(payload.getLastName());
        staff.setMiddleName(payload.getMiddleName());
        staffRepository.save(staff);
        return ApiResponse.<String>builder()
                .data(String.format(STAFF_UPDATED,UPDATED))
                .code(HttpStatus.OK)
                .message(SUCCESS)
                .build();
    }

    @Override
    public ApiResponse<StaffResponse> retrieveStaff(UUID uuid) {
        return ApiResponse.<StaffResponse>builder()
                .code(HttpStatus.OK)
                .data(Mapper.convertObject(validateStaff(uuid),StaffResponse.class))
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
                .code(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<String> updatePhoto(java.util.UUID uuid, MultipartFile file) throws IOException {
        Staff staff = validateStaff(uuid);
        staff.setPhotoImage(AppUtil.getBase64Image(file.getBytes()));
        staffRepository.save(staff);
        return ApiResponse.<String>builder()
                .data(String.format(STAFF_UPDATED,UPDATED))
                .code(HttpStatus.OK)
                .message(SUCCESS)
                .build();
    }
}
