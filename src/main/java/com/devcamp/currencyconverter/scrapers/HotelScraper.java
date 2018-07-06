package com.devcamp.currencyconverter.scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class HotelScraper {

    private static final String URL_FIRST_PART = "https://alpha.locktrip.com/hotels/listings/";
    private static final String URL_SECOND_PART = "?region=";
    private static final String URL_THRID_PART = "&currency=USD&startDate=07/07/2018&endDate=08/07/2018&rooms=%5B%7B%22adults%22:1,%22children%22:%5B%5D%7D%5D";


    private static final Map<String, Integer> REGIONS = Map.of(
            "France", 16471
            , "United Kingdom", 52612
            , "United States", 19793
            , "India", 16874
            , "Australia", 15375
            , "Canada", 15751
            , "Singapore", 18196
            , "Bulgaria", 15664);

    private static final Map<String, Integer> REGIONS_START_ID = Map.of(
            "France", 2651
            , "United Kingdom", 6542
            , "United States", 8234
            , "India", 31835
            , "Australia", 1717
            , "Canada", 1566
            , "Singapore", 8811
            , "Bulgaria", 13233);

    private static final String HOTEL_SECTION_ID = "hotel-section";
    private static final String TITLE_TAG = "h2";


    //@PostConstruct
    public void scrape(){

//        int counter = 1;
//        for (Map.Entry<String, Integer> pair : REGIONS.entrySet()) {
//            int startId = REGIONS_START_ID.get(pair.getKey());
//            for (int i = startId; i < startId + 10; i++) {
//                String url = String.format("%s%s%s%s%s", URL_FIRST_PART, i, URL_SECOND_PART, pair.getValue(), URL_THRID_PART);
//                System.out.println(counter++);
//                System.out.println(url);
//                try {
//                    Document document = Jsoup.connect(url).timeout(0).get();
//                    Element aa = document.getElementById("app");
//                    System.out.println(aa.text());
//                    System.out.println(document.text());
//                    Element hotelSection = document.getElementById(HOTEL_SECTION_ID);
//                    Elements title = hotelSection.getElementsByTag(TITLE_TAG);
//                    System.out.println(title.text());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
