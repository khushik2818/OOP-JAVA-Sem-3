import java.io.*;
import java.util.ArrayList;

class AgriSense {
    public static void main(String[] args) {
        ArrayList<String> report = new ArrayList<String>();
        ArrayList<String> errors = new ArrayList<String>();

        int total = 0;
        int invalid = 0;
        int irrigation = 0;

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("sensor_data.txt"));

            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                total++;

                try {
                    Crop crop = createCrop(line);
                    SoilData soil = crop.getSoilData();

                    String status;
                    if (crop.isIrrigationRequired()) {
                        status = "Irrigation required";
                        irrigation++;
                    } else {
                        status = "No irrigation required";
                    }

                    String result = "\nField " + total + " - "
                            + crop.getCropName()
                            + "\nSoil: " + crop.getSoilType()
                            + "\nMoisture: " + soil.getMoisture() + "%"
                            + "\nTemperature: " + soil.getTemperature() + " C"
                            + "\nHumidity: " + soil.getHumidity() + "%"
                            + "\nStatus: " + status
                            + "\nAdvice: " + crop.getAdvice()
                            + "\n";

                    System.out.println(result);
                    report.add(result);

                } catch (InvalidSensorDataException e) {
                    invalid++;
                    errors.add("Line " + lineNumber + ": " + e.getMessage());
                }
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error reading sensor_data.txt: "
                    + e.getMessage());
            return;
        }

        String summary = "\nSUMMARY"
                + "\nValid fields: " + (total - invalid)
                + "\nIrrigation required: " + irrigation
                + "\nNo irrigation required: "
                + (total - invalid - irrigation)
                + "\nInvalid records: " + invalid;

        System.out.println(summary);
        report.add(summary);

        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter("agrisense_report.txt"));

            for (String result : report) {
                bw.write(result);
                bw.newLine();
            }

            bw.close();

            bw = new BufferedWriter(
                    new FileWriter("agrisense_errors.txt"));

            if (errors.isEmpty()) {
                bw.write("No errors found.");
            } else {
                for (String error : errors) {
                    bw.write(error);
                    bw.newLine();
                }
            }

            bw.close();

            System.out.println("\nReport saved to agrisense_report.txt");
            System.out.println("Errors saved to agrisense_errors.txt");

        } catch (IOException e) {
            System.out.println("Error saving files: " + e.getMessage());
        }
    }

    static Crop createCrop(String line)
            throws InvalidSensorDataException {

        String[] data = line.split(",");

        if (data.length != 5) {
            throw new InvalidSensorDataException(
                    "Enter crop, soil type, moisture, temperature and humidity.");
        }

        String name = data[0].trim();
        String soilType = data[1].trim();

        if (name.isEmpty() || soilType.isEmpty()) {
            throw new InvalidSensorDataException(
                    "Crop name and soil type cannot be empty.");
        }

        double moisture;
        double temperature;
        double humidity;

        try {
            moisture = Double.parseDouble(data[2].trim());
            temperature = Double.parseDouble(data[3].trim());
            humidity = Double.parseDouble(data[4].trim());
        } catch (NumberFormatException e) {
            throw new InvalidSensorDataException(
                    "Sensor readings must be numbers.");
        }

        SoilData soil = new SoilData(moisture, temperature, humidity);
        soil.validate();

        switch (name.toLowerCase()) {
            case "wheat":
                return new Wheat(soilType, soil);
            case "rice":
                return new Rice(soilType, soil);
            case "tomato":
                return new Tomato(soilType, soil);
            case "cotton":
                return new Cotton(soilType, soil);
            default:
                throw new InvalidSensorDataException(
                        "Unknown crop: " + name);
        }
    }
}