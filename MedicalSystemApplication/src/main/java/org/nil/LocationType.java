package org.nil;
import org.nil.StringJoin.UrlValue;

public enum LocationType implements UrlValue {
    ROOFTOP,
    RANGE_INTERPOLATED,
    GEOMETRIC_CENTER,
    APPROXIMATE,
    UNKNOWN;

    @Override
    public String toUrlValue() {
        if (this == UNKNOWN) {
            throw new UnsupportedOperationException("Shouldn't use LocationType.UNKNOWN in a request.");
        }
        return name();
    }

}
