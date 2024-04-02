package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record CurrencyRates(
        String result,
        String provider,
        String documentation,
        String terms_of_use,
        int time_last_update_unix,
        String time_last_update_utc,
        int time_next_update_unix,
        String time_next_update_utc,
        int time_eol_unix,
        String base_code,
        @JsonProperty("error-type")
        String errorType,
        HashMap<String, Double> rates
) {
}


