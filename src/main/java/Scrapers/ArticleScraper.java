package Scrapers;

import Categories.Category;
import ObjectMaps.Article;

import java.util.List;
import java.util.Map;

/**
 * All article web scrapers will have their own unique implementation for how they scrape data, but they all
 * should extend this.
 *
 * Note: It is an abstract class because Java does not allow protected methods in interfaces
 */
public abstract class ArticleScraper {
    protected abstract List<Article> scrapeData(int amount, Category category);
}
