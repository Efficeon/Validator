package com.validator.validator;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.List;

/**
 * Validator implementation (for counterparty) of {@link Validator} interface.
 *
 * @author Leonid Dubravsky
 */

public class ValidatorForCounterpartyImpl implements Validator {

    private String message = null;
    JSONArray jsonArrayAnswer;

    public ValidatorForCounterpartyImpl(JSONArray jsonArrayAnswer) {
        this.jsonArrayAnswer = jsonArrayAnswer;
    }

    @Override
    public boolean validation(JSONObject jsonObject, int tradeNumber) {

        List<String> customers = Arrays.asList("PLUTO1", "PLUTO2");

        boolean isValidationSuccessfully = true;

        String counterparty = (String) jsonObject.get("customer");

        boolean isCustomer = customers.contains(counterparty);

        JSONObject jsonMessage = new JSONObject();

        if (!isCustomer) {
            isValidationSuccessfully = false;

            jsonMessage.put("ErrorType", "The counterparty is incorrect or not one of the supported");
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
