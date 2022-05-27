package es.hulk.hub.util.menu.callback;

import java.io.Serializable;

public interface TypeCallback<T> extends Serializable {
    void callback(final T p0);
}
