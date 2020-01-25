package us.ilite.shuffleboard.plugins;

import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;
import us.ilite.shuffleboard.plugins.rgbcolor.RGBColorWIdget;

import java.util.List;

//THIS IS REQUIRED OR IT WILL NOT WORK!!
@Description(
        group = "us.ilite",
        name="RGB ColorWidget Plugin",
        version ="1.0.0",
        summary="A panel that will allow three channels (RGB) to be registered. " +
                "The panel will then be colored to the color the RGB makes")
public class RGBColorWidgetPlugin extends Plugin {

    @Override
    public List<ComponentType> getComponents() {
        return List.of(
                WidgetType.forAnnotatedWidget(RGBColorWIdget.class)
        );
    }
}
