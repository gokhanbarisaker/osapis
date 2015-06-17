package com.gokhanbarisaker.osapis.utility;

import android.util.Log;

import com.gokhanbarisaker.osapis.model.UriQueryPair;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokhanbarisaker on 1/6/15.
 */
public class UriQueryParser {

    /**
     * Parser for raw/encoded query string<br>
     *<br>
     *  ...?key1=value1&amp;key2=value2&amp;key1=value3<br>
     *<br>
     *  that will result as,<br>
     *  <br>
     *  <pre>
     *  [<br>
     *      {"key":"value1","value":"value1"},<br>
     *      {"key":"value1","value":"value1"},<br>
     *      {"key":"value1","value":"value1"}<br>
     *  ]<br>
     *  </pre>
     *
     * @param queryString <a href="http://en.wikipedia.org/wiki/Query_string">Query string</a>
     * @return The parsed list of query pairs
     */
    public List<UriQueryPair> parse(String queryString)
    {
        List<UriQueryPair> queryPairs = new ArrayList<>();

        queryString = StringUtils.removeStart(queryString, "?");
        String[] rawQueryPairs = StringUtils.split(queryString, '&');

        if (rawQueryPairs != null)
        {
            for (String rawQueryPair : rawQueryPairs)
            {
                String[] queryPairParts = StringUtils.split(rawQueryPair, '=');

                if (queryPairParts != null) {
                    int queryPairPartsLength = queryPairParts.length;

                    if (queryPairPartsLength == 2) {
                        try {
                            String key = URLDecoder.decode(queryPairParts[0], "UTF-8");
                            String value = URLDecoder.decode(queryPairParts[1], "UTF-8");
                            UriQueryPair queryPair = new UriQueryPair(key, value);

                            queryPairs.add(queryPair);
                        } catch (UnsupportedEncodingException e) { /* Ignored */ }
                    }
                    else if (queryPairPartsLength == 1)
                    {
                        if (rawQueryPair.startsWith("=")) {
                            try
                            {
                                String value = URLDecoder.decode(queryPairParts[0], "UTF-8");
                                UriQueryPair queryPair = new UriQueryPair("", value);

                                queryPairs.add(queryPair);
                            }
                            catch (UnsupportedEncodingException e) { /* Ignored */ }
                        }
                        else if (rawQueryPair.endsWith("=")) {
                            try
                            {
                                String key = URLDecoder.decode(queryPairParts[0], "UTF-8");
                                UriQueryPair queryPair = new UriQueryPair(key, "");

                                queryPairs.add(queryPair);
                            }
                            catch (UnsupportedEncodingException e) { /* Ignored */ }
                        }
                    }
                    else
                    {
                        Log.e("Query parser", "Unable to split query parameter in parts: " + rawQueryPair);
                    }
                }
            }
        }

        return queryPairs;
    }
}
