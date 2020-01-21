package us.ilite.shuffleboard.plugins.data;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;
import edu.wpi.first.shuffleboard.api.data.DataType;

import java.util.Map;
import java.util.function.Function;

public class ExampleDataType extends ComplexDataType<ExampleDataPoint> {

    public static DataType INSTANCE = new ExampleDataType();
    private ExampleDataType() {
        super("ExampleDataPoint", ExampleDataPoint.class);
    }

    @Override
    public Function<Map<String, Object>, ExampleDataPoint> fromMap() {
        return map->{
             Object value = map.getOrDefault("value", 0.0d);
             return new ExampleDataPoint(Double.parseDouble(value.toString()));
        };
    }

    @Override
    public ExampleDataPoint getDefaultValue() {
        return new ExampleDataPoint(0.0d);
    }
}
