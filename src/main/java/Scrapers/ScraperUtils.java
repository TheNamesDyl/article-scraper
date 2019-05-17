package Scrapers;

import Categories.Category;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Static utility methods used for scraping data
 */
public class ScraperUtils {

    protected static HtmlPage loadPage(Category category, WebScraper.AvailableScrapingLink link) {
        if (link.toString().equals(WebScraper.AvailableScrapingLink.ARX_URL.toString())) {
            return getElementsOnSite(WebScraper.AvailableScrapingLink.ARX_URL + "/list/" + category.getArxCategoryLink() + "/recent");
        }
        return null;
    }

    /**
     * Goes to the site requested and returns a list of html elements from that site
     *
     * @param searchUrl
     * @return The HtmlPage of the url searchUrl requested, if url is malformed or connection fails,
     * returns null and prints the stacktrace
     */
    protected static HtmlPage getElementsOnSite(String searchUrl) {
        WebClient client = new WebClient();

        // Makes page load faster
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            HtmlPage page = client.getPage(searchUrl);
            return page;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
