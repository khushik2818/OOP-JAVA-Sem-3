class Wheat extends Crop {
    Wheat(String soilType, SoilData soilData) {
        super("Wheat", soilType, soilData);
    }

    double getMinMoisture() {
        return 35.0;
    }

    String getAdvice() {
        if (isIrrigationRequired()) {
            return "Water wheat lightly.";
        }
        return "No watering needed now.";
    }
}