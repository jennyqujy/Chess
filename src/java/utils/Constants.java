package utils;

import java.awt.*;

public class Constants {
    public static final int boardSize = 8;

    public enum ChessDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        UPLEFT,
        UPRIGHT,
        DOWNLEFT,
        DOWNRIGHT,
    }

    public static class Colors{
        public static final Color darkColor = new Color(123,149,84);
        public static final Color lightColor = new Color(238, 238, 209);
        public static final Color highlightColor1 = new Color(246,246,130);
        public static final Color highlightColor2 = new Color(186,202,68);
    }
}
