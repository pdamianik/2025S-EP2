package AB3;
import AB3.Interfaces.Decoder;

/**
 * The BrailleReader class provides functionality to translate Braille text lines represented as
 * scan lines into ASCII text. It uses a Decoder to decode Braille character bitmaps into corresponding
 * ASCII characters.
 */
public class BrailleReader {
    private final int WIDTH=2;
    private final int HEIGHT=3;

    private Decoder decoder;    // can store a reference to a BrailleDecoder

    /**
     * Constructs a BrailleReader instance.
     */
    public BrailleReader(Decoder decoder){ // Hint: pass your BrailleDecoder to this constructor
        this.decoder = decoder;
    }

    /**
     * Extracts a Braille character represented as a 2D array from the given Braille scanlines.
     *
     * @param position the zero-based number of the Braille character in the given line data.
     * @param spacing the number of characters used as spacing between Braille characters in the line data.
     * @param brailleLine an array of strings (scanlines) representing the Braille line data.
     * @return the bitmap of a single Braille character extracted from the provided
     *         line data, or null if the {@code brailleLine} is invalid or {@code null},
     *         or the specified position is out of bounds.
     */
    private char[][] getBrailleChar(int position, int spacing, String[] brailleLine){
        int offset = (WIDTH + spacing) * position;
        if (brailleLine == null || brailleLine.length < HEIGHT || offset < 0) return null;

        int rowLength = brailleLine[0].length();
        int remainingChars = rowLength % (WIDTH + spacing);
        if (offset + WIDTH > rowLength || remainingChars != WIDTH) return null;

        char[][] brailleChar = new char[HEIGHT][WIDTH];
        for (int row = 0; row < HEIGHT; row++) {
            if (brailleLine[row].length() != rowLength) return null;
            System.arraycopy(brailleLine[row].toCharArray(), offset, brailleChar[row], 0, WIDTH);
        }
        return brailleChar;
    }

    /**
     * Translates the given Braille text lines into an ASCII representation.
     *
     * @param brailleLine an array of strings representing scanlines of Braille text.
     * @param dotSymbol the character used to represent raised Braille dots in the bitmap.
     * @param spacing the number of spaces between individual Braille characters within the input.
     * @return a string representing the ASCII translation of the Braille text. Returns an
     *         empty string if the input is invalid or no Braille characters are detected.
     */
    public String translate(String[] brailleLine, char dotSymbol, int spacing){
        if (brailleLine == null || brailleLine.length < HEIGHT) return "";

        int lineLength = brailleLine[0].length();
        int suffixLength = lineLength % (WIDTH + spacing);
        if (suffixLength != WIDTH) return "";
        for (String line : brailleLine) {
            if (line.length() != lineLength) return "";
        }

        int bitmapCount = lineLength / (WIDTH + spacing) + 1;
        StringBuilder decoded = new StringBuilder(bitmapCount);
        for (int i = 0; i < bitmapCount; i++) {
            char[][] bitmap = this.getBrailleChar(i, spacing, brailleLine);
            if (bitmap == null) return "";
            char decodedChar = this.decoder.decodeBitmap(bitmap, dotSymbol);
            if (decodedChar == 0) return "";
            decoded.append(decodedChar);
        }
        return decoded.toString();
    }
}
