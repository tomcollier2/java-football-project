package uk.ac.sheffield.com1003.assignment;

import uk.ac.sheffield.com1003.assignment.codeprovided.*;

import java.util.*;

/**
 * this class - implements functions that help with handling the data
 * on all of the players
 */
public class PlayerCatalog extends AbstractPlayerCatalog {
    /**
     * Constructor
     */
    public PlayerCatalog(String eplFilename, String ligaFilename) {
        super(eplFilename, ligaFilename);
    }

    /**
     * this function takes a line containing all of the information about a player
     * breaks the line up and creates a playerproperty map with all of the
     * appropriate values
     */
    @Override
    public PlayerPropertyMap parsePlayerEntryLine(String line) throws IllegalArgumentException {
        // splits the line up into individual values
        var values = line.split(",");
        if (values.length != 29) {
            throw new IllegalArgumentException();
        }
        // assignes each value to there appropriate propert in the playerpropertymap
        PlayerPropertyMap playerPropertyMap = new PlayerPropertyMap();
        playerPropertyMap.putDetail(PlayerDetail.PLAYER, values[0]);
        playerPropertyMap.putDetail(PlayerDetail.NATION, values[1]);
        playerPropertyMap.putDetail(PlayerDetail.POSITION, values[2]);
        playerPropertyMap.putDetail(PlayerDetail.TEAM, values[3]);
        playerPropertyMap.put(PlayerProperty.AGE, Integer.valueOf(values[4]));
        playerPropertyMap.put(PlayerProperty.MATCHES, Integer.valueOf(values[5]));
        playerPropertyMap.put(PlayerProperty.MINUTES, Integer.valueOf(values[6]));
        playerPropertyMap.put(PlayerProperty.YELLOWCARDS, Double.valueOf(values[7]));
        playerPropertyMap.put(PlayerProperty.REDCARDS, Double.valueOf(values[8]));
        playerPropertyMap.put(PlayerProperty.GOALS, Integer.valueOf(values[9]));
        playerPropertyMap.put(PlayerProperty.PKGOALS, Double.valueOf(values[10]));
        playerPropertyMap.put(PlayerProperty.PKATTEMPTS, Double.valueOf(values[11]));
        playerPropertyMap.put(PlayerProperty.ASSISTS, Double.valueOf(values[12]));
        playerPropertyMap.put(PlayerProperty.OWNGOALS, Double.valueOf(values[13]));
        playerPropertyMap.put(PlayerProperty.PASSATTEMPTED, Double.valueOf(values[14]));
        playerPropertyMap.put(PlayerProperty.PASSCOMPLETED, Double.valueOf(values[15]));
        playerPropertyMap.put(PlayerProperty.AERIALSWON, Double.valueOf(values[16]));
        playerPropertyMap.put(PlayerProperty.AERIALSLOST, Double.valueOf(values[17]));
        playerPropertyMap.put(PlayerProperty.AERIALSWONPERC, Double.valueOf(values[18]));
        playerPropertyMap.put(PlayerProperty.TACKLES, Double.valueOf(values[19]));
        playerPropertyMap.put(PlayerProperty.TACKLESWON, Double.valueOf(values[20]));
        playerPropertyMap.put(PlayerProperty.CLEARANCES, Double.valueOf(values[21]));
        playerPropertyMap.put(PlayerProperty.FOULSCOMMITTED, Double.valueOf(values[22]));
        playerPropertyMap.put(PlayerProperty.PKCONCEDED, Double.valueOf(values[23]));
        playerPropertyMap.put(PlayerProperty.SHOTS, Double.valueOf(values[24]));
        playerPropertyMap.put(PlayerProperty.SHOTSTARGET, Double.valueOf(values[25]));
        playerPropertyMap.put(PlayerProperty.FOULSDRAWN, Double.valueOf(values[26]));
        playerPropertyMap.put(PlayerProperty.CROSSES, Double.valueOf(values[27]));
        playerPropertyMap.put(PlayerProperty.PKWON, Double.valueOf(values[28]));
        return playerPropertyMap;
    }

    /**
     * this function adds a player entry list to the player catalog
     * that contains the players from both leagues
     */
    @Override
    public void updatePlayerCatalog() {
        // creates a list for prem liga and combined leagues
        List<PlayerEntry> prem = playerEntriesMap.get(League.EPL);
        List<PlayerEntry> liga = playerEntriesMap.get(League.LIGA);
        List<PlayerEntry> combined = new ArrayList<>();
        // prem and liga lists added to combined
        combined.addAll(prem);
        combined.addAll(liga);
        // the list is added to the player catalog
        playerEntriesMap.put(League.ALL, combined);
    }

    /**
     * this methods returns the minimum value of a property in a given
     * playerentrylist
     */
    @Override
    public double getMinimumValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList)
            throws NoSuchElementException {
        // creates a list containing all the values of the selected property
        List<Double> listOfProperties = new ArrayList<>();
        for (PlayerEntry p : playerEntryList) {
            listOfProperties.add(p.getProperty(playerProperty));
        }
        // returns the smallest value in the list
        return Collections.min(listOfProperties);
    }

    /**
     * this methods returns the maximum value of a property in a given
     * playerentrylist
     */
    @Override
    public double getMaximumValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList)
            throws NoSuchElementException {
        // creates a list containing all the values of the selected property
        List<Double> listOfProperties = new ArrayList<>();
        for (PlayerEntry p : playerEntryList) {
            listOfProperties.add(p.getProperty(playerProperty));
        }
        // returns the largest value in the list
        return Collections.max(listOfProperties);
    }

    /**
     * this methods returns the average value of a property in a given
     * playerentrylist
     */
    @Override
    public double getMeanAverageValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList)
            throws NoSuchElementException {
        // creates a variable containing the sum of all the values of the selected
        // property
        Double sumOfProperties = 0.0;
        for (PlayerEntry p : playerEntryList) {
            sumOfProperties += (p.getProperty(playerProperty));
        }
        // returns the variable divided by the number of players in the list to
        // calculate mean
        return sumOfProperties / (playerEntryList.size());
    }

    /**
     * this method returns a list which contains the first five values of a list
     */
    @Override
    public List<PlayerEntry> getFirstFivePlayerEntries(League type) {
        List<PlayerEntry> listOfPlayers = getPlayerEntriesList(type);
        // the first five values are added to an empty list
        List<PlayerEntry> firstFive = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            firstFive.add(listOfPlayers.get(i));
        }
        return firstFive;
    }

}
