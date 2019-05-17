package Scrapers;

import Categories.Category;
import ObjectMaps.Article;

import java.util.*;

/**
 * Web scraper used for collecting data from multiple sites that publish computer science related articles.
 *
 * This is the body class clients will use to interact with the scrapers
 */
public class WebScraper {

    /**
     * Contains all the links currently being scraped
     */
    public enum AvailableScrapingLink {
        ARX_URL("https://arxiv.org/", new ArxScraper());

        private final String link;
        private ArticleScraper scraper;

        /**
         * @param link The link to the specified site
         * @param scraper an initialized object for the scraper used for this url
         */
        AvailableScrapingLink(final String link, ArticleScraper scraper) {
            this.link = link;
            this.scraper = scraper;
        }

        /**
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return link;
        }

        public ArticleScraper getScraper() {
            return scraper;
        }

    }


    /**
     * Scrapes data from all sites current being processed by scrapers. No strict guarantee of recent articles being
     * first since it's an aggregate of data that doesn't include date.
     *
     * @param amount The amount of articles requested
     * @param category The category requested to scraped
     * @return Returns a map of the category to a map containing the index of the article mapped to a list of info: the title, description, author,
     *         and pdf link. If parameter amount > amount of articles available, returns amount of articles available.
     *         Second map will be null if no items in that category
     * @throws IllegalArgumentException if category is null
     */
    public static List<Article> scrapeAllSites(int amount, Category category) {
        if (category == null) {
            throw new IllegalArgumentException();
        }

        List<Article> aggregate = new ArrayList<Article>();
        for (AvailableScrapingLink links : AvailableScrapingLink.values()) {
            aggregate.addAll(links.getScraper().scrapeData(amount, category));
        }

        return aggregate;
    }


    /**
     * Scrapes data from specified site from AvailableScrapingLinks
     *
     * @param amount The amount of articles requested
     * @param category The category requested to scraped
     * @param link The link to be scraped from the choices in AvailableScrapingLinks
     * @return Returns a map of the category to a map containing a list of Articles containing: the title, description, author,
     *         and pdf link. If parameter amount > amount of articles available, returns amount of articles available.
     *         Second map will be null if no items in that category
     * @throws IllegalArgumentException if category is null
     */
    public static List<Article> scrapeSite(int amount, Category category, AvailableScrapingLink link) {
        if (category == null) {
            throw new IllegalArgumentException();
        }


        return link.scraper.scrapeData(amount, category);
    }


    public static void main(String[] args) {
        scrapeAllSites(20, Category.COMPUTER_SCIENCE__GAME_THEORY);
    }
}