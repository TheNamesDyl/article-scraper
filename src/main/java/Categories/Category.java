package Categories;

public enum Category {
    ARTIFICIAL_INTELLIGENCE(new String[]{"cs.AI"}),
    COMPUTATION_AND_LANGUAGE(new String[]{"cs.CL"}),
    COMPUTATIONAL_COMPLEXITY(new String[]{"cs.CC"}),
    COMPUTATIONAL_ENGINEERING_FINANCE(new String[]{"cs.CE"}),
    COMPUTATIONAL_GEOMETRY(new String[]{"cs.CG"}),
    COMPUTER_SCIENCE__GAME_THEORY(new String[]{"cs.GT"}),
    COMPUTER_VISION_PATTERN_RECOGNITION(new String[]{"cs.CV"}),
    COMPUTERS_AND_SOCIETY(new String[]{"cs.CY"});

    private final String ArxCategoryLink;

    /**
     * @param categoryLink The part of the url to identify the category on the page, used for navigating to the right link
     */
    Category(final String[] categoryLink) {
        this.ArxCategoryLink = categoryLink[0];
    }

    public String getArxCategoryLink() {
        return ArxCategoryLink;
    }
}
