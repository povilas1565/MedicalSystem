package org.nil;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import org.nil.StringJoin.UrlValue;

public class LatLng implements UrlValue, Serializable {
    private static final long serialVersionUID = 1L;

    public double lat;
    public double lng;

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LatLng() {
    }

    @Override
    public String toString() {
        return toUrlValue();
    }

    @Override
    public String toUrlValue() {
        return String.format(Locale.ENGLISH, "%.8f,%.8f", lat, lng);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LatLng latLng = (LatLng) o;
        return Double.compare(latLng.lat, lat) == 0 && Double.compare(latLng.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lng);
    }
}

