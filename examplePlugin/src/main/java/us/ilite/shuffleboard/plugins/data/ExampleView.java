package us.ilite.shuffleboard.plugins.data;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

//THIS IS REQUIRED OR IT WILL NOT WORK!!
@Description(name = "Example View", dataTypes = ExampleDataPoint.class, summary = "Just a test")
public class ExampleView extends SimpleAnnotatedWidget<ExampleDataPoint> {


    ExampleView() {

    }


    @Override
    public Pane getView() {
        ChangeListener<ExampleDataPoint>alistener = new ChangeListener<ExampleDataPoint>() {
            @Override
            public void changed(ObservableValue<? extends ExampleDataPoint> observable, ExampleDataPoint oldValue, ExampleDataPoint newValue) {
                //perform the updates to the UI when the data changes. Need to make sure the GUI component exists
            }
        };

        dataOrDefault.addListener((obs, old, newVal)->{
                //update the GUI when the value changes
        });

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("Data");
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

}
