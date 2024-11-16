package com.github.allex_goncalves.book_catalog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Genre {
    private static final Logger LOGGER = Logger.getLogger(Genre.class.getName());
    private static List<String> genres;

    public Genre() {
        this.genres = new ArrayList<>();
    }

    public static List<String> extractGenres(List<String> apiList) {
        List<String> genres = new ArrayList<>();
        for (String apiCategory : apiList) {
            String cleanedCategory = apiCategory.replaceFirst("(?i)^Browsing:\\s*", "").trim();
            genres.add(cleanedCategory);
            LOGGER.info("Added genre: " + cleanedCategory);
        }
        return genres;
    }

    public static String toString(String t) {
        return String.join("\n", genres);
    }

}
