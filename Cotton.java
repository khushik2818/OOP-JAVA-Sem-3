class Cotton extends Crop {
    Cotton(String soilType, SoilData soilData) {
        super("Cotton", soilType, soilData);
    }

    double getMinMoisture() {
        return 30.0;
    }

    String getAdvice() {
        if (isIrrigationRequired()) {
            return "Give the field light irrigation.";
        }
        return "Moisture is sufficient.";
    }
}