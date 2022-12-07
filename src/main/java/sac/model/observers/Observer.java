package sac.model.observers;

/**
 * A class to implement the observer pattern.
 * @param <T> The type of event.
 */
public interface Observer<T> {
    void update(T obj);
}
