class Rice extends Crop {
    Rice(String soilType, SoilData soilData) {
        super("Rice", soilType, soilData);
    }

    double getMinMoisture() {
        return 50.0;
    }

    String getAdvice() {
        if (isIrrigationRequired()) {
            return "Water the field to keep the soil moist.";
        }
        return "Moisture is sufficient.";
    }
}