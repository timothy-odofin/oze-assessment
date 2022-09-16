package oze.career.assessment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
public class RestMapper {
    private static Gson getGson(){

        return new Gson();
    }
    public static String mapToJson(Object obj) throws JsonProcessingException {

        return getGson().toJson(obj);
    }
    public static <T> T mapFromJson(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }

}
