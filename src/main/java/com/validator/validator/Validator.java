package com.validator.validator;

import org.json.JSONObject;

/**
 * Interface for validators implementation.
 *
 * @author Leonid Dubravsky
 */

public interface Validator {

    public boolean validation(JSONObject jsonObject, int tradeNumber);
    public String getMessage();
    public void setMessage(String message);

}
