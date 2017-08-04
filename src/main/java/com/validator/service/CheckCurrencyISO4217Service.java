package com.validator.service;

import java.util.Currency;

/**
 * Service for ISO4217 check.
 *
 * @author Leonid Dubravsky
 */

public class CheckCurrencyISO4217Service {

    public static boolean isValidCurrencyISO4217(String currency) {

        try {
            Currency cur = Currency.getInstance(currency);
            cur.getCurrencyCode();
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}