package utils;

import java.util.HashMap;

public class Constants {

    public static final String[] REQUEST_INFO_LABELS = new String[]{"Start Date: ", "End Date: ", "Price: ", "Discount: ",
        "Status: ", "Submission Date: ", "Confirmation Date: ", "Distance: ", "Total Cost: "};

    public static final String SLASH = "/";
    public static final String SEPARATOR = "|";

    public static final HashMap<Integer, String> statuses = new HashMap<Integer, String>();

    static {
        statuses.put(0, "SUBMITTED");
        statuses.put(1, "WAITING_FOR_CONFIRMATION");
        statuses.put(2, "ACCEPTED");
        statuses.put(3, "REJECTED");
    }

    public static final String SUBMITTED = "SUBMITTED";
    public static final String WAITING_FOR_CONFIRMATION = "WAITING_FOR_CONFIRMATION";
    public static final String ACCEPTED = "ACCEPTED";
    public static final String REJECTED = "REJECTED";

}
