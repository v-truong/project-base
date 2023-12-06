package com.example.common.config;

public interface Constants {
    public static final String TENANT_ID_PROP = "tenantId";
    public static final String TENANT_ID_COLUMN = "tenant_id";
    public static final int ID_MAX_LENGTH = 36;
    public static final String USER_SYSTEM = "SYSTEM";
    public static final String ZERO_UUID = "00000000-0000-0000-0000-000000000000";
    public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    public static final int DEFAULT_PAGE_SIZE_MAX = 1000;
    public static final String ROLE_LBB="LDB";//role lãnh dạo bộ
    public static final String ROLE_LDC="LDC";//role lãnh đạo cục
    public static final String ROLE_CBB="CBB";//role cán bộ Bộ
    public static final String ROLE_CBC="CBC";//role cán bộ cục
    // public static final String  STATUS_CBC=

}
