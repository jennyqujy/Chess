package utils;

public class PieceColor {

    private ColorType colorType;

    public static PieceColor white() {
        return new PieceColor(ColorType.WHITE);
    }

    public static PieceColor black() {
        return new PieceColor(ColorType.BLACK);
    }

    public enum ColorType {
        BLACK,
        WHITE
    }

    PieceColor(ColorType colorType) {
        this.colorType = colorType;
    }

    public ColorType getColorType() {
        return this.colorType;
    }

}
