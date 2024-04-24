package uk.ac.sheffield.com1003.assignment;

import uk.ac.sheffield.com1003.assignment.codeprovided.*;
import java.util.*;

/**
 * this class is used to parse queries provided from text file
 */
public class QueryParser extends AbstractQueryParser {
    /**
     * this method reads through provided query tokens and creates queries from
     * them,
     * it then returns a list of all the queries inputted
     */
    @Override
    public List<Query> readQueries(List<String> queryTokens) throws IllegalArgumentException {
        League leagueType;
        List<Query> queryList = new ArrayList<>();
        PlayerProperty playerProperty;
        String operator;
        double value;
        int i = 0;

        // iterates through the querytokens list
        while (i < queryTokens.size()) {
            // checks if the list is formatted correctly
            if (queryTokens.get(i).equals("select")) {
                // creates an empty list that will contain all subqueries
                List<SubQuery> subQueryList = new ArrayList<>();
                // sets league according to the query tokens
                if (queryTokens.get(i + 2).equals("or")) {
                    leagueType = League.ALL;
                    i += 4;
                } else if (queryTokens.get(i + 1).equals("epl")) {
                    leagueType = League.EPL;
                    i += 2;
                } else if (queryTokens.get(i + 1).equals("liga")) {
                    leagueType = League.LIGA;
                    i += 2;
                } else {
                    throw new IllegalArgumentException();
                }

                // repeats until there are no more tokens left
                boolean anotherSubQuery = true;
                while (i < queryTokens.size() && anotherSubQuery) {
                    if (queryTokens.get(i).equals("where") || queryTokens.get(i).equals("and")) {
                        // creates a subquery with the values supplied in the list
                        playerProperty = PlayerProperty.fromName(queryTokens.get(i + 1));
                        operator = queryTokens.get(i + 2);
                        value = Double.valueOf(queryTokens.get(i + 3));
                        SubQuery subQuery = new SubQuery(playerProperty, operator, value);
                        // adds the subquery to the list
                        subQueryList.add(subQuery);
                        i += 4;
                    } else if (queryTokens.get(i).equals("select")) {
                        // shows the end of the current query
                        anotherSubQuery = false;
                    } else {
                        throw new IllegalArgumentException();
                    }

                }
                // creates a query object containing all of the subqueries found and adds the
                // query to the list
                Query query = new Query(subQueryList, leagueType);
                queryList.add(query);
            } else {
                throw new IllegalArgumentException();
            }
        }
        return queryList;
    }

}
