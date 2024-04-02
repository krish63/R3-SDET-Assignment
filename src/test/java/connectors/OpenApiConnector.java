package connectors;

import core.EnvSerenity;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;

public class OpenApiConnector {

    private RequestSpecification baseRequest() {
        return SerenityRest.with()
                .accept(ContentType.JSON)
                .baseUri(EnvSerenity.baseURI);
    }

    public Response getConversionRates(String currencyName) {
        return baseRequest()
                .get("v6/latest/" + currencyName)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("application/json")
                .extract().response();
    }

    public Response getConversionRates404(String currencyName) {
        return baseRequest()
                .get("v6/latest/" + currencyName)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .contentType("text/html")
                .extract().response();
    }
}
