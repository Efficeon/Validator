package com.validator.validator;

import com.validator.service.CheckDateService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Validator implementation (for end date) of {@link Validator} interface.
 *
 * @author Leonid Dubravsky
 */

public class ValidatorForEndDateImpl implements Validator {

    private String message = null;
    JSONArray jsonArrayAnswer;

    public ValidatorForEndDateImpl(JSONArray jsonArrayAnswer) {
        this.jsonArrayAnswer = jsonArrayAnswer;
    }

    @Override
    public boolean validation(JSONObject jsonObj, int tradeNumber) {

        boolean isValidationSuccessfully = true;

        if (!jsonObj.get("type").equals("VanillaOption")) {
            return true;
        }

        String expiryDate = (String) jsonObj.get("expiryDate");
        String premiumDate = (String) jsonObj.get("premiumDate");
        String deliveryDate = (String) jsonObj.get("deliveryDate");

        boolean isExpiryDate = CheckDateService.checkBeforeDate(expiryDate, deliveryDate);
        boolean isPremiumDate = CheckDateService.checkBeforeDate(premiumDate, deliveryDate);

        JSONObject jsonObjValidationMSG = new JSONObject();

        if (!isExpiryDate || !isPremiumDate) {
            isValidationSuccessfully = false;

            jsonObjValidationMSG.put("ErrorType", "Invalid expiry And Premium Date");
            jsonObjValidationMSG.put("TradeNumber", tradeNumber);
        }

        if (!isValidationSuccessfully) {
            setMessage(jsonObjValidationMSG.toString());
            jsonArrayAnswer.put(jsonObjValidationMSG);
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