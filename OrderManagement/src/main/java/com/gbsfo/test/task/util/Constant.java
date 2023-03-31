package com.gbsfo.test.task.util;

public class Constant {

    public static final String LENGTH_VALIDATION_MESSAGE = "Length should be between 4 and 50";
    public static final String USER_NOT_FOUND_BY_USERNAME_ERROR_MESSAGE = "User with username '%s' not found";
    public static final String NO_ACCESS_ERROR_MESSAGE = "You don't have access to item with id %s";

    public static final String USERNAME_EXISTS_ERROR_MESSAGE = "Username '%s' already exists";
    public static final String USER_NOT_FOUND_BY_ID_ERROR_MESSAGE = "User with id '%d' not found";
    public static final String INCORRECT_PASSWORD_ERROR_MESSAGE = "Incorrect password";

    public static final String JWT_TOKEN_IS_INVALID = "Jwt token is invalid";
    public static final String AUTHORIZATION_HEADER_IS_INCORRECT_ERROR_MESSAGE = "Authorization header is incorrect";
    public static final String INCORRECT_PRICE_MESSAGE = "Price cannot be less that 0";
    public static final String ALL_ITEMS_MUST_BE_PAID_ERROR_MESSAGE = "To change status to %s all items must be paid";
    public static final String ORDER_ALREADY_HAS_THIS_STATUS_ERROR_MESSAGE = "Order already has status '%s'";
    public static final String YOU_CANNOT_ADD_NEW_ITEM_ERROR_MESSAGE = "You cannot add new item to order with status '%s'";
    public static final String ORDER_NOT_FOUND_ERROR_MESSAGE = "Order with id %d not found";
    public static final String ENTITY_NOT_FOUND_ERROR_MESSAGE = "Entity with id %d not found";
    public static final String PAYMENT_ERROR_MESSAGE = "You can't pay for someone else's order";
    public static final String ENTITY_UPDATING_ERROR_MESSAGE = "You cannot update someone's else record";
    public static final String ORDER_FULLY_PAID_ERROR_MESSAGE = "Order fully paid";
    public static final String PAYMENT_SUM_CONSTRAINT_ERROR_MESSAGE = "You can add up to %s money to the sum of this payment";
    public static final String PAYMENT_NOT_FOUND_ERROR_MESSAGE = "Payment with id %d not found";
    public static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";
    public static final String NUMBER_PATTERN = "№%d";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";


}
