package com.validator.validator;

import com.validator.service.CheckDateService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Validator implementation (for start date) of {@link Validator} interface.
 *
 * @author Leonid Dubravsky
 */

public class ValidatorForStartDateImpl implements Validator {

    private String message = null;
    JSONArray jsonArrayAnswer;

    public ValidatorForStartDateImpl(JSONArray jsonArrayAnswer) {
        this.jsonArrayAnswer = jsonArrayAnswer;
    }

    @Override
    public boolean validation(JSONObject jsonObj, int tradeNumber) {

        boolean isValidationSuccessfully = true;

        if (!jsonObj.get("type").equals("VanillaOption")) {
            return true;
        }

        if (!jsonObj.get("style").toString().equalsIgnoreCase("AMERICAN")) {
            return true;
        }

        String tradeDate = (String) jsonObj.get("tradeDate");
        String expiryDate = (String) jsonObj.get("expiryDate");
        String excerciseStartDate = (String) jsonObj.get("excerciseStartDate");

        boolean isTradeDate = CheckDateService.checkBeforeDate(excerciseStartDate, tradeDate);
        boolean isExpiryDate = CheckDateService.checkBeforeDate(excerciseStartDate, expiryDate);

        JSONObject jsonMessage = new JSONObject();
        if (isTradeDate || !isExpiryDate) {
            isValidationSuccessfully = false;

            jsonMessage.put("ErrorType", "Incorrect start date");
            jsonMessage.put("TradeNumber", tradeNumber);
        }

        if (!isValidationSuccessfully) {
            setMessage(jsonMessage.toString());
            jsonArrayAnswer.put(jsonMessage);
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