package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import models.CurrencyRates;
import utils.JsonUtils;

import static org.assertj.core.api.Assertions.assertThat;


public class ConversionRateSteps extends BaseSteps {

    private static CurrencyRates currencyRates;
    private static Response response;


    @Given("I send a request to the Currency Conversion API for Base code {string}")
    public void getConversion(String currencyName) {
        if (currencyName.isEmpty()) {
            response = openApiConnector.getConversionRates404(currencyName);
            return;
        }
        response = fetchCurrencyRates(currencyName);
        currencyRates = response.as(CurrencyRates.class);
    }

    @Then("the response should contain a status field with value {string}")
    public void responseStatusCheck(String result) {
        assertThat(currencyRates.result()).withFailMessage("Response Result mismatch").isEqualTo(result);
    }

    @And("the API should return a price for {string} within the range of {double} to {double}")
    public void validatePrice(String currency, double min, double max) {
        logger.info("Conversion rate for " + currency + "/" + currencyRates.base_code() + "= " + currencyRates.rates().get(currency));
        assertThat(currencyRates.rates().get(currency)).withFailMessage("Not in Range").isGreaterThan(min).isLessThan(max);
    }

    @Then("the API response should match the expected JSON schema")
    public void schemaValidation() {
        assertThat(JsonUtils.validateJsonSchema(response, System.getProperty("user.dir") + "/src/test/resources/json-schemas/currency-conversion.json"))
                .withFailMessage("JSON Schema Validation Error")
                .isTrue();
    }

    @And("the API should return exactly {int} currency pairs")
    public void rateListSizeCheck(int size) {
        logger.info("Fetched conversion rates for " + currencyRates.rates().size() + " countries");
        assertThat(currencyRates.rates().size()).withFailMessage("Size mismatch").isEqualTo(size);
    }

    @Then("the result is {string} and error-type should be {string}")
    public void validateErrorMessage(String result, String errorMessage) {
        logger.info("Intentional Failure: " + response.body().asString());
        assertThat(currencyRates.result()).withFailMessage("Result mismatch").isEqualTo(result);
        assertThat(currencyRates.errorType()).withFailMessage("Error type mismatch").isEqualTo(errorMessage);

    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        assertThat(response.statusCode()).withFailMessage("Incorrect Status Code").isEqualTo(statusCode);
    }
}
