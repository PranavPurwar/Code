package io.github.rosemoe.editor.widget;

import io.github.rosemoe.editor.text.LineRemoveListener;
import io.github.rosemoe.editor.text.ContentListener;

/**
 * Layout is a manager class for editor to display text
 * Different layout may display texts in different way
 * Implementations of this interface should manage 'row's in editor.
 *
 * @author Rose
 */
interface Layout extends LineRemoveListener, ContentListener {

    /**
     * Called by editor to destroy this layout
     * This means the layout will never be used again
     */
    void destroyLayout();

    /**
     * Get line index of a row in layout
     *
     * @param row The row index in layout
     * @return Line index in text
     */
    int getLineNumberForRow(int row);

    /**
     * Return a {@link RowIterator} object for editor to draw text rows
     *
     * @param initialRow The first row in result iterator
     * @return Iterator contains rows
     */
    RowIterator obtainRowIterator(int initialRow);

    /**
     * Get the width of this layout
     * Editor will use this to compute scroll range
     *
     * @return Width of layout
     */
    int getLayoutWidth();

    /**
     * Get the height of this layout
     * Editor will use this to compute scroll range
     *
     * @return Height of layout
     */
    int getLayoutHeight();

    /**
     * Get character line and column for offsets in layout
     *
     * @param xOffset Horizontal offset on layout
     * @param yOffset Vertical offset on layout
     * @return Packed IntPair, first is line and second is column
     * @see io.github.rosemoe.editor.util.IntPair
     */
    long getCharPositionForLayoutOffset(float xOffset, float yOffset);

    /**
     * Get layout offset of a position in text
     *
     * @param line   The line index
     * @param column Column on line
     * @return An array contains layout offset, first element is the bottom of character and second element is the left of character
     */
    float[] getCharLayoutOffset(int line, int column);

}
