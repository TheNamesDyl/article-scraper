import Categories.Category;
import ObjectMaps.Article;
import Scrapers.WebScraper;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Model
 *
 * Responsible for parsing input from rest service and feeding it to the web scraper
 */
@Service
public class ScrapeModel {

    /**
     * Scrape a specific site
     *
     * @param amount amount of articles requested, if more than available returns the amount available
     * @param categoryInput Available categories and their strings are listed in {@see main.java.Categories.Category}
     * @param linkInput link to the site to scrape, available site links are listed in {@see main.java.Scrapers.WebScraper#AvailableScrapingLinks}
     * @return Scraped data from that particular site and category, null if categoryInput or linkInput do not match
     *         a valid category or site.
     */
    public List<Article> getScrapedArticle(int amount, String categoryInput, String linkInput) {
        Category category = getCategoryFromString(categoryInput);
        WebScraper.AvailableScrapingLink link = getLinkFromString(linkInput);

        if (category == null || link == null) {
            return null;
        }

        return WebScraper.scrapeSite(amount, category, link);
    }


    /**
     * Scrape all computer science sites
     *
     * @param amount amount of articles requested, if more than available returns the amount available
     * @param categoryInput Available categories and their strings are listed in {@see main.java.Categories.Category}
     * @return Scraped data from that particular site and category, null if categoryInput or linkInput do not match
     *         a valid category or site.
     */
    public List<Article> getAllArticles(int amount, String categoryInput) {
        Category categoryResult = getCategoryFromString(categoryInput);

        if (categoryResult == null) {
            return null;
        }

        return WebScraper.scrapeAllSites(amount, categoryResult);
    }

    /**
     * Helper method for grabbing categories from strings {@see main.java.Categories.Category}
     *
     * @param categoryInput
     * @return category or null if not found
     */
    private Category getCategoryFromString(String categoryInput) {
        for (Category category : Category.values()) {
            if (category.getCategoryName().equalsIgnoreCase(categoryInput)) {
                return category;
            }
        }
        return null;
    }

    /**
     * Helper method for grabbing links from strings {@see main.java.Scrapers.WebScraper#AvailableScrapingLinks}
     *
     * @param linkInput
     * @return AvailableScrapingLinks or null if not found
     */
    private WebScraper.AvailableScrapingLink getLinkFromString(String linkInput) {
        for (WebScraper.AvailableScrapingLink link : WebScraper.AvailableScrapingLink.values()) {
            if (link.toString().equalsIgnoreCase(linkInput)) {
                return link;
            }
        }
        return null;
    }
}
