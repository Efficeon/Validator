package com.validator.validator;

import com.validator.service.CheckDateService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Validator implementation (for weekend) of {@link Validator} interface.
 *
 * @author Leonid Dubravsky
 */

public class ValidatorForWeekendImpl implements Validator {

    private String message = null;
    JSONArray jsonArrayAnswer;

    public ValidatorForWeekendImpl(JSONArray jsonArrayAnswer) {
        this.jsonArrayAnswer = jsonArrayAnswer;
    }

    @Override
    public boolean validation(JSONObject jsonObj, int tradeNumber) {

        boolean isValidationSuccessfully = true;

        if (!(jsonObj.get("type").equals("Spot") || jsonObj.get("type").equals("Forward"))) {
            return true;
        }

        String valueDate = (String) jsonObj.get("valueDate");

        boolean result = CheckDateService.isDateFallInWeekend(valueDate);

        JSONObject jsonMessage = new JSONObject();
        if (result) {
            isValidationSuccessfully = false;

            jsonMessage.put("ErrorType", "Value date cannot fall on weekend");
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
