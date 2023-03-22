package controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.nil.GeoCoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping(value = "api")
@Api
public class GeoCodingController {

    private static final String GEOCODING_URI = "https://maps.googleapis.com/maps/api/geocode/json";

    @Autowired
    private Environment env;

    @RequestMapping("/getGeoCoding/{address}")
    @ApiOperation("Получение геоданных для Местоположения")
    public GeoCoding getGeoCodingForLoc(@PathVariable(value = "address") String address) {

        address = address.replaceAll("\\s", "");
        log.info("Getting geodata for location '{}'", address);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GEOCODING_URI).queryParam("address", address)
                .queryParam("key", env.getProperty("apiKey")).queryParam("sensor", true);

        log.info("Calling geocoding api with: " + builder.toUriString());

        GeoCoding geoCoding = restTemplate.getForObject(builder.toUriString(), GeoCoding.class);

        if (geoCoding != null) {
            log.info(geoCoding.toString());
        }

        return geoCoding;
    }
}
