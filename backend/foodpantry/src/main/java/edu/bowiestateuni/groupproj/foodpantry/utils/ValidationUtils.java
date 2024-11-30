package edu.bowiestateuni.groupproj.foodpantry.utils;

import io.micrometer.common.util.StringUtils;

public class ValidationUtils {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private boolean isValidSSN(final String ssn){
        if (StringUtils.isBlank(ssn)) {
            return false;
        }
        final String ssnPattern = "^(?!000|666|9\\d\\d)(\\d{3})-(?!00)(\\d{2})-(?!0000)(\\d{4})$|^(?!000|666|9\\d\\d)(\\d{3})(?!00)(\\d{2})(?!0000)(\\d{4})$";

        return ssn.matches(ssnPattern);
    }
    private boolean isValidAccountNumber(final String accountNumber){
        if (StringUtils.isBlank(accountNumber)) {
            return false;
        }

        // Check that the account number contains only digits and is within the length of 8 to 12
        final String accountNumberPattern = "^\\d{8,12}$";

        return accountNumber.matches(accountNumberPattern);
    }
    private boolean isValidRoutingNumber(final String routingNumber) {

        if (StringUtils.isBlank(routingNumber)) {
            return false;
        }
        final String routingNumberPattern = "^\\d{9}$";
        return routingNumber.matches(routingNumberPattern);
    }
}
