package com.validator;

import com.validator.service.CheckCurrencyISO4217Service;
import com.validator.validator.ValidatorForBeforeDateImpl;
import com.validator.validator.ValidatorForCounterpartyImpl;
import com.validator.validator.ValidatorForWeekendImpl;
import com.validator.service.CheckDateService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidatorApplicationTests {

    @Test
    public void testBeforeDate() {

        String firstDate = "2016-08-11";
        String secondDate = "2016-08-18";

        boolean result = CheckDateService.checkBeforeDate(firstDate, secondDate);
        Assert.isTrue(result);
    }

    @Test
    public void testIfDateFallInWeekend() {

        String date = "2017-06-11";
        boolean result = CheckDateService.isDateFallInWeekend(date);
        Assert.isTrue(result);
    }

    @Test
    public void testIfCurrencyIsValidISO4217() {

        String currency = "USD";
        boolean result = CheckCurrencyISO4217Service.isValidCurrencyISO4217(currency);
        Assert.isTrue(result);
    }

    @Test
    public void testBeforeDateValidator() {

        String tradeJson = "{\"customer\":\"PLUTO1\"," +
                "\"ccyPair\":\"EURUSD\"," +
                "\"type\":\"Spot\"," +
                "\"direction\":\"BUY\"," +
                "\"tradeDate\":\"2016-08-11\"," +
                "\"amount1\":1000000.00," +
                "\"amount2\":1120000.00," +
                "\"rate\":1.12," +
                "\"valueDate\":\"2016-08-15\"," +
                "\"legalEntity\":\"CS Zurich\"," +
                "\"trader\":\"Johann Baumfiddler\"}, \n";

        JSONArray validationMessages = new JSONArray();
        ValidatorForBeforeDateImpl validatorForBeforeDate = new ValidatorForBeforeDateImpl(validationMessages);

        try {
            JSONObject jsonBeforeDate = new JSONObject(tradeJson);

            boolean result = validatorForBeforeDate.validation(jsonBeforeDate, 1);

            Assert.isTrue(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testWeekendValidator() {

        String tradeJson = "{\"customer\":\"PLUTO1\"," +
                "\"ccyPair\":\"EURUSD\"," +
                "\"type\":\"Spot\"," +
                "\"direction\":\"BUY\"," +
                "\"tradeDate\":\"2016-08-11\"," +
                "\"amount1\":1000000.00," +
                "\"amount2\":1120000.00," +
                "\"rate\":1.12," +
                "\"valueDate\":\"2016-08-15\"," +
                "\"legalEntity\":\"CS Zurich\"," +
                "\"trader\":\"Johann Baumfiddler\"}, \n";

        JSONArray validationMessages = new JSONArray();
        ValidatorForWeekendImpl validatorForWeekend = new ValidatorForWeekendImpl(validationMessages);

        boolean result = false;

        try {
            JSONObject jsonObject = new JSONObject(tradeJson);

            result = validatorForWeekend.validation(jsonObject, 1);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Assert.isTrue(result);

    }

    @Test
    public void testCustomerValidator() {

        String tradeJson ="{\"customer\":\"PLUTO1\"," +
                "\"ccyPair\":\"EURUSD\"," +
                "\"type\":\"Spot\"," +
                "\"direction\":\"BUY\"," +
                "\"tradeDate\":\"2016-08-11\"," +
                "\"amount1\":1000000.00," +
                "\"amount2\":1120000.00," +
                "\"rate\":1.12," +
                "\"valueDate\":\"2016-08-15\"," +
                "\"legalEntity\":\"CS Zurich\"," +
                "\"trader\":\"Johann Baumfiddler\"}, \n";

        JSONArray validationMessages = new JSONArray();
        ValidatorForCounterpartyImpl validatorForCounterparty = new ValidatorForCounterpartyImpl(validationMessages);
        boolean result = false;
        try {
            JSONObject jsonObject = new JSONObject(tradeJson);

            result = validatorForCounterparty.validation(jsonObject, 1);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Assert.isTrue(result);

    }

    @Test
    public void testCustomerValidator2() {

        String tradeJson = "{\"customer\":\"Touraj\"," +
                "\"ccyPair\":\"EURUSD\"," +
                "\"type\":\"Spot\"," +
                "\"direction\":\"BUY\"," +
                "\"tradeDate\":\"2016-08-11\"," +
                "\"amount1\":1000000.00," +
                "\"amount2\":1120000.00," +
                "\"rate\":1.12," +
                "\"valueDate\":\"2016-08-15\"," +
                "\"legalEntity\":\"CS Zurich\"," +
                "\"trader\":\"Johann Baumfiddler\"}, \n";

        JSONArray validationMessages = new JSONArray();
        ValidatorForCounterpartyImpl validatorForCounterparty = new ValidatorForCounterpartyImpl(validationMessages);
        boolean result = false;
        try {
            JSONObject jsonObjectCustomer = new JSONObject(tradeJson);

            result = validatorForCounterparty.validation(jsonObjectCustomer, 1);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Validation Message : "  + validatorForCounterparty.getMessage());

        org.junit.Assert.assertEquals(false, result);

    }

}
