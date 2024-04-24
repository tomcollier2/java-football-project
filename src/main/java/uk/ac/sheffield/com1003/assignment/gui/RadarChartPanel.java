package uk.ac.sheffield.com1003.assignment.gui;

import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractRadarChart;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractRadarChartPanel;
import uk.ac.sheffield.com1003.assignment.codeprovided.PlayerProperty;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractPlayerDashboardPanel;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JLabel;

/**
 * this method is in-charge of redrawing the radar chart, it plots the layout of
 * the radar chart
 * and then plots the graph lines depending on the values supplied
 */

public class RadarChartPanel extends AbstractRadarChartPanel {
    // Constructor
    public RadarChartPanel(AbstractPlayerDashboardPanel parentPanel, AbstractRadarChart radarPlot) {
        super(parentPanel, radarPlot);
    }

    /**
     * this function is in charge of redrawing the radarchart
     */
    @Override
    protected void paintComponent(Graphics g) {
        // removes all current drawings on the radarchart
        this.removeAll();

        super.paintComponent(g);
        Dimension d = getSize();
        Graphics2D g2 = (Graphics2D) g;
        /*
         * creates 10 polygons that will create the base of the chart,
         * each polygon is marginally larger than the prior and they are all added to an
         * array list
         */
        ArrayList<Polygon> radarChart = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            Polygon p = new Polygon();
            for (int i = 0; i < 5; i++) {
                p.addPoint((int) (d.getWidth() / 2 + (j * 30) * Math.cos(i * 2 * Math.PI / 5)),
                        (int) (d.getHeight() / 2 + (j * 30) * Math.sin(i * 2 * Math.PI / 5)));
            }
            radarChart.add(p);
        }
        // each polygon is drawn
        for (Polygon p : radarChart) {
            g2.draw(p);
        }
        // check if there are values to avoid errors being thrown
        if (this.getRadarChart().getRadarPlotAxesValues().size() != 0) {
            // increases the width of the lines
            g2.setStroke(new BasicStroke(3));
            // creates three polygons for each checkbox
            Polygon maxRadarPolygon = new Polygon();
            Polygon minRadarPolygon = new Polygon();
            Polygon avgRadarPolygon = new Polygon();

            int i = 0;
            // for each property
            for (PlayerProperty property : this.getRadarChart().getPlayerRadarChartProperties()) {
                // finds the coordinates the poin needs to be at
                int x = (int) ((270 * Math.cos(i * 2 * Math.PI / 5)));
                int y = (int) ((270 * Math.sin(i * 2 * Math.PI / 5)));
                int xCentre = (int) (d.getWidth() / 2);
                int yCentre = (int) (d.getHeight() / 2);

                // draws the appropriate label in the corner of the chart
                JLabel label = new JLabel(property.getName());
                Dimension size = label.getPreferredSize();
                if (i == 2 || i == 3) {
                    label.setBounds(xCentre + (x - size.width), yCentre + y, size.width, size.height);
                } else if (i == 4) {
                    label.setBounds(xCentre + x, yCentre + (y - size.height), size.width, size.height);
                } else {
                    label.setBounds(xCentre + x, yCentre + y, size.width, size.height);
                }
                this.add(label);

                // if the max check box is selected will draw the polygon that shows the max
                // values
                if (getParentPanel().isMaxCheckBoxSelected()) {
                    Double max = this.getRadarChart().getRadarPlotAxesValues().get(property).getMax();
                    maxRadarPolygon.addPoint((int) (xCentre + (x * max)), (int) (yCentre + (y * max)));
                }

                // if the avg check box is selected will draw the polygon that shows the avg
                // values
                if (getParentPanel().isAverageCheckBoxSelected()) {
                    Double avg = this.getRadarChart().getRadarPlotAxesValues().get(property).getAverage();
                    avgRadarPolygon.addPoint((int) (xCentre + (x * avg)), (int) (yCentre + (y * avg)));
                }

                // if the min check box is selected will draw the polygon that shows the min
                // values
                if (getParentPanel().isMinCheckBoxSelected()) {
                    Double min = this.getRadarChart().getRadarPlotAxesValues().get(property).getMin();
                    minRadarPolygon.addPoint((int) (xCentre + (x * min)), (int) (yCentre + (y * min)));
                }

                i++;

            }
            // sets each polygon to a different color befor drawing
            g2.setColor(new Color(255, 0, 0));
            g2.draw(maxRadarPolygon);
            g2.setColor(new Color(0, 255, 0));
            g2.draw(avgRadarPolygon);
            g2.setColor(new Color(0, 0, 255));
            g2.draw(minRadarPolygon);

            this.revalidate();
            this.repaint();
        }
    }
}
