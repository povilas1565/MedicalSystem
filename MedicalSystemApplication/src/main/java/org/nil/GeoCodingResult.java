package org.nil;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
    public class GeoCodingResult {
        @JsonProperty(value = "formatted_address")
        String formattedAddress;

        @JsonProperty("place_id")
        String placeId;

        @JsonProperty("geometry")
        Geometry geometry;


        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        @Override
        public String toString() {
            return "GeoCodingResult [geometry="+geometry+"formattedAddress=" + formattedAddress + ", placeId=" + placeId + "]";
        }
}
