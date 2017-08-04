package com.validator.validator;

import com.validator.service.CheckCurrencyISO4217Service;
import org.json.JSONArray;
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

        if (!jsonObj.get("type").equals("VanillaOption")) {
            return true;
        }

        String payCcy = (String) jsonObj.get("payCcy");
        String premiumCcy = (String) jsonObj.get("premiumCcy");

        boolean isPayCcy = CheckCurrencyISO4217Service.isValidCurrencyISO4217(payCcy);
        boolean isPremiumCcy = CheckCurrencyISO4217Service.isValidCurrencyISO4217(premiumCcy);

        JSONObject jsonMessage1 = new JSONObject();
        JSONObject jsonMessage2 = new JSONObject();

        if (!isPayCcy) {
            isValidationSuccessfully = true;
            jsonMessage1.put("ErrorType", "payCcy invalid");
            jsonMessage1.put("TradeNumber", tradeNumber);
            jsonArrayAnswer.put(jsonMessage1);
        }

        if (!isPremiumCcy) {

            isValidationSuccessfully = true;
            jsonMessage2.put("ErrorType", "premium invalid");
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
