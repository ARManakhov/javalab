package ru.sirosh;

public class StateHolder {
    private State state;

    public StateHolder(State state) {
        this.state = state;
    }

    public synchronized State getState() {
        return state;
    }

    public synchronized void setState(State state) {
        this.state = state;
    }
}
