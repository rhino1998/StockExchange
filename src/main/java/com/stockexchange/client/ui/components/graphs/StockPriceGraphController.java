package com.stockexchange.client.ui.components.graphs;

import com.stockexchange.stocks.StockDataPoint;

import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

import javafx.util.StringConverter;

public class StockPriceGraphController implements Initializable {
    @FXML
    private PriceLineChart chart;
    private Series<Number, Number> ask = new XYChart.Series<Number, Number>();
    private Series<Number, Number> bid = new XYChart.Series<Number, Number>();
    private long start;
    private long end;

    /**
     * DOCUMENT ME!
     *
     * @param url DOCUMENT ME!
     * @param rb DOCUMENT ME!
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NumberAxis xAxis = (NumberAxis)chart.getXAxis();
        NumberAxis yAxis = (NumberAxis)chart.getYAxis();

        xAxis.setLabel("Time");
        xAxis.setForceZeroInRange(false);

        yAxis.setLabel("Price");
        yAxis.setLowerBound(0.0);
        yAxis.setForceZeroInRange(false);


        chart.setCreateSymbols(false);
        chart.getData().add(ask);
        chart.getData().add(bid);
        chart.setLegendVisible(false);


        ((NumberAxis)chart.getXAxis()).setTickLabelFormatter(new
        StringConverter<Number>() {
            @Override
            public Number fromString(String arg0) {
                return 0;
            }

            @Override
            public String toString(Number num) {
                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("HH:mm:ss");

                return String.format("%s",
                                     dateFormat.format(
                                         new Date((long)(num.longValue() / 1e6))));
            }
        });
    }

    /**
     * DOCUMENT ME!
     *
     * @param data DOCUMENT ME!
     */
    public void update(List<StockDataPoint> data) {
        if (ask.getData().size() == 0) {
            for (StockDataPoint datum : data) {
                ask.getData()
                .add(new Data<Number, Number>(datum.getTime(),
                                              datum.getAsk()));
                bid.getData()
                .add(new Data<Number, Number>(datum.getTime(),
                                              datum.getBid()));
            }
        }

        if (data.size() == 0) {
            return;
        }

        int j = 0;
        end = data.get(data.size() - 1).getTime();

        for (int i = data.size() - 1; i > 0; i--) {
            StockDataPoint datum = data.get(i);
            long time = (long)((datum.getTime() - start) / 1e9);

            if (ask.getData().get(ask.getData().size() - 1).equals(time)) {
                break;
            }

            j++;
        }

        for (int i = data.size() - 1 - j; i < data.size(); i++) {
            StockDataPoint datum = data.get(i);
            ask.getData()
            .add(new Data<Number, Number>(datum.getTime(), datum.getAsk()));
            bid.getData()
            .add(new Data<Number, Number>(datum.getTime(), datum.getBid()));
        }

        if (ask.getData().size() > 720) {
            ask.getData().remove(0, j - 1);
            bid.getData().remove(0, j - 1);
        }
    }
}
