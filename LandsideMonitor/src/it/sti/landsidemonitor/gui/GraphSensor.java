package it.sti.landsidemonitor.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.dto.SensorDTO;

public class GraphSensor extends JFrame {

	static int x = 0;
	SensorDTO sonda;
	RasterPanel mainP;
	Thread thread;
	boolean exit;
	public GraphSensor(SensorDTO _sonda, RasterPanel _mainP) 
	{
		mainP=_mainP;
		sonda=_sonda;
		
		setTitle("Sensor Graph GUI");
		setSize(800, 400);
		setLayout(new BorderLayout());
		
		exit = false;
		JPanel topPanel = new JPanel();
	
		add(topPanel, BorderLayout.NORTH);

		// create the line graph
		XYSeries seriesX = new XYSeries("Asse X");
		XYSeries seriesY = new XYSeries("Asse Y");
		XYSeries seriesZ = new XYSeries("Asse Z");
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		dataset.addSeries(seriesX);
		dataset.addSeries(seriesY);
		dataset.addSeries(seriesZ);
		
		JFreeChart chart = ChartFactory.createXYLineChart("Grafico Variazione Accelerazione Sonda "+sonda.getIdentifier(), "Time", "m/s²", dataset);
		
		XYPlot plot = (XYPlot) chart.getPlot();
		ValueAxis range = plot.getDomainAxis();
		range.setVisible(false);
		
		add(new ChartPanel(chart), BorderLayout.CENTER);
		
					 thread = new Thread(){
						@Override public void run() {
							 while (!exit){
								try {
									Thread.sleep(50);
									String value=MainFrame.pr.getValue();

									if(value.split(",").length==4) 
										{
											String pivot=value.split(",")[0].substring(1,2);
		
											if(pivot.equals(sonda.getIdentifier()))
													{
										
													double numberX = Double.parseDouble(value.split(",")[1].trim());
													double numberY = Double.parseDouble(value.split(",")[2].trim());
													double numberZ = Double.parseDouble(value.split(",")[3].substring(0,value.split(",")[3].length()-1).trim());
													
													if(numberX >= 0.2  || numberX <= -0.2 )
													{
														seriesX.add(x++,numberX);
													}
													else 
													{
														seriesX.add(x++,0);
													}
													
													if(numberY >= 0.2 || numberY <= -0.2 )
													{
														seriesY.add(x++,numberY);
													}else 
													{
														seriesY.add(x++,0);
													}
													
													if(numberZ >= 0.2 || numberZ <= -0.2 )
													{
														seriesZ.add(x++,numberZ);
													}else 
													{
														seriesZ.add(x++,0);
													}

													
													if(seriesX.getItemCount()>=50) 
													{
														seriesX.remove(0);
														seriesY.remove(0);
														seriesZ.remove(0);
													}
													
													repaint();
													}
									
									}
								
							
							}catch(NumberFormatException e) {
								System.err.println("Valore Mancante sonda "+sonda.getIdentifier());
							}
								catch (IndexOutOfBoundsException e) {
								e.printStackTrace();
							}
								catch (Exception e) {
								e.printStackTrace();
							}
							}
						
						}
					};
					thread.start();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
			    
				exit=true;
			    }
		});
	}
}
