package com.validator.validator;

import com.validator.service.CheckDateService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Validator implementation (for before date) of {@link Validator} interface.
 *
 * @author Leonid Dubravsky
 */

public class ValidatorForBeforeDateImpl implements Validator {

    private String message = null;
    JSONArray jsonArrayAnswer;

    public ValidatorForBeforeDateImpl(JSONArray jsonArrayAnswer) {
        this.jsonArrayAnswer = jsonArrayAnswer;
    }

    @Override
    public boolean validation(JSONObject jsonObject, int tradeNumber) {

        boolean isValidationSuccessfully = true;

        String valueDate = (String) jsonObject.get("valueDate");
        String tradeDate = (String) jsonObject.get("tradeDate");

        boolean isBeforeDate = CheckDateService.checkBeforeDate(valueDate, tradeDate);

        JSONObject answerJsonMessage = new JSONObject();
        if (isBeforeDate) {
            isValidationSuccessfully = false;

            answerJsonMessage.put("ErrorType", "Incorrect date(value date cannot be before trade date)");
            answerJsonMessage.put("TradeNumber", tradeNumber);
        }

        if (!isValidationSuccessfully) {
            setMessage(answerJsonMessage.toString());
            jsonArrayAnswer.put(answerJsonMessage);
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