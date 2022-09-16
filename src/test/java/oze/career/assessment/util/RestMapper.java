package oze.career.assessment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.PatientResponseData;

import java.lang.reflect.Type;

public class RestMapper {
    public static Gson getGson(){

        return new Gson();
    }
    public static String mapToJson(Object obj) throws JsonProcessingException {

        return getGson().toJson(obj);
    }
    public static <T> T mapFromJson(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }
    public static ApiResponse<PatientResponseData> mapFromJson(String json) {
        Type patientResponse = new TypeToken<ApiResponse<PatientResponseData>>() {}.getType();
        return getGson().fromJson(json, patientResponse);
    }

}
