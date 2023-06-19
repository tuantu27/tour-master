package com.example.tour.config.app;

import org.springframework.lang.Nullable;

public enum CustomStatus {
    INVALID_REQUEST(1000, 401, Series.CLIENT_ERROR, "Invalid request"),
    INVALID_CLIENT(1001, 401, Series.CLIENT_ERROR, "Invalid client"),
    INVALID_INPUT(1002, 400, Series.CLIENT_ERROR, "Invalid input"),
    INVALID_NOT_FOUND(1003, 400, Series.CLIENT_ERROR, "Invalid not found"),
    UNAUTHORIZED_CLIENT(1004, 401, Series.CLIENT_ERROR, "Unauthorized client"),
    REDIRECT_URI_MISMATCH(1005, 401, Series.CLIENT_ERROR, "Redirect uri mismatch"),
    ACCESS_DENIED(1006, 401, Series.CLIENT_ERROR, "Access denied"),
    UNSUPPORTED_RESPONSE_TYPE(1007, 401, Series.CLIENT_ERROR, "Unsupported response type"),
    INVALID_GRANT(1008, 401, Series.CLIENT_ERROR, "Invalid grant"),
    INVALID_USERNAME_PASSWORD(1009, 401, Series.CLIENT_ERROR, "Invalid username password"),
    DUPLICATED_USERNAME(1010, 400, Series.CLIENT_ERROR, "Duplicated username"),
    DUPLICATED_EMAIL(1011, 400, Series.CLIENT_ERROR, "Duplicated email"),
    DUPLICATED_VALUE(1012, 400, Series.CLIENT_ERROR, "Duplicated value"),
    USERNAME_AVAILABLE_INVALID_CLIENT(1013, 400, Series.CLIENT_ERROR, "Username available invalid client"),
    UNSUPPORTED_GRANT_TYPE(1014, 401, Series.CLIENT_ERROR, "Unsupported grant type"),
    DISABLED_USER(1015, 401, Series.CLIENT_ERROR, "Disable user"),
    INVALID_SCOPE(1016, 401, Series.CLIENT_ERROR, "Invalid scope"),
    INVALID_TOKEN(1017, 401, Series.CLIENT_ERROR, "Invalid token"),
    INVALID_GATEWAY(1018, 401, Series.CLIENT_ERROR, "Invalid gateway"),
    INVALID_TYPE(1019, 406, Series.CLIENT_ERROR, "Invalid type"),
    INSUFFICIENT_SCOPE(1021, 401, Series.CLIENT_ERROR, "Insufficient scope"),
    DATA_NOT_FOUND(1022, 406, Series.CLIENT_ERROR, "Data not found"),
    DATA_FOUND(1023, 406, Series.CLIENT_ERROR, "Data found"),
    PARTNER_NOT_FOUND(1024, 406, Series.CLIENT_ERROR, "Partner not found"),
    USERNAME_NOT_FOUND(1025, 406, Series.CLIENT_ERROR, "Username not found"),
    SERVER_NOT_FOUND(1026, 406, Series.CLIENT_ERROR, "Server not found"),
    INVALID_SCHEMA(1027, 399, Series.REDIRECTION, "Invalid schema"),
    PASSWORD_CHECK(1028, 409, Series.CLIENT_ERROR, "Password check"),
    PASSWORD_NOT_FOUND(1029, 303, Series.REDIRECTION, "Password not found"),
    DEVICE_TYPE_CHECK(1030, 333, Series.REDIRECTION, "Device type check"),
    CONFLICT_VALUE(1031, 409, Series.CLIENT_ERROR, "Conflict value"),
    HTTP_NOT_ALLOWED(1033, 404, Series.CLIENT_ERROR, "Http not allowed"),
    ROLE_NOT_FOUND(1034, 406, Series.CLIENT_ERROR, "Role not found"),
    ROLE_NOT_ACCESS(1035, 406, Series.CLIENT_ERROR, "Role not access"),
    DATA_NOT_ACCEPT(1036, 406, Series.CLIENT_ERROR, "Data not accept"),
    SOMETHING_WENT_WRONG(5000, 500, Series.SERVER_ERROR, "Something went wrong"),
    SOMETHING_WENT_WRONG_KEYCLOAK(5001, 500, Series.SERVER_ERROR, "Something went wrong"),
    SUCCESSFULLY(2000, 200, Series.SUCCESSFUL, "Successfully"),
    /**
     * hungpg bo sung error code
     */
    DUPLICATED_ID(1037, 400, Series.CLIENT_ERROR, "Duplicated id"),
    INPUT_REQUIRED(1038, 400, Series.CLIENT_ERROR, "Input required");

    private final int value;
    private final int httpStatusCode;
    private final Series series;
    private final String reasonPhrase;

    CustomStatus(int value, int httpStatusCode, Series series, String reasonPhrase) {
        this.value = value;
        this.series = series;
        this.reasonPhrase = reasonPhrase;
        this.httpStatusCode = httpStatusCode;
    }

    public int getValue() {
        return value;
    }

    public Series getSeries() {
        return series;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public static CustomStatus valueOf(int statusCode) {
        CustomStatus status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
        return status;
    }

    private static final CustomStatus[] VALUES;

    static {
        VALUES = values();
    }

    @Nullable
    public static CustomStatus resolve(int statusCode) {
        // Use cached VALUES instead of values() to prevent array allocation.
        for (CustomStatus status : VALUES) {
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }

    public static enum Series {
        INFORMATIONAL(1),
        SUCCESSFUL(2),
        REDIRECTION(3),
        CLIENT_ERROR(4),
        SERVER_ERROR(5);

        private final int value;

        private Series(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
