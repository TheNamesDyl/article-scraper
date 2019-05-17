import ObjectMaps.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The public API for getting scraped data from computer science sites
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class RipApi {

    /** Model for web scraper */
    private ScrapeModel model = new ScrapeModel();

    /**
     * Scrape a specific site
     *
     * @param amount amount of articles requested, if more than available returns the amount available
     * @param category Available categories and their strings are listed in {@see main.java.Categories.Category}
     * @param link link to the site to scrape, available site links are listed in {@see main.java.Scrapers.WebScraper#AvailableScrapingLinks}
     * @return Scraped data from that particular site and category, returns a bad request if category/link are not valid
     */
    @GetMapping("/scrape")
    @ResponseBody
    public ResponseEntity<List<Article>> getArticleData(@RequestParam(value="amount") int amount,
                                         @RequestParam("category") String category,
                                         @RequestParam("link") String link) {

        List<Article> response = model.getScrapedArticle(amount, category, link);

        if (response == null) {
            return new ResponseEntity<List<Article>>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Scrape an aggregate the sites for the most recent articles
     *
     * @param amount amount of articles requested, if more than available returns the amount available
     * @param category Available categories and their strings are listed in {@see main.java.Categories.Category}
     * @return A list of articles aggregated for that specific category, returns a bad request if category/link are not valid
     */
    @GetMapping("/scrape-all-sites")
    @ResponseBody
    public ResponseEntity<List<Article>> getBuildings(int amount, String category) {
        List<Article> response = model.getAllArticles(amount, category);

        if (response == null) {
            return new ResponseEntity<List<Article>>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(response);
    }
}
