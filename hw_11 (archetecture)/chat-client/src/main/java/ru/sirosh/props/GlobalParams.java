package ru.sirosh.props;

public class GlobalParams {
    private static GlobalParams instance;
    public synchronized static GlobalParams getInstance() {
        return instance;
    }
    private boolean isListener;

    public synchronized boolean isListener() {
        return isListener;
    }

    public synchronized void setListener(boolean listener) {
        isListener = listener;
    }
}
