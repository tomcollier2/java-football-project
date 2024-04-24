package uk.ac.sheffield.com1003.assignment.gui;

import uk.ac.sheffield.com1003.assignment.codeprovided.*;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractPlayerDashboardPanel;

import java.util.*;

import javax.swing.*;

/**
 * Abstract class designed to be extended.
 * Provides basic reading functionalities of datasets with player entries and
 * queries.
 *
 * @version 1.1 5/5/2023
 *
 *          Copyright (c) University of Sheffield 2023
 */

public class PlayerDashboardPanel extends AbstractPlayerDashboardPanel {

    // Constructor
    public PlayerDashboardPanel(AbstractPlayerCatalog playerCatalog) {
        super(playerCatalog);
    }

    /**
     * sets the values of the combo boxes to contain
     * the values in the dataset
     */
    @Override
    public void populatePlayerDetailsComboBoxes() {
        // adds a default empty value at the top
        playerNamesList.add("");
        nationList.add("");
        positionList.add("");
        teamList.add("");

        for (PlayerEntry p : filteredPlayerEntriesList) {
            // ensures no duplicate values are entered
            if (!playerNamesList.contains(p.getPlayerName())) {
                playerNamesList.add(p.getPlayerName());
            }
            if (!nationList.contains(p.getNation())) {
                nationList.add(p.getNation());
            }
            if (!positionList.contains(p.getPosition())) {
                positionList.add(p.getPosition());
            }
            if (!teamList.contains(p.getTeam())) {
                teamList.add(p.getTeam());
            }
        }

        // sorts the relevant list before appending the lists to the comboboxes
        DefaultComboBoxModel<String> playerNamesModel = (DefaultComboBoxModel<String>) comboPlayerNames.getModel();
        playerNamesModel.addAll(playerNamesList);

        DefaultComboBoxModel<String> nationModel = (DefaultComboBoxModel<String>) comboNations.getModel();
        Collections.sort(nationList);
        nationModel.addAll(nationList);

        DefaultComboBoxModel<String> positionModel = (DefaultComboBoxModel<String>) comboPositions.getModel();
        Collections.sort(positionList);
        positionModel.addAll(positionList);

        DefaultComboBoxModel<String> teamModel = (DefaultComboBoxModel<String>) comboTeams.getModel();
        Collections.sort(teamList);
        teamModel.addAll(teamList);

        // calls the update functions so the interface is preloaded with user values
        updatePlayerCatalogDetailsBox();
        updateStatistics();
    }

    /**
     * addListeners method - adds relevant actionListeners to the GUI components
     */
    @SuppressWarnings("unused")
    @Override
    public void addListeners() {
        buttonAddFilter.addActionListener(e -> {
            addFilter();
        });
        buttonClearFilters.addActionListener(e -> {
            clearFilters();
        });
        comboPlayerNames.addActionListener(e -> {
            updateInterface();
        });
        comboLeagueTypes.addActionListener(e -> {
            updateInterface();
        });
        comboNations.addActionListener(e -> {
            updateInterface();
        });
        comboPositions.addActionListener(e -> {
            updateInterface();
        });
        comboTeams.addActionListener(e -> {
            updateInterface();
        });
        comboRadarChartCategories.addActionListener(e -> {
            updateRadarChart();
        });
    }

    /**
     * clearFilters method - clears all filters from the subQueryList ArrayList and
     * updates
     * the relevant GUI components
     */
    @Override
    public void clearFilters() {
        subQueryList.clear();
        subQueriesTextArea.setText("");
        updateInterface();
    }

    /**
     * updates the radar chart to show values based on the
     * current filters and category selected
     */
    @Override
    public void updateRadarChart() {
        // creates an arraylist full of properties from the currently selected category
        Category category = Category.getCategoryFromName(comboRadarChartCategories.getSelectedItem().toString());
        List<PlayerProperty> playerProperties = new ArrayList<>();
        PlayerProperty[] categoryList = category.getProperties();
        for (PlayerProperty p : categoryList) {
            playerProperties.add(p);
        }
        // updates radar chart with the properties and filtered player lists
        radarChart.updateRadarChartContents(playerProperties, filteredPlayerEntriesList);
    }

    /**
     * updateStats method - updates the table with statistics after any changes
     * which may
     * affect the JTable which holds the statistics
     */
    @Override
    public void updateStatistics() {
        // initially sets the statistics area to blank
        statisticsTextArea.setText("");
        // ensures no error is thrown if player list is empty
        if (filteredPlayerEntriesList.size() > 0) {
            // creates a header string with all of the property names
            String header = " ";
            header += addBlankSpace(header);
            header += "tackles";
            header += addBlankSpace(header);
            header += "crosses";
            header += addBlankSpace(header);
            header += "pkattemps";
            header += addBlankSpace(header);
            header += "redcards";
            header += addBlankSpace(header);
            header += "shotsOT";
            header += addBlankSpace(header);
            header += "pkwon";
            header += addBlankSpace(header);
            header += "shotstotal";
            header += addBlankSpace(header);
            header += "%aerialwon";
            header += addBlankSpace(header);
            header += "tackles";
            header += addBlankSpace(header);
            header += "foulsdrawn";
            header += addBlankSpace(header);
            header += "goals";
            header += addBlankSpace(header);
            header += "foulscommited";
            header += addBlankSpace(header);
            header += "minutes";
            header += addBlankSpace(header);
            header += "aerialswon";
            header += addBlankSpace(header);
            header += "owngoals";
            header += addBlankSpace(header);
            header += "aeriellost";
            header += addBlankSpace(header);
            header += "matches";
            header += addBlankSpace(header);
            header += "assists";
            header += addBlankSpace(header);
            header += "clearences";
            header += addBlankSpace(header);
            header += "yellowcards";
            header += addBlankSpace(header);
            header += "pkconceded";
            header += addBlankSpace(header);
            header += "passattempted";
            header += addBlankSpace(header);
            header += "age";
            header += addBlankSpace(header);
            header += "pkgoals";
            header += "\n";
            // appends header string to the statistics text area
            statisticsTextArea.append(header);

            /*
             * gets a string of playerentry to string so
             * the property names can easilly be accessed from the string
             */
            String playerEntry = filteredPlayerEntriesList.get(0).toString();
            playerEntry = playerEntry.substring(12, playerEntry.length() - 3);
            String[] playerProperties = playerEntry.split("(, )|(=)");

            // creates a string called row initially for the maximum values of the filtered
            // list
            String row = "Maximum";
            /*
             * only iterates through the values in the playerproperties array that contains
             * property names so that PlayerProperty.fromPropertyName can be called
             */
            for (int i = 4; i < playerProperties.length; i += 2) {
                row += addBlankSpace(row);
                row += playerCatalog.getMaximumValue(PlayerProperty.fromPropertyName(playerProperties[i]),
                        filteredPlayerEntriesList);
            }
            row += "\n";
            // appends the row to the statistics text area
            statisticsTextArea.append(row);

            row = "Mean";
            for (int i = 4; i < playerProperties.length; i += 2) {
                row += addBlankSpace(row);
                row += Math
                        .round(playerCatalog.getMeanAverageValue(PlayerProperty.fromPropertyName(playerProperties[i]),
                                filteredPlayerEntriesList) * 100.0)
                        / 100.0;
            }
            row += "\n";
            statisticsTextArea.append(row);

            row = "Minimum";
            for (int i = 4; i < playerProperties.length; i += 2) {
                row += addBlankSpace(row);
                row += playerCatalog.getMinimumValue(PlayerProperty.fromPropertyName(playerProperties[i]),
                        filteredPlayerEntriesList);
            }
            row += "\n";
            statisticsTextArea.append(row);
        }
    }

    /**
     * updatePlayerCatalogDetailsBox method - updates the list of players when
     * changes are made
     */
    @Override
    public void updatePlayerCatalogDetailsBox() {
        // initially sets the player details area to blank
        filteredPlayerEntriesTextArea.setText("");
        // ensures no error is thrown if player list is empty
        if (filteredPlayerEntriesList.size() > 0) {
            // creates a header string with all of the property names
            String header = "league";
            header += addBlankSpace(header);
            header += "id";
            header += addBlankSpace(header);
            header += "player name";
            header += addBlankSpace(header);
            header += "nation";
            header += addBlankSpace(header);
            header += "position";
            header += addBlankSpace(header);
            header += "team";
            header += addBlankSpace(header);
            header += "tackles";
            header += addBlankSpace(header);
            header += "crosses";
            header += addBlankSpace(header);
            header += "pkattemps";
            header += addBlankSpace(header);
            header += "redcards";
            header += addBlankSpace(header);
            header += "shotsOT";
            header += addBlankSpace(header);
            header += "pkwon";
            header += addBlankSpace(header);
            header += "shotstotal";
            header += addBlankSpace(header);
            header += "%aerialwon";
            header += addBlankSpace(header);
            header += "tackles";
            header += addBlankSpace(header);
            header += "foulsdrawn";
            header += addBlankSpace(header);
            header += "goals";
            header += addBlankSpace(header);
            header += "foulscommited";
            header += addBlankSpace(header);
            header += "minutes";
            header += addBlankSpace(header);
            header += "aerialswon";
            header += addBlankSpace(header);
            header += "owngoals";
            header += addBlankSpace(header);
            header += "aeriellost";
            header += addBlankSpace(header);
            header += "matches";
            header += addBlankSpace(header);
            header += "assists";
            header += addBlankSpace(header);
            header += "clearences";
            header += addBlankSpace(header);
            header += "yellowcards";
            header += addBlankSpace(header);
            header += "pkconceded";
            header += addBlankSpace(header);
            header += "passattempted";
            header += addBlankSpace(header);
            header += "age";
            header += addBlankSpace(header);
            header += "pkgoals";
            header += "\n";
            // appends header string to the player details text area
            filteredPlayerEntriesTextArea.append(header);

            // iterates through every available player in the list
            for (PlayerEntry p : filteredPlayerEntriesList) {
                /*
                 * turns the player entry into a string so the player details and player
                 * properties
                 * can easilly be accessed
                 */
                String playerEntry = p.toString();
                playerEntry = playerEntry.substring(12, playerEntry.length() - 3);
                String[] playerProperties = playerEntry.split("(, )|(=)");

                // formats the string row to first contain all the players details
                String row = p.getLeagueType().toString().toLowerCase();
                String split = " ";
                row += addBlankSpace(row);
                row += p.getId();
                row += addBlankSpace(row);
                row += p.getPlayerName().toLowerCase();
                row += addBlankSpace(row);
                row += p.getNation().toLowerCase();
                row += addBlankSpace(row);
                row += p.getPosition().toLowerCase();
                row += addBlankSpace(row);
                row += p.getTeam().toLowerCase();
                // then adds all the player properties to the row
                for (int i = 5; i < playerProperties.length; i += 2) {
                    row += addBlankSpace(row);
                    row += playerProperties[i].split(split)[0];
                    row += "            ";
                }
                row += "\n";
                // appends the row to the player entries text area
                filteredPlayerEntriesTextArea.append(row);
            }
        }
    }

    /**
     * addBlankSpace function- takes in the string row and will
     * add blank spaces to the string row until the length of the
     * string is a multiple of 45;
     * intended to improve formatting of the text areas
     * 
     * @param row
     * @return
     */
    public String addBlankSpace(String row) {
        String blankSpace = "";
        // repeats until the string length produces no remainder when divided by 45
        while (((row + blankSpace).length() % 45) != 0) {
            blankSpace += " ";
        }
        return blankSpace;
    }

    /**
     * executeQuery method - applies chosen query to the relevant list
     */
    @Override
    public void executeQuery() {
        // gets the selected league from the combobox
        League league = League.fromName(comboLeagueTypes.getSelectedItem().toString());
        // executes the current subqueries created by the user for the data set
        Query query = new Query(subQueryList, league);
        filteredPlayerEntriesList = query.executeQuery(playerCatalog);

        // gets the values selected in the comboboxes and assigns it to relevant
        // variables
        String name;
        String nation;
        String position;
        String team;

        if (comboPlayerNames.getSelectedItem() != null) {
            name = comboPlayerNames.getSelectedItem().toString();
        } else {
            name = "";
        }
        if (comboNations.getSelectedItem() != null) {
            nation = comboNations.getSelectedItem().toString();
        } else {
            nation = "";
        }
        if (comboPositions.getSelectedItem() != null) {
            position = comboPositions.getSelectedItem().toString();
        } else {
            position = "";
        }
        if (comboTeams.getSelectedItem() != null) {
            team = comboTeams.getSelectedItem().toString();
        } else {
            team = "";
        }
        // filters the data depending on the selected values in the comboboxes
        if (name != "") {
            filteredPlayerEntriesList = executeDetailQueries("name", name, filteredPlayerEntriesList);
        }
        if (nation != "") {
            filteredPlayerEntriesList = executeDetailQueries("nation", nation, filteredPlayerEntriesList);
        }
        if (position != "") {
            filteredPlayerEntriesList = executeDetailQueries("position", position, filteredPlayerEntriesList);
        }
        if (team != "") {
            filteredPlayerEntriesList = executeDetailQueries("team", team, filteredPlayerEntriesList);
        }
    }

    /**
     * executeDetailQueries function - takes the current list and transfers all of
     * the players that
     * match filters applied using the comboboxes into a new list which is returned
     * 
     * @param comboBoxCategory
     * @param detail
     * @param unfilteredList
     * @return
     */
    public List<PlayerEntry> executeDetailQueries(String comboBoxCategory, String detail,
            List<PlayerEntry> unfilteredList) {
        List<PlayerEntry> filteredByPlayerDetails = new ArrayList<>();
        // adds all values to the list that have the selcted name
        if (comboBoxCategory == "name") {
            for (PlayerEntry p : unfilteredList) {
                if (p.getPlayerName().equals(detail)) {
                    filteredByPlayerDetails.add(p);
                }
            }
            // adds all values to the list that have the selcted nation
        } else if (comboBoxCategory == "nation") {
            for (PlayerEntry p : unfilteredList) {
                if (p.getNation().equals(detail)) {
                    filteredByPlayerDetails.add(p);
                }
            }
            // adds all values to the list that have the selcted position
        } else if (comboBoxCategory == "position") {
            for (PlayerEntry p : unfilteredList) {
                if (p.getPosition().equals(detail)) {
                    filteredByPlayerDetails.add(p);
                }
            }
            // adds all values to the list that have the selcted team
        } else if (comboBoxCategory == "team") {
            for (PlayerEntry p : unfilteredList) {
                if (p.getTeam().equals(detail)) {
                    filteredByPlayerDetails.add(p);
                }
            }
        }
        return filteredByPlayerDetails;
    }

    /**
     * addFilters method - adds filters input into GUI to subQueryList ArrayList
     */
    @Override
    public void addFilter() {
        // gets the query values from the interface
        PlayerProperty property = PlayerProperty.fromPropertyName(comboQueryProperties.getSelectedItem().toString());
        String operator = comboOperators.getSelectedItem().toString();
        // creates a subquery with the values
        SubQuery subquery = new SubQuery(property, operator, Double.valueOf(value.getText()));
        // adds the subquery to the list and updates the interface
        subQueryList.add(subquery);
        subQueriesTextArea.setText(subQueryList.toString());
        updateInterface();
    }

    @Override
    public boolean isMinCheckBoxSelected() {
        // returns true if the mincheckbox is selected
        if (minCheckBox.isSelected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isMaxCheckBoxSelected() {
        // returns true if the maxcheckbox is selected
        if (maxCheckBox.isSelected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isAverageCheckBoxSelected() {
        // returns true if the averagecheckbox is selected
        if (averageCheckBox.isSelected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * updateInterface function - calls all the relevant functions
     * that update the interface so they can all be called at once
     */
    public void updateInterface() {
        executeQuery();
        updatePlayerCatalogDetailsBox();
        updateStatistics();
        updateRadarChart();
    }

    public AbstractPlayerCatalog getPlayerCatalog() {
        return playerCatalog;
    }

}
