/* DO NOT EDIT */
package AB3.Provided;

/**
 * The {@code BrailleFont} class represents a set of printable Braille characters of type {@code byte[][]} and corresponding
 * mappings from ASCII characters ({@code char}) to this set.
 * <p>All printable characters are initialized at construction time and are of equal size (monospaced).</p>
 */
public class BrailleFont {
    private final int height;   // height of characters
    private final int width;    // width of characters (remark: font is monospaced)

    /**
     * A 3-dimensional array containing printable Braille characters (bitmaps) in ascending alphabetic order.
     * <p>In detail, it is an array of 2-dimensional matrices (bitmaps):
     * {@code lowerCaseLetters[index of character][bitMap row][bitmap column]}. Each bitmap contains dot and space symbols
     * according to the letter's Braille cell configuration.</p>
     * <p>The array does not include representations for non-letter characters or capital letters.
     * Other characters, such as white space, are handled separately.</p>
     */
    private final char[][][] lowerCaseLetters;    // bitmaps for all lowercase letters

    /**
     * Represents the white space character.
     * <p>This array provides a printable representation (bitmap) of a white space within Braille texts.</p>
     * <p>The array is initialized during the construction of the {@code BrailleFont} object.</p>
     */
    private final char[][] whiteSpace;    // the white space character


    /**
     * Constructs a {@code BrailleFont} object and calculates the font's bitmaps.
     *
     * @param height      number of lines of font's characters (bitmap height)
     * @param width       number of columns of font's characters (bitmap width)
     * @param dotSymbol   the character used to represent a filled cell (dot) within a character's bitmap.
     * @param spaceSymbol the character used to represent an empty cell (space) within a character's bitmap.
     * @param encoder     the Braille encoder ({@code class BrailleEncoder}) used to calculate the font's bitmaps at construction time.
     *                    <p>Precondition: (encoder != null)</p>
     */
    public BrailleFont(int height, int width, char dotSymbol, char spaceSymbol, BrailleEncoder encoder) {
        this.height = height;
        this.width = width;

        // initialize bitmaps for the lower case alphabet
        lowerCaseLetters = new char['z' - 'a' + 1][][];

        for (char character = 'a'; character <= 'z'; character++) {
            lowerCaseLetters[character - 'a'] = new char[height][width];

            // calculate binary representation
            byte brailleCharBin = encoder.toBinary(character);

            // create dot pattern from binary representation
            int index = 0;
            for (int column = 0; column < width; column++) {
                for (int row = 0; row < height; row++) {
                    lowerCaseLetters[character - 'a'][row][column] = ((brailleCharBin & (1 << index++)) != 0) ? dotSymbol : spaceSymbol;
                }
            }
        }

        // initialize bitmap for white space
        whiteSpace = new char[height][width];
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                whiteSpace[row][column] = spaceSymbol;
            }
        }
    }


    /**
     * Retrieves a bitmap ({@code char[][]}) of a given ASCII character.
     *
     * @param character the ASCII character for which a printable character will be returned.
     * @return a bitmap ({@code char[][]}) that corresponds to the given ASCII character.
     * For letters, it returns the corresponding lowercase printable character from array {@code lowerCaseLetters[]}.
     * For non-letters, it returns the representation of a white space ({@code whiteSpace}).
     */
    public char[][] getBitmap(char character) {
        // This implementation differs from AB1. It is not required by specification (character is ASCII letter)
        // but is another clean way to guard array access without relying on specified ASCII restrictions.
        if ((character >= 'a' && character <= 'z')) {
            return lowerCaseLetters[character- 'a'].clone();
            // THINK ABOUT IT: What would a code variant like this do?
            // return lowerCaseLetters[character- 'a'];
        }
        if ((character >= 'A' && character <= 'Z')) {
            return lowerCaseLetters[Character.toLowerCase(character) - 'a'].clone();
            // THINK ABOUT IT: What would a code variant like this do?
            // return lowerCaseLetters[Character.toLowerCase(character) - 'a'];
        }

        // for all other cases return a white space char
        return whiteSpace;
    }

    /**
     * Returns the font's height.
     *
     * @return the number of rows of a character's bitmap.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the font's width (the font is monospaced).
     *
     * @return the number of columns of a character's bitmap.
     */
    public int getWidth() {
        return width;
    }
}