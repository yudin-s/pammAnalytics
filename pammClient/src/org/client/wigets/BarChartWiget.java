package org.client.wigets;

import java.util.Map.Entry;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.IBarSeries;
import org.swtchart.ILegend;
import org.swtchart.ILineSeries;
import org.swtchart.ISeries.SeriesType;
import org.swtchart.ITitle;

public class BarChartWiget extends Composite {

	private IAxis xAxis;
	private IAxis yAxis;
	private Chart chart;

	public BarChartWiget(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		chart = new Chart(this, SWT.BORDER);
		xAxis = chart.getAxisSet().getXAxis(0);
		yAxis = chart.getAxisSet().getYAxis(0);
		ILegend legend = chart.getLegend();
		legend.setVisible(false);
		ITitle graphTitle = chart.getTitle();
		ITitle xAxisTitle = xAxis.getTitle();
		ITitle yAxisTitle = yAxis.getTitle();
		graphTitle.setVisible(false);
		xAxisTitle.setVisible(false);
		yAxisTitle.setVisible(false);
	}

	public void setData(String categoryFields[], double data[]) {
		xAxis.setCategorySeries(categoryFields);
		xAxis.enableCategory(true);
		IBarSeries series = (IBarSeries) chart.getSeriesSet().createSeries(
				SeriesType.BAR, "series");
		series.setBarColor(this.getDisplay().getSystemColor(SWT.COLOR_RED));
		series.setYSeries(data);
		ILineSeries line = (ILineSeries) chart.getSeriesSet().createSeries(
				SeriesType.LINE, "line");
		line.setLineColor(this.getDisplay().getSystemColor(SWT.COLOR_BLUE));
		line.setYSeries(data);
		agustRange();
	}

	public void setData(Properties data) {
		int size = data.entrySet().size();
		double value[] = new double[size];
		String key[] = new String[size];
		int i = 0;
		for (Entry<Object, Object> k : data.entrySet()) {
			value[i] = parseDouble((String) k.getValue());
			key[i] = (String) k.getKey();
			i++;
		}
		setData(key, value);
	}

	public void agustRange() {
		chart.getAxisSet().adjustRange();
	}

	public Chart getChart() {
		return chart;
	}

	private double parseDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (Throwable t) {
			return 0.0;
		}
	}
}
