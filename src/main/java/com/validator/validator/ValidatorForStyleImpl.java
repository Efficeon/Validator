package com.validator.validator;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.List;

/**
 * Validator implementation (for style) of {@link Validator} interface.
 *
 * @author Leonid Dubravsky
 */

public class ValidatorForStyleImpl implements Validator {

    private String message = null;
    JSONArray jsonArrayAnswer;

    public ValidatorForStyleImpl(JSONArray jsonArrayAnswer) {
        this.jsonArrayAnswer = jsonArrayAnswer;
    }

    @Override
    public boolean validation(JSONObject jsonObj, int tradeNumber) {

        List<String> validStylesList = Arrays.asList("AMERICAN", "EUROPEAN");

        boolean isValidationSuccessfull = false;

        if (!jsonObj.get("type").equals("VanillaOption")) {
            return true;
        }

        String style = jsonObj.get("style").toString().toUpperCase();

        boolean isStyle = validStylesList.contains(style);

        JSONObject jsonObjValidationMSG = new JSONObject();

        if (!isStyle) {
            isValidationSuccessfull = true;

            jsonObjValidationMSG.put("ErrorType", "Style not valid");
            jsonObjValidationMSG.put("TradeNumber", tradeNumber);
        }

        if (isValidationSuccessfull) {
            setMessage(jsonObjValidationMSG.toString());
            jsonArrayAnswer.put(jsonObjValidationMSG);
        }

        return isValidationSuccessfull;
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