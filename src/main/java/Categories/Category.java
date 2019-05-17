package Categories;

public enum Category {
    ARTIFICIAL_INTELLIGENCE("ArtificialIntelligence", new String[]{"cs.AI"}),
    COMPUTATION_AND_LANGUAGE("ComplexityAndLanguage", new String[]{"cs.CL"}),
    COMPUTATIONAL_COMPLEXITY("ComputationalComplexity", new String[]{"cs.CC"}),
    COMPUTATIONAL_ENGINEERING_FINANCE("ComputationalEngineeringFinance", new String[]{"cs.CE"}),
    COMPUTATIONAL_GEOMETRY("Computational_Geometry", new String[]{"cs.CG"}),
    COMPUTER_SCIENCE__GAME_THEORY("ComputerScienceGameTheory", new String[]{"cs.GT"}),
    COMPUTER_VISION_PATTERN_RECOGNITION("ComputerVisionPatternRecognition", new String[]{"cs.CV"}),
    COMPUTERS_AND_SOCIETY("ComputersAndSociety", new String[]{"cs.CY"});

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private String categoryName;
    private final String ArxCategoryLink;

    /**
     * @param categoryLink The part of the url to identify the category on the page, used for navigating to the right link
     */
    Category(String categoryName, final String[] categoryLink) {
        this.categoryName = categoryName;
        this.ArxCategoryLink = categoryLink[0];
    }

    public String getArxCategoryLink() {
        return ArxCategoryLink;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
