package Scrapers;

import Categories.Category;
import ObjectMaps.Article;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.net.MalformedURLException;
import java.util.*;


/**
 * Web scraper specifically for arxiv.org
 *
 * Note that arxiv does not have descriptions for it's articles
 */
public class ArxScraper extends ArticleScraper {


    /**
     * Scrapes data from arxiv.org. List is guaranteed to be in order from top to bottom on the site (most recent first)
     *
     * @param amount The amount of articles requested
     * @param category The category requested
     * @return Returns a map of integers, i, to list containing three items: title, author, links in that order specified by amount.
     *         If amount > available articles, returns available articles
     * @throws IllegalArgumentException if category == null
     */
    @Override
    protected List<Article> scrapeData(int amount, Category category) {
        if (category == null) {
            throw new IllegalArgumentException();
        }

        HtmlPage page = ScraperUtils.loadPage(category, WebScraper.AvailableScrapingLinks.ARX_URL);

        List<String> titles = scrapeTitles(amount, page);
        List<String> authors = scrapeAuthors(amount, page);
        List<String> links = scrapeLinks(amount, page);

        assert titles.size() == authors.size() && titles.size() == links.size();

        List<Article> items = new ArrayList<Article>();
        for (int i = 0; i < amount; i++) {
            items.add(new Article(titles.get(i), authors.get(i), links.get(i)));
        }

        return items;
    }

    /**
     * Scrapes title data. List is guaranteed to be in order from top to bottom on the site (most recent first)
     *
     * @param amount The amount of articles requested
     * @param page The page we are scraping from
     * @return Returns a list of titles specified by amount. If amount > available articles, returns available articles
     * @throws IllegalArgumentException if page == null
     */
    private static List<String> scrapeTitles(int amount, HtmlPage page) {
        if (page == null) {
            throw new IllegalArgumentException();
        }

        DomNodeList<DomNode> elements = page.querySelectorAll(".list-title");

        List<String> titles = new ArrayList<String>();
        for (DomNode element : elements) {
            if (amount > titles.size()) {
                titles.add(element.getLastChild().getNodeValue());
            }
        }

        return titles;
    }


    /**
     * Scrapes link data. List is guaranteed to be in order from top to bottom on the site (most recent first)
     *
     * @param amount The amount of articles requested
     * @param page The page we are scraping from
     * @return Returns a list of links specified by amount. If amount > available articles, returns available articles
     * @throws IllegalArgumentException if page == null
     */
    private static List<String> scrapeLinks(int amount, HtmlPage page) {
        if (page == null) {
            throw new IllegalArgumentException();
        }

        List<HtmlAnchor> unfilteredAnchors = page.getAnchors(); // anchors are <a> in html
        List<String> pdfLinks = new ArrayList<String>();

        for (HtmlAnchor anchor : unfilteredAnchors) { // just want anchors with text "pdf"
            if (anchor.getTextContent().equals("pdf")) {
                try {

                    if (amount > pdfLinks.size()) {
                        pdfLinks.add(page.getFullyQualifiedUrl(anchor.getHrefAttribute())
                                .toString()); // getHrefAttribute is relative url, converts it to absolute
                    }

                } catch(MalformedURLException e) { // should only happen if the site itself has malformed urls
                    e.printStackTrace();
                }
            }
        }


        return pdfLinks;
    }

    /**
     * Scrapes author data. List is guaranteed to be in order from top to bottom on the site
     *
     * @param amount The amount of articles requested
     * @param page The page we are scraping from
     * @return Returns a list of authors specified by amount. If amount > available articles, returns available articles
     * @throws IllegalArgumentException if page == null
     */
    private static List<String> scrapeAuthors(int amount, HtmlPage page) {
        if (page == null) {
            throw new IllegalArgumentException();
        }

        /* get all anchors under div .list-authors */
        List<HtmlAnchor> elements = page.getByXPath("//div[@class=\"list-authors\"]/a");

        List<String> authors = new ArrayList<String>(); // this list is a list of all the authors
        List<String> groupedAuthors = new ArrayList<String>(); // this list has a list of merged authors for this article into a string
        Iterator<HtmlAnchor> iterator = elements.iterator();
        HtmlAnchor lastElement = null;
        while(iterator.hasNext()) {
            HtmlAnchor element = iterator.next();

            if (amount > groupedAuthors.size()) {
                /* if the author is no longer for the same article, merge authors into groupedAuthors and clear authors */
                if (lastElement != null && !getItemNumberBehindAuthor(lastElement).equals(getItemNumberBehindAuthor(element))) {
                    String authorString = "";
                    for (String author : authors) {
                        authorString += author + " ";
                    }
                    groupedAuthors.add(authorString);
                    authors.clear();
                }

                authors.add(element.getFirstChild().getTextContent());
                lastElement = element;
            }
        }

        return groupedAuthors;
    }

    // TODO: Change this method so it depends on the text content (i.e [1]) of the article instead of depending
    //  on the relative location of anchor

    /**
     * Used for the particular case of finding the text element that contains the article number for
     * the specified author anchor
     *
     * @param anchor the author anchor
     * @return A string of the current article number this author is in
     */
    private static String getItemNumberBehindAuthor(HtmlAnchor anchor) {
        return anchor.getParentNode().getParentNode().getParentNode().getPreviousSibling().getPreviousSibling().getFirstChild().getFirstChild().getTextContent();
    }
}
