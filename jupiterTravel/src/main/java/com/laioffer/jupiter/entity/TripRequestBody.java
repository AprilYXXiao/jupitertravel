package com.laioffer.jupiter.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TripRequestBody {
    private final Place tripPlace;

    @JsonCreator
    public TripRequestBody(@JsonProperty("trip") Place tripPlace) {
        this.tripPlace = tripPlace;
    }


    public Place getTripPlace() {
        return tripPlace;
    }

}
