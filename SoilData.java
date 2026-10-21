class SoilData {
    private double moisture;
    private double temperature;
    private double humidity;

    SoilData(double moisture, double temperature, double humidity) {
        this.moisture = moisture;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    double getMoisture() {
        return moisture;
    }

    double getTemperature() {
        return temperature;
    }

    double getHumidity() {
        return humidity;
    }

    void validate() throws InvalidSensorDataException {
        if (moisture < 0 || moisture > 100) {
            throw new InvalidSensorDataException("Invalid moisture value.");
        }

        if (humidity < 0 || humidity > 100) {
            throw new InvalidSensorDataException("Invalid humidity value.");
        }

        if (temperature < -50 || temperature > 70) {
            throw new InvalidSensorDataException("Invalid temperature value.");
        }
    }
}