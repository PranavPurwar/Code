package io.github.rosemoe.editor.interfaces;

/**
 * Language for editor
 * <p>
 * A Language helps editor to highlight text and provide auto-completion.
 * Implement this interface when you want to add new language support for editor.
 *
 * <strong>NOTE:</strong> A language must not be single instance.
 * One language instance should always serves for only one editor.
 * It means that you should not give a language object to other editor instances
 * after it has been applied to one editor.
 * This is to provide better connection between auto completion provider and code analyzer.
 *
 * @author Rose
 */
public interface EditorLanguage {

    /**
     * Get CodeAnalyzer of this language object
     *
     * @return CodeAnalyzer
     */
    CodeAnalyzer getAnalyzer();

    /**
     * Get AutoCompleteProvider of this language object
     *
     * @return AutoCompleteProvider
     */
    AutoCompleteProvider getAutoCompleteProvider();

    /**
     * Called by editor to check whether this is a character for auto completion
     *
     * @param ch Character to check
     * @return Whether is character for auto completion
     */
    boolean isAutoCompleteChar(char ch);

    /**
     * Get advance for indent
     *
     * @param content Content of a line
     * @return Advance space count
     */
    int getIndentAdvance(String content);

    /**
     * Whether use tab to format
     *
     * @return Whether use tab
     */
    boolean useTab();

    /**
     * Format the given content
     *
     * @param text Content to format
     * @return Formatted code
     */
    CharSequence format(CharSequence text);

}
