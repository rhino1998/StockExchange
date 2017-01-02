package com.stockexchange.client.ui.components.chart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.stockexchange.stocks.StockDataPoint;

import javafx.geometry.Insets;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;

public class StockPriceChart extends LineChart<Number, Number>{

	private XYChart.Series<Number,Number> bid;
	private XYChart.Series<Number,Number> ask;
	
	private long start;
	private long end;
	
	
	
	public StockPriceChart(
			String exchange,
			String symbol,
			List<StockDataPoint> data
		){
		super(xAxis("Time"),yAxis("Dollars"));
		
		bid = new XYChart.Series<Number, Number>();
		ask = new XYChart.Series<Number, Number>();
		start = data.get(0).getTime();
		end = data.get(data.size()-1).getTime();
		
		for (StockDataPoint datum : data){
			ask.getData().add(new Data<Number, Number>(datum.getTime(), datum.getAsk()));
			bid.getData().add(new Data<Number, Number>(datum.getTime(), datum.getBid()));
		}
		
		this.setCreateSymbols(false);
		this.getData().add(ask);
		this.getData().add(bid);
		this.setLegendVisible(false);
		((NumberAxis) this.getXAxis()).setTickLabelFormatter(new StringConverter<Number>(){

			@Override
			public Number fromString(String arg0) {
				return 0;
			}

			@Override
			public String toString(Number num) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				return String.format("%s",
						dateFormat.format(new Date((long) (num.longValue()/1e6)))
				);
			}
        	
        });
	}

	public long getEnd(){
		return end;
	}
	
	public void update(List<StockDataPoint> data){
		if (data.size()==0){
			return;
		}
		int j = 0;
		end = data.get(data.size()-1).getTime();
		
		for (int i = data.size()-1; i>0; i--){
			StockDataPoint datum = data.get(i);
			long time = (long) ((datum.getTime()-start)/1e9);
			if (ask.getData().get(ask.getData().size()-1).equals(time)){
				break;
			}
			j++;
		}
		
		for (int i = data.size()-1-j; i<data.size(); i++){
			StockDataPoint datum = data.get(i);
			ask.getData().add(new Data<Number, Number>(datum.getTime(), datum.getAsk()));
			bid.getData().add(new Data<Number, Number>(datum.getTime(), datum.getBid()));
		}
		if (ask.getData().size()>720){
			ask.getData().remove(0, j-1);
			bid.getData().remove(0, j-1);
		}
	}
	
	private static  NumberAxis xAxis(String label){
		NumberAxis x = new NumberAxis();
		x.setLabel(label);
        x.setForceZeroInRange(false);
		return x;
	}
	
	private static NumberAxis yAxis(String label){
		NumberAxis y = new NumberAxis();
		y.setLabel(label);
		y.setLowerBound(0.0);
		y.setForceZeroInRange(false);
		return y;
	}
	
	
}
