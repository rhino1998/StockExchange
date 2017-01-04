package com.stockexchange.client.ui;

import javafx.stage.Stage;

public class ViewStage {

    private final Stage stage;
    private View view;

    public ViewStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setView(View view) {
        double oldWidth = stage.getWidth();
        double oldHeight = stage.getHeight();

        if (this.view != null) {
            this.view.stop();
        }

        stage.setScene(view.getScene());

        this.view = view;
        this.view.start();

        stage.sizeToScene();
        stage.setX(stage.getX() - (stage.getWidth() - oldWidth) / 2);
        stage.setY(stage.getY() - (stage.getHeight() - oldHeight) / 2);
    }

    public void show() {
        this.stage.show();
    }

    public void hide() {
        this.stage.hide();
    }

    public double getWidth() {
        return stage.getWidth();
    }

    public void setTitle(String title) {
        stage.setTitle(title);
    }

    public double getHeight() {
        return stage.getHeight();
    }

    public void setX(double x) {
        stage.setX(x);
    }

    public void setY(double y) {
        stage.setY(y);
    }

    public double getX() {
        return stage.getX();
    }

    public double getY() {
        return stage.getY();
    }
}
