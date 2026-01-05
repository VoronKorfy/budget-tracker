package service;

public enum MenuOption {
    ADD_INCOME(1),
    ADD_EXPENSE(2),
    DISPLAY_LAST(3),
    DISPLAY_BY_DATE(4),
    SHOW_INCOME_HISTORY(5),
    SHOW_EXPENSE_HISTORY(6),
    EXIT(0);

    private final int code;

    MenuOption(int code) {
        this.code = code;
    }

    public static MenuOption fromInt(int value) {
        for (MenuOption option : values()) {
            if (option.code == value) {
                return option;
            }
        }
        return null;
    }
}
