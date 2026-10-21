abstract class Crop implements IrrigationSystem {
    private String cropName;
    private String soilType;
    private SoilData soilData;

    Crop(String cropName, String soilType, SoilData soilData) {
        this.cropName = cropName;
        this.soilType = soilType;
        this.soilData = soilData;
    }

    String getCropName() {
        return cropName;
    }

    String getSoilType() {
        return soilType;
    }

    SoilData getSoilData() {
        return soilData;
    }

    abstract double getMinMoisture();

    abstract String getAdvice();

    public boolean isIrrigationRequired() {
        return soilData.getMoisture() < getMinMoisture();
    }
}