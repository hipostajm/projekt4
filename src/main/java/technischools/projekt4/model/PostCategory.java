package technischools.projekt4.model;

public enum PostCategory {
    URGENT("Urgent"),
    INFO("Info"),
    EVENT("Event"),
    ANIMEGIRLS("Anime Girls!"),
    HASKELL("Haskell"),
    BIGDECIMAL("BigDecimal");

    private final String displayName;

    PostCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
