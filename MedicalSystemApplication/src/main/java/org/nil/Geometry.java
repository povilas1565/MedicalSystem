package org.nil;

import java.io.Serializable;

public class Geometry implements Serializable {
    private static final long serialVersionUID = 1L;

    public Bounds bounds;

    public LatLng location;

    public LocationType locationType;

    public Bounds viewport;

    @Override
    public String toString() {
        return String.format(
                "[Geometry: %s (%s) bounds=%s, viewport=%s]", location, locationType, bounds, viewport);
    }
}
