package com.gokhanbarisaker.osapis;

import com.gokhanbarisaker.osapis.model.UriQueryPair;
import com.gokhanbarisaker.osapis.utility.UriQueryParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gokhanbarisaker on 6/4/15.
 */
public class UriQueryParserTest {
    private static final String QUERY_REGULAR = "q=test&oq=test&aqs=chrome..69i57j69i60l4j69i59.414j0j8&sourceid=chrome&es_sm=91&ie=UTF-8";
    private static final String QUERY_BACON = "t-bone=&=porchetta&&beef";

    private UriQueryParser parser;

    @Before public void setup() {
        parser = new UriQueryParser();
    }

    @Test public void testParseRegular(){
        // test with valid chars
        List<UriQueryPair> parsed = parser.parse(QUERY_REGULAR);

        List<UriQueryPair> expected = Arrays.asList(
                new UriQueryPair("q", "test"),
                new UriQueryPair("oq", "test"),
                new UriQueryPair("aqs", "chrome..69i57j69i60l4j69i59.414j0j8"),
                new UriQueryPair("sourceid", "chrome"),
                new UriQueryPair("es_sm", "91"),
                new UriQueryPair("ie", "UTF-8")
        );

        Assert.assertArrayEquals("It should parse the query correctly", parsed.toArray(), expected.toArray());
    }

    @Test public void testParseBacon() {
        List<UriQueryPair> parsed = parser.parse(QUERY_BACON);

        List<UriQueryPair> expected = Arrays.asList(
                new UriQueryPair("t-bone", ""),
                new UriQueryPair("", "porchetta")
        );

        Assert.assertArrayEquals("It should parse the query correctly", parsed.toArray(), expected.toArray());
    }
}
