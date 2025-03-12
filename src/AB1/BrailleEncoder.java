package AB1;

/**
 * This class implements an encoder from ASCII character to Braille binary representation.
 * In Braille binary representation a 2D Braille cell is represented by one byte,
 * where each dot is mapped to one specific bit.
 *
 * <p> The encoder utilizes a lookup table ({@code brailleLUT}) of predefined binary values
 * corresponding to ASCII characters </p>
 */
public class BrailleEncoder implements AB1.Interfaces.Encoder {

    /**
     * Lookup table (LUT) for Braille character encoding.
     *
     * <p>This array holds the Braille cells binary representation for all lower case ASCII letters ('a' to 'z').
     * Index 0 corresponds to letter 'a', index 1 to letter 'b', index 2 to letter 'c' and so on.
     * The binary representation of each letter specifies which dots are set within the letter's Braille cell,
     * a 2x3 dot matrix configuration of specific dots set (1 or 0).</p>
     *
     * The LUT must be used by the class method {@code toBinary}.
     */
    private static final byte[] brailleLUT = {
            0b000_001, 0b000_011, 0b001_001, 0b011_001, 0b010_001,   // a-e
            0b001_011, 0b011_011, 0b010_011, 0b001_010, 0b011_010,   // f-j
            0b000_101, 0b000_111, 0b001_101, 0b011_101, 0b010_101,   // k-o
            0b001_111, 0b011_111, 0b010_111, 0b001_110, 0b011_110,   // p-t
            0b100_101, 0b100_111, 0b111_010, 0b101_101, 0b111_101, 0b110_101,   // u-z
    };

    /**
     * Encoder for ASCII to binary representation of a Braille cell.
     *
     * <p>This method encodes a given ASCII character from range [a-z] to it's
     * corresponding Braille binary representation. The method implementation relies on the lookup table (LUT) {@code brailleLut}. </p>
     *
     * @param asciiChar an ASCII character from the lower case alphabet ['a','z']
     *                  <p>Precondition: (asciiChar>='a') && (asciiChar<='z')</p>
     */
    @Override
    public byte toBinary(char asciiChar) {
        return brailleLUT[asciiChar - 'a'];
    }


}
