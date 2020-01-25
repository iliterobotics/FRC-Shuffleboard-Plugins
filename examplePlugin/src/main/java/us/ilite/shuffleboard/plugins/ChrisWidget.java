package us.ilite.shuffleboard.plugins;

import edu.wpi.first.shuffleboard.api.data.IncompatibleSourceException;
import edu.wpi.first.shuffleboard.api.sources.DataSource;
import edu.wpi.first.shuffleboard.api.widget.AbstractWidget;
import edu.wpi.first.shuffleboard.api.widget.AnnotatedWidget;
import edu.wpi.first.shuffleboard.api.widget.SingleTypeWidget;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import org.fxmisc.easybind.EasyBind;
import org.fxmisc.easybind.monadic.MonadicBinding;
import org.fxmisc.easybind.monadic.PropertyBinding;

public abstract class ChrisWidget<T> extends AbstractWidget implements AnnotatedWidget, SingleTypeWidget<T> {
    protected final ObjectProperty<DataSource> source = new SimpleObjectProperty<>(this, "source", DataSource.none());

    @Override
    public void addSource(DataSource source) throws IncompatibleSourceException {
        if (getDataTypes().contains(source.getDataType())) {
            this.source.set(source);
            this.sources.remove(getSource());
            this.sources.setAll(source);
            source.addClient(this);
        } else {
            throw new IncompatibleSourceException(getDataTypes(), source.getDataType());
        }
    }

    public final Property<DataSource> sourceProperty() {
        return source;
    }

    public final DataSource getSource() {
        return source.get();
    }

    public void setSource(DataSource source) throws IncompatibleSourceException {
        addSource(source);
    }

    /**
     * The property for this widgets data. This is the preferred way to get the current value of the
     * data source because it will update whenever the source is modified.
     */
    private final PropertyBinding<T> data
            = EasyBind.monadic(source).selectProperty(DataSource::dataProperty);

    /**
     * A read-only binding of the data for this widget. If this widget has a source, this is equivalent to
     * {@link #dataProperty()}; otherwise, it contains the default value of this widgets data type.
     */
    protected final MonadicBinding<T> dataOrDefault = data.orElse(getDataType().getDefaultValue());

    @Override
    public final PropertyBinding<T> dataProperty() {
        return data;
    }

    public final Property<DataSource<T>> typedSourceProperty() {
        return (Property) sourceProperty();
    }
}
