package com.example.PantryTrackingAPI.enums;

public enum Units {
    // Volume Units
    ML("mL"), L("L"), TSP("tsp"), TBSP("tbsp"), FLOZ("fl oz"),
    CUP("cup"), PINT("pint"), QUART("quart"), GALLON("gallon"),

    // Weight Units
    MG("mg"), G("g"), KG("kg"), POUND("lb"), OUNCE("oz");

    private final String value;

    Units(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
