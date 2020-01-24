package us.ilite.shuffleboard.plugins.data;

import edu.wpi.first.shuffleboard.api.data.IncompatibleSourceException;
import edu.wpi.first.shuffleboard.api.sources.DataSource;
import edu.wpi.first.shuffleboard.api.widget.AbstractWidget;
import edu.wpi.first.shuffleboard.api.widget.Description;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.logging.LogManager;
import java.util.logging.Logger;

//THIS IS REQUIRED OR IT WILL NOT WORK!!
@Description(name = "Example View", dataTypes = ExampleDataPoint.class, summary = "Just a test")
public class ExampleView extends AbstractWidget {


    private Text title;
    private final Logger myLog;

    public ExampleView() {
        myLog =
                LogManager.getLogManager().getLogger("");
        myLog.config("I'm just a singer in a rock and roll band!");
    }

    @Override
    public void addSource(DataSource source) throws IncompatibleSourceException {
        super.addSource(source);

        if(title != null) {
            title.setText(source.getName() +": " + source.getClass());
        }
    }

    @Override
    public Pane getView() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        title = new Text("Data for me");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Hyperlink options[] = new Hyperlink[] {
                new Hyperlink("Sales"),
                new Hyperlink("Marketing"),
                new Hyperlink("Distribution"),
                new Hyperlink("Costs")};

        for (int i=0; i<4; i++) {
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
            vbox.getChildren().add(options[i]);
        }

        return vbox;
    }

    @Override
    public String getName() {
        return "Example View";
    }

}
