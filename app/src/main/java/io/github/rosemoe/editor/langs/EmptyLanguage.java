package io.github.rosemoe.editor.langs;

import io.github.rosemoe.editor.struct.ResultItem;
import io.github.rosemoe.editor.text.TextAnalyzer.AnalyzeThread.Delegate;
import io.github.rosemoe.editor.interfaces.CodeAnalyzer;
import io.github.rosemoe.editor.text.TextAnalyzeResult;
import io.github.rosemoe.editor.interfaces.AutoCompleteProvider;
import io.github.rosemoe.editor.interfaces.EditorLanguage;

import java.util.ArrayList;
import java.util.List;

/**
 * Empty language without any effect
 *
 * @author Rose
 */
public class EmptyLanguage implements EditorLanguage {

    @Override
    public CharSequence format(CharSequence text) {
        return text;
    }


    @Override
    public CodeAnalyzer getAnalyzer() {
        return new EmptyCodeAnalyzer();
    }

    @Override
    public AutoCompleteProvider getAutoCompleteProvider() {
        return new EmptyAutoCompleteProvider();
    }

    @Override
    public boolean isAutoCompleteChar(char ch) {
        return false;
    }

    @Override
    public int getIndentAdvance(String content) {
        return 0;
    }

    @Override
    public boolean useTab() {
        return false;
    }

    public static class EmptyAutoCompleteProvider implements AutoCompleteProvider {

        @Override
        public List<ResultItem> getAutoCompleteItems(String prefix, boolean isInCodeBlock, TextAnalyzeResult colors, int line) {
            return new ArrayList<>();
        }

    }

    private static class EmptyCodeAnalyzer implements CodeAnalyzer {

        @Override
        public void analyze(CharSequence content, TextAnalyzeResult colors, Delegate delegate) {
            colors.addNormalIfNull();
        }

    }
}

