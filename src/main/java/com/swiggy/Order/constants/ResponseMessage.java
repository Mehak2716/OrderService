package com.swiggy.Order.constants;

public class ResponseMessage {

    public static final String INVALID_REGISTRATION_ARGUMENT = "Invalid registration request. Username, password, and location must be provided.";

    public static final String USERNAME_ALREADY_IN_USE = "Username already in use.Try with other name";

    public static final String INVALID_ORDER_ARGUMENT = "Invalid order request. Restaurant and menu item must be provided.";

    public static final String EMPTY_MENU_LIST = "Order should contain atleast one item.";

    public static final String USER_NOT_FOUND = "User not found";

    public static final String RESOURCE_FORBIDDEN = "Cannot access this resource";

    public static final String SERVICE_UNAVAILABLE = "Fulfillment Service Unavailable";

    public static final String DELIVERY_PARTNER_UNAVAILABLE = "Cannot place order due to unavailability of delivery partner.";

    public static final String GRPC_UNKNOWN_ERROR = "Unexpected Error occured calling grpc service";

}
