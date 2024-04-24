package uk.ac.sheffield.com1003.assignment.gui;

import uk.ac.sheffield.com1003.assignment.codeprovided.AbstractPlayerCatalog;
import uk.ac.sheffield.com1003.assignment.codeprovided.League;
import uk.ac.sheffield.com1003.assignment.codeprovided.PlayerEntry;
import uk.ac.sheffield.com1003.assignment.codeprovided.PlayerProperty;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractRadarChart;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.RadarAxisValues;

import java.util.*;

/**
 * Abstract class designed to be extended.
 * Provides basic reading functionalities of datasets with player entries and
 * queries.
 *
 * @version 1.1 5/5/2023
 *
 *          Copyright (c) University of Sheffield 2023
 */

public class RadarChart extends AbstractRadarChart {
    // Constructor
    public RadarChart(AbstractPlayerCatalog playerCatalog, List<PlayerEntry> filteredPlayerEntriesList,
            List<PlayerProperty> playerRadarChartProperties) {
        super(playerCatalog, filteredPlayerEntriesList, playerRadarChartProperties);
    }

    /**
     * this method updates the radar chart attributes depending on the selected
     * category and current filters applied, it creates new radar axis values
     * which tell the radar chart panel where to place each point
     */
    @Override
    public void updateRadarChartContents(List<PlayerProperty> radarChartPlayerProperties,
            List<PlayerEntry> filteredPlayerEntriesList) {
        this.playerRadarChartProperties = radarChartPlayerProperties;
        this.filteredPlayerEntries = filteredPlayerEntriesList;
        // removes all the axes values from the current chart
        radarAxesValues.clear();
        // ensures no error occurs if the player list is empty
        if (filteredPlayerEntriesList.size() != 0 && filteredPlayerEntriesList != null) {
            /*
             * for each property in the category calculates the max,min and avg values from
             * the filtered list
             * and the max value from the list of all the players in the dataset
             */
            for (PlayerProperty property : radarChartPlayerProperties) {
                Double maxValue = playerCatalog.getMaximumValue(property, filteredPlayerEntriesList);
                Double minValue = playerCatalog.getMinimumValue(property, filteredPlayerEntriesList);
                Double avgValue = playerCatalog.getMeanAverageValue(property, filteredPlayerEntriesList);

                Double totalMaxValue = playerCatalog.getMaximumValue(property,
                        playerCatalog.getPlayerEntriesList(League.ALL));

                // works out the fraction of the value from the max so the chart knows where to
                // draw the line
                maxValue = maxValue / totalMaxValue;
                minValue = minValue / totalMaxValue;
                avgValue = avgValue / totalMaxValue;
                // creates an object with these values
                RadarAxisValues values = new RadarAxisValues(minValue, maxValue, avgValue);
                // adds these values to the radar chart
                radarAxesValues.put(property, values);
            }
        }
    }

    @Override
    public List<PlayerProperty> getPlayerRadarChartProperties() throws NoSuchElementException {
        return playerRadarChartProperties;
    }

    @Override
    public Map<PlayerProperty, RadarAxisValues> getRadarPlotAxesValues() throws NoSuchElementException {
        return radarAxesValues;
    }

    @Override
    public AbstractPlayerCatalog getPlayerCatalog() {
        return playerCatalog;
    }

    @Override
    public List<PlayerEntry> getFilteredPlayerEntries() {
        return filteredPlayerEntries;
    }

}
