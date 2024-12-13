package com.example.quiz11.constants;

public enum QuesType {

    SINGLE("single"),
    MULTI("multi"),
    TEXT("text");

    private String type;

    private QuesType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static boolean checkType(String type) {
//		if (type.equalsIgnoreCase(QuesType.SINGLE.toString())
//				|| type.equalsIgnoreCase(QuesType.MULTI.toString())
//				|| type.equalsIgnoreCase(QuesType.TEXT.toString())) {
//			return true;
//		}

        // 上面那段可以用以下替換
        for (QuesType item : QuesType.values()) {
            if (item.getType().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}
