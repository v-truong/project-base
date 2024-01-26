package com.example.common.config;

public interface Constants {
    public static final String TENANT_ID_PROP = "tenantId";
    // Format: yyyyMMddHHmmss - Years 1900-2099
    public static final String DATE_TIME_REGEX = "^((19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])([01]\\d|2[0-3])([0-5]\\d)([0-5]\\d))$";
    public static final String TENANT_ID_COLUMN = "tenant_id";
    public static final int ID_MAX_LENGTH = 36;
    public static final String USER_SYSTEM = "SYSTEM";
    public static final String ZERO_UUID = "00000000-0000-0000-0000-000000000000";
    public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    public static final int DEFAULT_PAGE_SIZE_MAX = 1000;
    public  static  final int ISDELETE_TRUE =0;//data chua xoa

    public  static  final int ISDELETE_FALSE =1;//data da xoa
    public  static  final String ROLE_ADMIN="0";
    public  static  final String ROLE_SALESPERSON="1";
    public  static  final String ROLE_BUYER="2";
    public static final long TIME_lIVE_CODE=180;
    public static  final long TIME_SEND_CODE=90;
    public static final int STATUS_STORE_ACTIVE=1;
    public static final int STATUS_STORE_DEACTIVE=0;
    public static  final String TYPE_STORE_ALL ="all";
    public static  final String TYPE_STORE_CREATED ="created";
    public static  final String TYPE_STORE_JOINED ="joined";
    public static  final String TYPE_STORE_DELETED ="deleted";
    public static final String ORDER_STATUS_CREATED="created";//'Đơn mới'
    public static final String ORDER_STATUS_RECEIVED="received";//'Đã tiếp nhận'
    public static final String ORDER_STATUS_PACKAGING="packaging";//'Đang đóng gói'
    public static final String ORDER_STATUS_SHIPPING="shipping";//'Đang giao hàng'
    public static final String ORDER_STATUS_DONE="done";//
    public static final String ORDER_STATUS_CANCELED="canceled";//


}
