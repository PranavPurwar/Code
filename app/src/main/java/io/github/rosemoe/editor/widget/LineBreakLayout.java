package io.github.rosemoe.editor.widget;

import java.util.NoSuchElementException;

import io.github.rosemoe.editor.text.Content;
import io.github.rosemoe.editor.text.ContentLine;
import io.github.rosemoe.editor.util.BinaryHeap;
import io.github.rosemoe.editor.util.IntPair;

/**
 * Layout implementation of editor
 * This layout is never broke unless there is actually a newline character
 *
 * @author Rose
 */
class LineBreakLayout extends AbstractLayout {

    private BinaryHeap widthMaintainer;

    LineBreakLayout(CodeEditor editor, Content text) {
        super(editor, text);
        measureAllLines();
    }

    private void measureAllLines() {
        if (text == null) {
            return;
        }
        widthMaintainer = new BinaryHeap();
        widthMaintainer.ensureCapacity(text.getLineCount());
        for (int i = 0; i < text.getLineCount(); i++) {
            ContentLine line = text.getLine(i);
            int width = (int) measureText(line, 0, line.length());
            line.setWidth(width);
            line.setId(widthMaintainer.push(width));
        }
    }

    private void measureLines(int startLine, int endLine) {
        if (text == null) {
            return;
        }
        while (startLine <= endLine && startLine < text.getLineCount()) {
            ContentLine line = text.getLine(startLine);
            int width = (int) measureText(line, 0, line.length());
            if (line.getId() != -1) {
                if (line.getWidth() == width) {
                    startLine++;
                    continue;
                }
                widthMaintainer.update(line.getId(), width);
                startLine++;
                continue;
            }
            line.setId(widthMaintainer.push(width));
            line.setWidth(width);
            startLine++;
        }
    }

    @Override
    public RowIterator obtainRowIterator(int initialRow) {
        return new LineBreakLayoutRowItr(initialRow);
    }

    @Override
    public void beforeReplace(Content content) {
        // Intentionally empty
    }

    @Override
    public void afterInsert(Content content, int startLine, int startColumn, int endLine, int endColumn, CharSequence insertedContent) {
        measureLines(startLine, endLine);
    }

    @Override
    public void afterDelete(Content content, int startLine, int startColumn, int endLine, int endColumn, CharSequence deletedContent) {
        measureLines(startLine, startLine);
    }

    @Override
    public void onRemove(Content content, ContentLine line) {
        widthMaintainer.remove(line.getId());
    }

    @Override
    public void destroyLayout() {
        super.destroyLayout();
        widthMaintainer = null;
    }

    @Override
    public int getLineNumberForRow(int row) {
        return Math.max(0, Math.min(row, text.getLineCount() - 1));
    }

    @Override
    public int getLayoutWidth() {
        return widthMaintainer.top();
    }

    @Override
    public int getLayoutHeight() {
        return text.getLineCount() * editor.getRowHeight();
    }

    @Override
    public long getCharPositionForLayoutOffset(float xOffset, float yOffset) {
        int lineCount = text.getLineCount();
        int line = Math.min(lineCount - 1, Math.max((int) (yOffset / editor.getRowHeight()), 0));
        ContentLine str = text.getLine(line);
        float[] res = orderedFindCharIndex(xOffset, str);
        return IntPair.pack(line, (int) res[0]);
    }

    @Override
    public float[] getCharLayoutOffset(int line, int column) {
        CharSequence sequence = text.getLine(line);
        return new float[]{
                editor.getRowHeight() * (line + 1),
                measureText(sequence, 0, column)
        };
    }

    class LineBreakLayoutRowItr implements RowIterator {

        private int currentRow;
        private final Row result;

        LineBreakLayoutRowItr(int initialRow) {
            currentRow = initialRow;
            result = new Row();
            result.isLeadingRow = true;
            result.startColumn = 0;
        }

        @Override
        public Row next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            result.lineIndex = currentRow;
            result.endColumn = text.getColumnCount(currentRow++);
            return result;
        }

        @Override
        public boolean hasNext() {
            return currentRow >= 0 && currentRow < text.getLineCount();
        }

    }

}
