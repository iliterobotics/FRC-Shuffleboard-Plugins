package us.ilite.shuffleboard.plugins;

import edu.wpi.first.shuffleboard.api.data.IncompatibleSourceException;
import edu.wpi.first.shuffleboard.api.sources.DataSource;
import edu.wpi.first.shuffleboard.api.widget.AbstractWidget;
import edu.wpi.first.shuffleboard.api.widget.AnnotatedWidget;

public abstract class ChrisWidget<T> extends AbstractWidget implements AnnotatedWidget {

    @Override
    public void addSource(DataSource source) throws IncompatibleSourceException {
        if (getDataTypes().contains(source.getDataType())) {
            this.sources.setAll(source);
            source.addClient(this);
        } else {
            throw new IncompatibleSourceException(getDataTypes(), source.getDataType());
        }
    }

}
