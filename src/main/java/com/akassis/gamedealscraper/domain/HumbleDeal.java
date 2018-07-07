package com.akassis.gamedealscraper.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = HumbleDealDeserializer.class)
public class HumbleDeal extends Deal {

    // TODO: Extract "page_size" to a parser argument
    private static final String requestUrl =
            "https://www.humblebundle.com/store/api/search?sort=discount&filter=onsale&request=1&page_size=10";

    public HumbleDeal() {

    }

    public static String getRequestUrl() {
        return requestUrl;
    }
}