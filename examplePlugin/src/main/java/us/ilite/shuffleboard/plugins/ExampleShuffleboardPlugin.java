package us.ilite.shuffleboard.plugins;

import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.data.DataTypes;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;
import us.ilite.shuffleboard.plugins.data.ExampleDataType;
import us.ilite.shuffleboard.plugins.data.ExampleView;

import java.util.List;
import java.util.Map;

//THIS IS REQUIRED OR IT WILL NOT WORK!!
@Description(group = "us.ilite", name="Example Plugin", version ="1.0.0", summary="Just an example")
public class ExampleShuffleboardPlugin extends Plugin {


    @Override
    public List<DataType> getDataTypes() {
        return List.of(
                DataTypes.All
        );
    }

    @Override
    public List<ComponentType> getComponents() {
        return List.of(
                WidgetType.forAnnotatedWidget(ExampleView.class)
        );
    }

    @Override
    public Map<DataType, ComponentType> getDefaultComponents() {
        return Map.of(
                DataTypes.All, WidgetType.forAnnotatedWidget(ExampleView.class)
        );
    }
}
