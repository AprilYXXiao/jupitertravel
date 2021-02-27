package com.laioffer.jupiter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = Location.Builder.class)
public class Location {
    @JsonProperty("place_id")
    private final String placeId;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("lat")
    private final double lat;

    @JsonProperty("lng")
    private final double lng;

    @JsonProperty("formatted_address")
    private final String address;

    public String getPlaceId() {
        return placeId;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getAddress() {
        return address;
    }

    private Location(Builder builder) {
        this.placeId = builder.placeId;
        this.name = builder.name;
        this.lat = builder.lat;
        this.lng = builder.lng;
        this.address = builder.address;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Builder {
        @JsonProperty("place_id")
        private String placeId;

        @JsonProperty("name")
        private String name;

        @JsonProperty("lat")
        private double lat;

        @JsonProperty("lng")
        private double lng;

        @JsonProperty("formatted_address")
        private String address;


        public Builder placeId(String locationId) {
            this.placeId = placeId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder lat(double lat) {
            this.lat = lat;
            return this;
        }

        public Builder lng(double lng) {
            this.lng = lng;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Location build() {
            return new Location(this);
        }
    }
}