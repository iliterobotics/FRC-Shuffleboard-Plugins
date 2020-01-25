package us.ilite.shuffleboard.plugins.data;

import edu.wpi.first.shuffleboard.api.components.NumberField;
import edu.wpi.first.shuffleboard.api.data.IncompatibleSourceException;
import edu.wpi.first.shuffleboard.api.data.SimpleDataType;
import edu.wpi.first.shuffleboard.api.sources.DataSource;
import edu.wpi.first.shuffleboard.api.widget.Description;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.fxmisc.easybind.EasyBind;
import org.fxmisc.easybind.monadic.PropertyBinding;
import org.w3c.dom.Text;
import us.ilite.shuffleboard.plugins.ChrisWidget;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

//THIS IS REQUIRED OR IT WILL NOT WORK!!
@Description(name = "Example View", dataTypes = {String.class, Number.class, Boolean.class}, summary = "Just a test")

public class ExampleView extends ChrisWidget<Object> {
    private Pane root;

    private enum COLOR_CHANNEL {
        RED {
            @Override
            public void layoutToPane(BorderPane pane, TextField tf) {
                pane.setTop(tf);
            }
        },

        GREEN{
            @Override
            public void layoutToPane(BorderPane pane, TextField tf) {
                pane.setCenter(tf);
            }
        },
        BLUE{
            @Override
            public void layoutToPane(BorderPane pane, TextField tf) {
                pane.setBottom(tf);
            }
        };

        public static COLOR_CHANNEL getNext(COLOR_CHANNEL current) {
            switch(current) {
                case RED: return GREEN;
                case GREEN: return BLUE;
            }
            return null;
        }

        public abstract void layoutToPane(BorderPane pane, TextField tf);

    }

    private COLOR_CHANNEL nextChannel = COLOR_CHANNEL.RED;

    private Map<COLOR_CHANNEL, TextField> colorFields;
    private Map<COLOR_CHANNEL, ObjectProperty<DataSource>> dataSources = new EnumMap<COLOR_CHANNEL, ObjectProperty<DataSource>>(COLOR_CHANNEL.class);



//    private final ObjectProperty<DataSource> redSource = new SimpleObjectProperty<>(this, "redSource", DataSource.none());
//    private final ObjectProperty<DataSource> greenSource = new SimpleObjectProperty<>(this, "greenSource", DataSource.none());
    private final AtomicInteger EVENT_COUNTER = new AtomicInteger(0);
    public ExampleView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinWidth(64);
        borderPane.setMinHeight(32);

        colorFields = Arrays.stream(COLOR_CHANNEL.values()).collect(Collectors.toMap(Function.identity(), aChan->new TextField(aChan.toString()+": XXX")));
        colorFields.entrySet().stream().forEach(anEntry->{
            anEntry.getKey().layoutToPane(borderPane, anEntry.getValue());
            SimpleObjectProperty<DataSource> simpleObjectProperty = new SimpleObjectProperty<>();
            dataSources.put(anEntry.getKey(), simpleObjectProperty);
        });

        this.root = borderPane;

    }

    @Override
    public void addSource(DataSource source) throws IncompatibleSourceException {
        final COLOR_CHANNEL currentChannel = this.nextChannel;
        ObjectProperty<DataSource>current = dataSources.get(currentChannel);
        current.setValue(source);

        PropertyBinding<Object> data
                = EasyBind.monadic(current).selectProperty(DataSource::dataProperty);

        this.nextChannel = COLOR_CHANNEL.getNext(currentChannel);

        data.addListener((__, prev, cur)->{
            int eventCounter = EVENT_COUNTER.getAndIncrement();
            if(cur != null) {
                TextField value = colorFields.get(currentChannel);
                String prefix = eventCounter +". " +  currentChannel;

                if(cur instanceof DataSource) {

                    DataSource currentSource = (DataSource)cur;
                    Object currData = currentSource.getData();
                    if(currData instanceof Number) {
                        value.setText(prefix + " " + currData.toString());
                    } else {
                        value.setText(prefix + " " + currData.getClass().getName());
                    }


                } else {
                    value.setText(prefix +" " + cur.getClass());
                }
            }
        });
    }


    @Override
    public Pane getView() {
        return root;
    }

}
