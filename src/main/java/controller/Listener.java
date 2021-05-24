package controller;

public interface Listener<V> {
    void notifyChange(V observed);
}
