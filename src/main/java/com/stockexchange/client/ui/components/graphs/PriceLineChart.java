package com.stockexchange.client.ui.components.graphs;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class PriceLineChart extends LineChart<Number, Number> {
    /**
     * Creates a new PriceLineChart object.
     */
    public PriceLineChart() {
        super(new NumberAxis(), new NumberAxis());
    }
}
