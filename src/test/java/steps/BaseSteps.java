package steps;

import connectors.OpenApiConnector;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class BaseSteps {
    protected static final Logger logger = LogManager.getLogger(BaseSteps.class);
    protected static final OpenApiConnector openApiConnector = new OpenApiConnector();

    @NotNull
    protected Response fetchCurrencyRates(String currencyName) {
        return openApiConnector.getConversionRates(currencyName);
    }

}
