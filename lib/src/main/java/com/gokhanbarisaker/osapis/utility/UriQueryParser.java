package com.gokhanbarisaker.osapis.utility;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gokhanbarisaker on 1/6/15.
 */
public class UriQueryParser {

    /**
     * Not suitable for repeating query key values, for they will be overwritten in dictionary. e.g.,<br>
     *<br>
     *  ...?key1=value1&amp;key2=value2&amp;key1=value3<br>
     *<br>
     *  that will result with loosing the key1. i.e,<br>
     *  <br>
     *  {<br>
     *      "key1":"value3",<br>
     *      "key2":"value2"<br>
     *  }<br>
     *
     *
     * @param queryString <a href="http://en.wikipedia.org/wiki/Query_string">Query string</a>
     * @return The parsed query dictionary
     */
    public Map<String, String> parse(final String queryString)
    {
        Map<String, String> queryMap = new HashMap<>();

        StringUtils.removeStart(queryString, "?");
        String[] queryPairs = StringUtils.split(queryString, '&');

        if (queryPairs != null)
        {
            for (String queryPair : queryPairs)
            {
                String[] queryPairParts = StringUtils.split(queryPair, '=');

                if (queryPairParts != null && queryPairParts.length == 2)
                {
                    try
                    {
                        String key = URLDecoder.decode(queryPairParts[0], "UTF-8");
                        String value = URLDecoder.decode(queryPairParts[1], "UTF-8");

                        queryMap.put(key, value);
                    }
                    catch (UnsupportedEncodingException e) { /* Ignored */ }
                }
            }
        }

        return queryMap;
    }
}
