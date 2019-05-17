package ObjectMaps;

/**
 * An object representing the scraped data from an article
 */
public class Article {

    public String title;
    public String description;
    public String author;
    public String link;


    public Article(String title, String description, String author, String link) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.link = link;
    }

    public Article(String title, String author, String link) {
        this(title, "", author, link);
    }
}
