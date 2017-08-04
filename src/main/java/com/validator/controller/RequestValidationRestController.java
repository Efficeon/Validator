package com.validator.controller;

import com.validator.validator.MasterValidatorImpl;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for trade request
 *
 * @author Leonid Dubravsky
 */

@RestController
public class RequestValidationRestController {

    /**
     * @param tradeRequest consumes a JSON array including trades information
     * @return validate trades information and returns validation results to the client
     * @throws Exception
     */
    @RequestMapping(
            value = "/validate",
            method = RequestMethod.POST,
            consumes = "text/plain")
    public String validatetrades(@RequestBody String tradeRequest) throws Exception {

        JSONArray jsonAnswer = new JSONArray();

        MasterValidatorImpl validator = new MasterValidatorImpl(jsonAnswer);
        validator.startValidation(tradeRequest);

        if (jsonAnswer.length() == 0) {
            return "Validation successful !";
        } else {

            return jsonAnswer.toString();
        }
    }
}
