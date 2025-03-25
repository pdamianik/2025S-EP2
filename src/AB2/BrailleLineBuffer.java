package AB2;

import AB2.Interfaces.LineBuffer;

/**
 * The {@code BrailleLineBuffer} class implements an abstract data type for handling printable representation of
 * Braille text lines of arbitrary length.
 *
 * <p>The class contains a buffer for a sequence of monospaced Braille character bitmaps (see {@code class BrailleFont}
 * for details on the bitmaps), as much as methods to manipulate this buffer. Character sequence within the buffer are
 * continuous, so it must not contain any 'holes' (e.g. null values) between the bitmaps. Remark: White spaces
 * are valid characters and are not considered 'holes'.</p>
 *
 * <p>The buffer is initialized with a start size, but can grow while adding and shrink while deleting characters.
 * It can not grow while deleting, and shrink wile adding characters.</p>
 */
public class BrailleLineBuffer implements LineBuffer {
    private char[][][] buffer; // array of 2-dimensional matrices (bitmaps) representing the text line (DO NOT CHANGE)

    private final int initialSize;
    private int count;
    private final double growthFactor;
    private final double reductionRatio;

    /**
     * Constructs a {@code BrailleLineBuffer} object.
     *
     * @param initialSize    the initial size of the buffer.
     * @param growthFactor   factor by which the buffer is expanded, if it is full but a new Braille character
     *                       has to be added
     *                       <p>Preconditions: ( growthFactor > 1 )</p>
     * @param reductionRatio denotes a ratio (number of characters stored in buffer divided by size of buffer). If the
     *                       real ratio after a delete operation falls below this value, the buffer will be shrunk.
     *                       <p>Preconditions: ( reductionRatio < 1 ) </p>
     */
    public BrailleLineBuffer(int initialSize, double growthFactor, double reductionRatio) {
        this.buffer = new char[initialSize][][];
        this.initialSize = initialSize;
        this.growthFactor = growthFactor;
        this.reductionRatio = reductionRatio;
    }

    /**
     * Method retrieves the number of Braille characters that can be stored in the internal buffer (buffer space).
     *
     * @return size of buffer
     */
    @Override
    public int size() {
        return this.buffer.length;
    }

    /**
     * Method retrieves the number of characters stored within the buffer.
     *
     * @return number of Braille characters currently stored within the buffer
     */
    @Override
    public int count() {
        return this.count;
    }

    /**
     * Resizes the internal buffer to a new size and copies over {@code count} the elements into the new buffer
     * <p>Preconditions:</p>
     * 1. {@code newSize >= count}
     * @param newSize what size the internal buffer should be resized to
     */
    private void resizeBuffer(int newSize) {
        char[][][] newBuffer = new char[newSize][][];
        System.arraycopy(this.buffer, 0, newBuffer, 0, this.count);
        this.buffer = newBuffer;
    }

    /**
     * Method expands the internal buffer to accommodate additional Braille characters
     * when buffer is full. Size of the buffer is increased based on the growth factor specified during the construction
     * of the object.
     *
     * <p>Increasing the buffer preserves the existing Braille characters and ensures that their order remains unchanged.
     * The new buffer size is computed as: {@code newSize = (int)Math.ceil(currentSize * growthFactor)}</p>
     *
     * <p>Preconditions:<br>
     * 1. {@code growthFactor > 1}, as guaranteed by the constructor.</p>
     * <p>Postconditions:<br>
     * 1. The buffer is guaranteed to have enough space to store at least one more character after expansion.<br>
     * 2. The contents of the buffer prior to expansion remain intact in their original indices.<br></p>
     */
    @Override
    public void expandBuffer() {
        if (this.count < this.size()) return;

        int newSize = (int)Math.ceil(this.size() * this.growthFactor);
        this.resizeBuffer(newSize);
    }

    /**
     * Method reduces the size of the internal buffer in order to preserve memory.
     *
     * <p>Reduction is only done, if the buffer's actual usage ratio is lower than the predefined value,
     * specified at construction time.</p>
     * <p>The new size of the buffer is computed as {@code newSize = Math.ceil(count() * growthFactor)}.
     * The method ensures that the buffer's capacity remains greater than or equal to the initial size
     * defined at time of object construction.</p>
     *
     * <p>Preconditions:<br>
     * 1. The actual usage ratio (actual number of characters stored in buffer divided by buffer size) is equal or lower
     * than the reduction ratio provided at construction time.</p>
     *
     * <p>Postconditions:<br>
     * 1. If the computed new buffer size is smaller than its current size but greater than or equal
     * to the initial size, the buffer is resized and its current contents are preserved.<BR>
     * 2. If resizing is not feasible due to size constraints, the buffer size remains unchanged.</p>
     */
    @Override
    public void reduceBuffer() {
        if (this.count >= this.size() * this.reductionRatio) return;

        int newSize = (int)Math.ceil(this.count * this.growthFactor);
        if (newSize < this.initialSize || newSize >= this.size()) return; // initial size lower bound and current size upper bound
        this.resizeBuffer(newSize);
    }

    /**
     * Method pushes (appends) a bitmap representation of a Braille character to the end of the internal buffer.
     *
     * <p>If the buffer is full, it expands its size before appending the new bitmap.</p>
     *
     * @param bitmap the bitmap representation of a Braille character to be appended to the buffer for details).
     *               Precondition: ( bitmap != null )
     */
    @Override
    public void push(char[][] bitmap) {
        this.expandBuffer();
        this.buffer[this.count] = bitmap;
        this.count++;
    }

    /**
     * Method retrieves the last bitmap of a Braille character from the internal buffer. If the buffer is empty,
     * {@code null} is returned instead. If a bitmap was found, it is removed from the buffer and the buffer might be shrun.
     *
     * @return {@code null} if buffer is empty, or the bitmap at the end of the buffer.
     */
    @Override
    public char[][] pop() {
        if (this.count == 0) return null;

        this.count--;
        char[][] lastBitmap = this.buffer[this.count];
        this.buffer[this.count] = null;
        this.reduceBuffer();
        return lastBitmap;
    }

    /**
     * Method inserts a bitmap representation of a Braille character into the internal buffer at specified position by
     * shifting all existing characters at and right of the specified position to the right (next index).
     *
     * <p>If the buffer is full, it expands its size before inserting the new bitmap.</p>
     *
     * @param cursorPosition the index at which the character has to be inserted.
     *                       If the position is invalid (negative or outside the range of stored characters),
     *                       no insertion is performed.
     * @param bitmap         the bitmap representation of a Braille character to be appended to the buffer.
     *                       Precondition: ( bitmap != null )
     * @return {@code true} if the character was successfully inserted,
     * {@code false} otherwise (e.g., invalid position or empty buffer).
     */
    @Override
    public boolean insert(int cursorPosition, char[][] bitmap) {
        if (cursorPosition < 0 || cursorPosition >= this.count) return false;

        this.expandBuffer();
        System.arraycopy(this.buffer, cursorPosition, this.buffer, cursorPosition + 1, this.count - cursorPosition);
        this.buffer[cursorPosition] = bitmap;
        this.count++;
        return true;
    }

    /**
     * Method deletes a Braille character at the specified position in the buffer.
     * Shifts the characters located after the specified position one step to the left.
     *
     * <p>Checks if buffer can be reduced after successful deletion of a character.</p>
     *
     * @param cursorPosition the index of the character to be deleted.
     *                       If the position is invalid (negative or outside the range of stored characters),
     *                       no deletion is performed.
     * @return {@code true} if the character was successfully deleted,
     * {@code false} otherwise (e.g., invalid position or empty buffer).
     */
    @Override
    public boolean delete(int cursorPosition) {
        if (cursorPosition < 0 || cursorPosition >= this.count) return false;

        this.count--;
        System.arraycopy(this.buffer, cursorPosition + 1, this.buffer, cursorPosition, this.count - cursorPosition);
        this.buffer[this.count] = null;
        this.reduceBuffer();
        return true;
    }

    /**
     * Method clears the content of the buffer.
     * <p>The buffer is reinitialized with its initial size, while the count of currently stored characters
     * is reset to zero.</p>
     */
    @Override
    public void clearBuffer() {
        this.buffer = new char[this.initialSize][][];
        this.count = 0;
    }

    /**
     * Renders the contents of the buffer into an array of strings, each representing a scanline.
     * Characters in the buffer are aligned horizontally with spacing between them.
     *
     * @param spacing the number of spaces (ASCII code 32) to insert between consecutive characters from the buffer
     *                in the rendered output.
     *                Precondition: (spacing >= 0)
     * @return an array of strings, where each string represents a scanline of the combined buffered characters.
     * Returns {@code null} if the buffer is empty or an error occurs during rendering (e.g. invalid font height).
     */
    @Override
    public String[] renderScanlines(int spacing) {
        if (this.count == 0) return null;

        int height = this.buffer[0].length; // assume all bitmaps have the same size (not what I would do, but currently AdvancedLinePrinter.font is final)
        StringBuilder[] lines = new StringBuilder[height];
        for (int i = 0; i < height; i++) {
            lines[i] = new StringBuilder();
        }

        String renderedSpacing = " ".repeat(spacing);
        for (int i = 0; i < this.count; i++) {
            if (this.buffer[i].length != height) return null;

            for (int row = 0; row < height; row++) {
                lines[row].append(this.buffer[i][row]);
                if (i != this.count - 1)
                    lines[row].append(renderedSpacing);
            }
        }

        String[] result = new String[height];
        for (int row = 0; row < height; row++) {
            result[row] = lines[row].toString();
        }

        return result;
    }

    /**
     * DO NOT USE FOR YOUR IMPLEMENTATION
     * This method is required for submission testing.
     */
    public char[][][] cloneBuffer() {
        // DO NOT EDIT
        char[][][] clone = new char[buffer.length][][];

        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] == null) continue;
            clone[i] = new char[buffer[i].length][];
            for (int j = 0; j < buffer[i].length; j++) {
                if (buffer[i][j] == null) continue;
                clone[i][j] = new char[buffer[i][j].length];
                System.arraycopy(buffer[i][j], 0, clone[i][j], 0, buffer[i][j].length);
            }
        }
        return clone;
    }
}
