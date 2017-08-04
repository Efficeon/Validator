package com.validator.validator;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Master validator for initialization all validators.
 *
 * @author Leonid Dubravsky
 */

public class MasterValidatorImpl {
    List<Validator> validators = new ArrayList<>();
    JSONArray jsonArrayAnswer;
    JSONArray jsonArrayQuery;

    public MasterValidatorImpl(JSONArray jsonArrayAnswer) {
        this.jsonArrayAnswer = jsonArrayAnswer;
    }

    public void startValidation(String tradeRequest) {
        jsonArrayQuery = new JSONArray(tradeRequest);

        initValidators();
        executeAllValidators();
    }

    private void initValidators()
    {
        ValidatorForBeforeDateImpl validatorForBeforeDate = new ValidatorForBeforeDateImpl(jsonArrayAnswer);
        ValidatorForWeekendImpl validatorForWeekend = new ValidatorForWeekendImpl(jsonArrayAnswer);
        ValidatorForISOImpl validatorForISO = new ValidatorForISOImpl(jsonArrayAnswer);
        ValidatorForCounterpartyImpl validatorForCustomer = new ValidatorForCounterpartyImpl(jsonArrayAnswer);
        ValidatorForStyleImpl validatorForStyle = new ValidatorForStyleImpl(jsonArrayAnswer);
        ValidatorForStartDateImpl validatorForStartDate = new ValidatorForStartDateImpl(jsonArrayAnswer);
        ValidatorForEndDateImpl validatorForEndDate = new ValidatorForEndDateImpl(jsonArrayAnswer);

        validators.add(validatorForBeforeDate);
        validators.add(validatorForWeekend);
        validators.add(validatorForISO);
        validators.add(validatorForCustomer);
        validators.add(validatorForStyle);
        validators.add(validatorForStartDate);
        validators.add(validatorForEndDate);

    }

    public void executeAllValidators()
    {
        for (int i = 0; i <jsonArrayQuery.length() ; i++) {
            for (Validator validator : validators) {
                validator.validation(jsonArrayQuery.getJSONObject(i), i+1);
            }
        }
    }
}
