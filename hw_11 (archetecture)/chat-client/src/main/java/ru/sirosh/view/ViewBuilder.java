package ru.sirosh.view;

public final class ViewBuilder {
    private StateHolder currentState;

    private ViewBuilder() {
    }

    public static ViewBuilder aView() {
        return new ViewBuilder();
    }

    public ViewBuilder withCurrentState(StateHolder currentState) {
        this.currentState = currentState;
        return this;
    }

    public View build() {
        return new View(currentState);
    }
}
