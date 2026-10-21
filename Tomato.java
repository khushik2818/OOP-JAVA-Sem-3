class Tomato extends Crop {
    Tomato(String soilType, SoilData soilData) {
        super("Tomato", soilType, soilData);
    }

    double getMinMoisture() {
        return 45.0;
    }

    String getAdvice() {
        if (isIrrigationRequired()) {
            return "Water the tomato plants.";
        }
        return "Avoid over-watering.";
    }
}