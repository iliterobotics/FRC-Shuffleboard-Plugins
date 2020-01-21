package us.ilite.shuffleboard.plugins.data;

import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.Map;

public class ExampleDataPoint extends ComplexData<ExampleDataPoint> {

    public ExampleDataPoint(double value) {
        this.value = value;
    }
    private final double value;
    @Override
    public Map<String, Object> asMap() {
        return Map.of("value", value);
    }
}
