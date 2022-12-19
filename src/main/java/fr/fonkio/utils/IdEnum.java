package fr.fonkio.utils;

public enum IdEnum {
    COMMAND_ADDDOC("adddoc"),
    COMMAND_REMOVEDOC("removedoc"),
    COMMAND_DOC("doc"),
    ARG_ID("id"),
    ARG_TITLE("title"),
    ARG_DESC("desc"),
    ARG_LINK("link");
    private final String id;

    IdEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static IdEnum parseString(String id) {
        for (IdEnum idEnum : IdEnum.values()) {
            if (idEnum.getId().equals(id)) {
                return idEnum;
            }
        }
        return null;
    }
}
