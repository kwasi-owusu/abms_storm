package com.woleapp.util;

public interface Constants
{
    int ASCII_VALUE_OF_POINT = 46;
    int ASCII_VALUE_OF_ZERO = 48;
    int ASCII_VALUE_OF_COMMA = 44;
    int ASCII_VALUE_OF_SPACE = 32;

    int TRANSACTION_FUND_WALLET = 1;
    int TRANSACTION_CASH_IN = 2;
    int TRANSACTION_CASH_OUT = 3;
    int TRANSACTION_QUICK = 4;

    int USER_TYPE_AGENT = 1;
    int USER_TYPE_MERCHANT = 2;
    int USER_TYPE_NONE = -1;

    int USER_LEVEL = 2;
    int USER_BRANCH = 2;
    int AGENCY_ID = 1;

    String PN_DATA = "Data";
    String PN_DATA_2 = "data";
    String PN_STATUS = "Status";
    String PN_MESSAGE = "message";

    String PN_ERROR = "error";
    String PN_DIRECTION = "direction";
    String PN_DIRECTION_VALUE = "request";
    String PN_REQUEST = "request";
    String PN_RESPONSE = "response";

    String PN_TERMINAL_ID = "terminalId";
    String PN_ACTION = "action";
    String PN_TRANSACTION = "transaction";
    String PN_PIN = "pin";

    String PN_REFERENCE = "reference";
    String PN_AMOUNT = "amount";
    String PN_CURRENCY = "currency";

    String PN_BANK_CODE = "bankCode";
    String PN_DESCRIPTION = "description";
    String PN_DESTINATION = "destination";

    String ACTION_ACCOUNT_QUERY = "AQ";
    String ACTION_FUNDS_TRANSFER = "FT";
    String ACTION_TRANSACTION_STATUS = "TS";
    String ACTION_PAY_BILL = "PB";
    String ACTION_VIRTUAL_TOPUP = "VTU";
    String ACTION_BALANCE_ENQUIRY = "BE";

    String DESTINATION_ENDPOINT_MOBILE = "M";
    String DESTINATION_ENDPOINT_ACCOUNT = "A";

    String ACCOUNT_NUMBER_SOURCE = "2063449787";
    String ACCOUNT_NUMBER_DESTINATION = "2077939278";
}
