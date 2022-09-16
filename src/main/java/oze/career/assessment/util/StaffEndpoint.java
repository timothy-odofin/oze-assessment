package oze.career.assessment.util;

public class StaffEndpoint {
    public static final String BASE="/staff";
    public static final String MEMBER="/member";
    public static final String ADD_MEMBER=MEMBER+"/add";
    public static final String FETCH_MEMBER=MEMBER+"/fetch";
    public static final String UPDATE_PROFILE=MEMBER+"/update/{uuid}";
    public static final String PHOTO_UPLOAD=MEMBER+"/upload/photograph/{uuid}";

    public static final String RETRIEVE_OR_UPDATE = MEMBER+"/retrieve/{uuid}";
}
