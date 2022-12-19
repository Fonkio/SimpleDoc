package fr.fonkio.utils;

public enum ConfigurationEnum {
    DOCLIST("doclist", ""),
    DOCTITLE("title", ""),
    DOCDESC("desc", ""),
    DOCLINK("link", ""),
    DOCID("id", ""),
    TOKEN("token", "Mettre le token ici");



    ConfigurationEnum(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    private final String key;
    private final String defaultValue;

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
