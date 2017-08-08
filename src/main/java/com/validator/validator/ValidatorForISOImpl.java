package com.validator.validator;

import com.validator.service.CheckCurrencyISO4217Service;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Validator implementation (for ISO4217) of {@link Validator} interface.
 *
 * @author Leonid Dubravsky
 */

public class ValidatorForISOImpl implements Validator {

    private String message = null;
    JSONArray jsonArrayAnswer;

    public ValidatorForISOImpl(JSONArray jsonArrayAnswer) {
        this.jsonArrayAnswer = jsonArrayAnswer;
    }

    @Override
    public boolean validation(JSONObject jsonObj, int tradeNumber) {

        boolean isValidationSuccessfully = false;

            String ccyPair = (String) jsonObj.get("ccyPair");
            String fromCurrency = ccyPair.substring(0, 3);
            String toCurrency = ccyPair.substring(3);

        boolean isFrom = CheckCurrencyISO4217Service.isValidCurrencyISO4217(fromCurrency);
        boolean isTo = CheckCurrencyISO4217Service.isValidCurrencyISO4217(toCurrency);

        JSONObject jsonMessage1 = new JSONObject();
        JSONObject jsonMessage2 = new JSONObject();

        if (!isFrom) {
            isValidationSuccessfully = true;
            jsonMessage1.put("ErrorType", "ccyPair(currency from) invalid");
            jsonMessage1.put("TradeNumber", tradeNumber);
            jsonArrayAnswer.put(jsonMessage1);
        }

        if (!isTo) {
            isValidationSuccessfully = true;
            jsonMessage2.put("ErrorType", "ccyPair(currency to) invalid");
            jsonMessage2.put("TradeNumber", tradeNumber);
            jsonArrayAnswer.put(jsonMessage2);
        }

        if (isValidationSuccessfully) {
            setMessage(jsonMessage1.toString() + "\n" + jsonMessage2.toString());
        }

        return isValidationSuccessfully;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
