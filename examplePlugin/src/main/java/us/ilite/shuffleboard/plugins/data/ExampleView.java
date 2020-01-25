package us.ilite.shuffleboard.plugins.data;

import edu.wpi.first.shuffleboard.api.components.NumberField;
import edu.wpi.first.shuffleboard.api.data.IncompatibleSourceException;
import edu.wpi.first.shuffleboard.api.sources.DataSource;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.fxmisc.easybind.EasyBind;
import us.ilite.shuffleboard.plugins.ChrisWidget;

//THIS IS REQUIRED OR IT WILL NOT WORK!!
@Description(name = "Example View", dataTypes = {String.class, Number.class, Boolean.class}, summary = "Just a test")

public class ExampleView extends ChrisWidget<Object> {

    public ExampleView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinWidth(64);
        borderPane.setMinHeight(32);

        textField = new TextField();
        numberField = new NumberField();
        borderPane.setCenter(textField);
        borderPane.setBottom(numberField);

        this.root = borderPane;

        dataProperty().addListener((__, prev, cur) -> {
            if (cur != null) {
                if (cur instanceof Number) {
                    numberField.setNumber(((Number) cur).doubleValue());
                } else if (cur instanceof String || cur instanceof Boolean) {
                    textField.setText(cur.toString());
                } else {
                    throw new UnsupportedOperationException("Unsupported type: " + cur.getClass().getName());
                }
            }
        });
//        numberField.visibleProperty().bind(EasyBind.map(dataProperty(), d -> d instanceof Number).orElse(false));
//        textField.visibleProperty().bind(numberField.visibleProperty().not());

        textField.textProperty().addListener((__, oldText, newText) -> {
            if (getData() instanceof Boolean) {
                // TODO maybe disable boolean text entry entirely? No point in typing "true" or "false" every time
                // Especially since checkboxes and toggle buttons exist
                setData(Boolean.valueOf(newText));
            } else {
                setData(newText);
            }
        });
        numberField.numberProperty().addListener((__, oldNumber, newNumber) -> setData(newNumber));
    }

    @Override
    public void addSource(DataSource source) throws IncompatibleSourceException {
        super.addSource(source);
        textField.setText(source.toString());
        Thread.dumpStack();
    }

    private Pane root;
    private TextField textField;
    private NumberField numberField;

    @Override
    public Pane getView() {
        return root;
    }

}
