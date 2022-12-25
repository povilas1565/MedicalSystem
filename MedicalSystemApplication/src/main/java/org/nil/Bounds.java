package org.nil;

import java.io.Serializable;

public class Bounds implements Serializable {

    private static final long serialVersionUID = 1L;
    public LatLng northeast;
    public LatLng southwest;

    @Override
    public String toString() {
        return String.format("[%s, %s]", northeast, southwest);
    }
}
