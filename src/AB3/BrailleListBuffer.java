package AB3;

import AB3.Interfaces.List;
import AB3.Interfaces.ListBuffer;
import AB3.Provided.ListNode;


/**
 * The {@code BrailleListBuffer} class provides a buffer for storing and manipulating a sequence of Braille
 * characters using a linked list. Each Braille character is represented as a bitmap contained within
 * {@code ListNode} objects. The buffer allows common operations such as adding nodes, removing nodes, inserting at
 * specific positions, and rendering the buffer contents as formatted scanlines.
 */
public class BrailleListBuffer implements ListBuffer {
    private static final char SPACE_SYMBOL=' ';
    private List list;    // can store a reference to a BrailleLinkedList


    /**
     * Constructs a new {@code BrailleListBuffer} object using the provided list.
     *
     * @param list the underlying {@code List} object used for storing and manipulating Braille characters.
     *             This list is expected to contain {@code ListNode} objects, where each node represents
     *             a Braille character as a bitmap.
     */
    BrailleListBuffer(List list) {  // Hint: pass your BrailleLinkedList to this constructor
        this.list = list;
    }

    /**
     * Returns the number of elements in the underlying list buffer.
     *
     * @return the number of elements in the list.
     */
    @Override
    public int size() {
        return this.list.size();
    }

    /**
     * Adds a new Braille character, represented as a 2-dimensional character array bitmap,
     * to the end of the underlying list buffer.
     *
     * @param bitmap a 2-dimensional array of characters representing the element to be added.
     *               This bitmap corresponds to a Braille character.
     */
    @Override
    public void push(char[][] bitmap) {
        if (bitmap == null) return;
        this.list.addLast(new ListNode(bitmap));
    }

    /**
     * Removes and retrieves the last Braille character bitmap from the underlying list.
     * If the list is empty, this method returns {@code null}.
     *
     * @return a 2-dimensional character array representing the Braille bitmap of the removed element,
     * or {@code null} if the list is empty.
     */
    @Override
    public char[][] pop() {
        var last = this.list.removeLast();
        return last == null ? null : last.getBitmap();
    }

    /**
     * Inserts a new Braille character bitmap at the specified position in the underlying list buffer.
     * If the provided bitmap is null, the method does not perform any action.
     *
     * @param position the index at which the new Braille character bitmap will be inserted.
     * @param bitmap   a 2-dimensional array of characters representing the Braille character
     *                 to be inserted.
     */
    @Override
    public void insert(int position, char[][] bitmap) {
        if (bitmap == null) return;
        this.list.insert(new ListNode(bitmap), position);
    }

    /**
     * Removes and retrieves the Braille character bitmap stored at the specified position
     * in the underlying list buffer. If the position is invalid or no element exists at the
     * specified index, it returns {@code null}.
     *
     * @param position the index of the element to be removed from the list.
     * @return a 2-dimensional character array representing the Braille bitmap of the removed element,
     *         or {@code null} if the list is empty.
     */
    @Override
    public char[][] delete(int position) {
        var removed = this.list.remove(position);
        return removed == null ? null : removed.getBitmap();
    }

    /**
     * Clears the underlying list buffer.
     *
     * <p>This method invokes the {@code clear} method on the underlying list,
     * ensuring the buffer is emptied of all stored Braille character bitmaps.</p>
     */
    @Override
    public void clearBuffer() {
        this.list.clear();
    }

    /**
     * Renders the contents of the buffer into an array of strings, each representing a scanline.
     * Characters in the buffer are aligned horizontally with spacing between them.
     *
     * @param spacing the number of spaces (ASCII code 32) to insert between consecutive characters from the buffer
     *                in the rendered output.
     *                Precondition: ( spacing >= 0 )
     * @return an array of strings, where each string represents a scanline of the combined buffered characters.
     * Returns {@code null} if the buffer is empty.
     */
    @Override
    public String[] renderScanlines(int spacing) {
        if (this.list.size() == 0) return null;

        String renderedSpacing = " ".repeat(spacing);
        ListNode cursor = this.list.getFirst();
        char[][] firstBitmap = cursor.getBitmap();

        StringBuilder[] builders = new StringBuilder[firstBitmap.length];
        for (int row = 0; row < builders.length; row++) {
            builders[row] = new StringBuilder(new String(firstBitmap[row]));
        }

        while (cursor.getNext() != null) {
            cursor = cursor.getNext();
            char[][] bitmap = cursor.getBitmap();

            if (bitmap.length != builders.length) {
                this.list.clear();
                return null;
            }

            for (int row = 0; row < builders.length; row++) {
                builders[row].append(renderedSpacing);
                builders[row].append(bitmap[row]);
            }
        }

        String[] rows = new String[builders.length];
        for (int row = 0; row < rows.length; row++) {
            rows[row] = builders[row].toString();
        }

        return rows;
    }
}
