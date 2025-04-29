package com.project.Dinning.enums;

public enum RestaurantType {
    FAST_FOOD("Fast Food"),
    FINE_DINING("Fine Dining"),
    CASUAL_DINING("Casual Dining"),
    CAFE("Cafe"),
    BUFFET("Buffet"),
    FOOD_TRUCK("Food Truck"),
    BAKERY("Bakery"),
    PUB("Pub"),
    BAR("Bar");

    private final String displayName;

    RestaurantType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
