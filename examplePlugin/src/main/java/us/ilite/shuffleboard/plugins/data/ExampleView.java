package us.ilite.shuffleboard.plugins.data;

import edu.wpi.first.shuffleboard.api.data.IncompatibleSourceException;
import edu.wpi.first.shuffleboard.api.sources.DataSource;
import edu.wpi.first.shuffleboard.api.widget.Description;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.fxmisc.easybind.EasyBind;
import org.fxmisc.easybind.monadic.PropertyBinding;
import us.ilite.shuffleboard.plugins.ChrisWidget;

//THIS IS REQUIRED OR IT WILL NOT WORK!!
@Description(name = "Example View", dataTypes = {String.class, Number.class, Boolean.class}, summary = "Just a test")

public class ExampleView extends ChrisWidget<Object> {
    private Pane root;
    enum colorState {
        RED,
        GREEN,
        BLUE
    }
    private colorState nextColor = colorState.RED;
    protected final ObjectProperty<DataSource> redSource = new SimpleObjectProperty<>(this, "red_source", DataSource.none());
    protected final ObjectProperty<DataSource> greenSource = new SimpleObjectProperty<>(this, "green_source", DataSource.none());
    protected final ObjectProperty<DataSource> blueSource = new SimpleObjectProperty<>(this, "blue_source", DataSource.none());


    private final PropertyBinding<Object> red_data
            = EasyBind.monadic(redSource).selectProperty(DataSource::dataProperty);
    private final PropertyBinding<Object> green_data
            = EasyBind.monadic(greenSource).selectProperty(DataSource::dataProperty);
    private final PropertyBinding<Object> blue_data
            = EasyBind.monadic(blueSource).selectProperty(DataSource::dataProperty);

    private TextField redField;
    private TextField greenField;
    private TextField blueField;


    public ExampleView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinWidth(64);
        borderPane.setMinHeight(32);

        redField = new TextField("RED: XXX");
        greenField = new TextField("GREEN: XXX");
        blueField = new TextField("BLUE: XXX");

        borderPane.setTop(redField);
        borderPane.setCenter(greenField);
        borderPane.setBottom(blueField);

        this.root = borderPane;

        red_data.addListener((__, prev, cur)->{
            updateField(redField, cur);

        });
        green_data.addListener((__, prev, cur)->{
            updateField(greenField, cur);

        });
        blue_data.addListener((__, prev, cur)->{
            updateField(blueField, cur);

        });


    }

    private void updateField(TextField field, Object cur) {
        if(cur != null) {
            if(cur instanceof Number) {
                field.setText(cur.toString());
            } else {
                field.setText(cur.getClass().toString());
            }
        }
    }

    @Override
    public void addSource(DataSource source) throws IncompatibleSourceException {
        super.addSource(source);
        colorState current = nextColor;

        if(current == null) {
            return;
        }

        switch(nextColor) {
            case RED: nextColor = colorState.GREEN;
            redSource.setValue(source);
            break;
            case GREEN: nextColor = colorState.BLUE;
                greenSource.setValue(source);
            break;
            case BLUE:
                blueSource.setValue(source);
                //deliberate fall through
            default: nextColor = null;
        }
    }
    @Override
    public Pane getView() {
        return root;
    }

}
