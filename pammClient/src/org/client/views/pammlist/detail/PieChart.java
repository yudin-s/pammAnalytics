package org.client.views.pammlist.detail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

public class PieChart extends Composite {
	private static final String SHIFT_0 = ")";
	private static final String SHIFT_9 = "(";
	ChartComposite chartComposite;

	public PieChart(Composite comp, int style) {
		super(comp, style);
		// TODO Auto-generated constructor stub
	}

	public class ValueCanNotBeNegative extends Exception {
		private static final long serialVersionUID = 7762456305824207347L;
	}

	public void drawByData(double... data) throws ValueCanNotBeNegative {
		double percent[] = new double[data.length];
		double summ = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i] >= 0)
				summ += data[i];
			else
				throw new ValueCanNotBeNegative();
		}
		for (int i = 0; i < data.length; i++) {
			percent[i] = data[i] / summ;
		}

		DefaultPieDataset dataset = genDataset(data, percent);
		JFreeChart chart = ChartFactory.createPieChart3D("", dataset, false,
				true, false);
		
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setOutlineVisible(false);
		plot.setLabelLinksVisible(false);
		plot.setStartAngle(290);
		if (chartComposite == null)
			chartComposite = new ChartComposite(this, SWT.BORDER, chart, true);
		else
			chartComposite.setChart(chart);
		chartComposite.setSize(70, 70);
		chartComposite.setChart(chart);
	}

	private DefaultPieDataset genDataset(double data[], double percents[]) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (int i = 0; i < percents.length; i++) {
			result.setValue(Double.toString(data[i])+SHIFT_9+i+1+SHIFT_0, percents[i]);
		}
		return result;
	}

}
