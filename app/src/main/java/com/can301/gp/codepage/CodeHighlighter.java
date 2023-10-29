package com.can301.gp.codepage;

import java.util.HashMap;
import java.util.List;

import prettify.PrettifyParser;
import syntaxhighlight.ParseResult;

/**
 * NOTE: TO USE IT,
 * add implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
 * to the Module build.gradle.kts to include local jar libraries.
 * Then, place the JavaPrettify jar into the folder (app/libs/)
 * Resync Gradle and it can be used after that.
 *
 * A class that inputs a string of Java code and
 * outputs a string of HTML code that is that code syntax-highlighted
 *
 * Key Idea:
 * Use the parser from the library to parse the code, then,
 * for each token, according to its token type (e.g. identifier, keyword),
 * add color to it by surrounding it with HTML tag <font color="">.
 */
public class CodeHighlighter {
    // #1: Color code string. #2: Text to color.
    private static final String htmlColorTagPattern = "<font color=\"%s\">%s</font>";

    // Parser to parse Java code
    private final PrettifyParser codeParser = new PrettifyParser();

    // Token type string maps to color string.
    private final HashMap<String, String> tokenColorMap = new HashMap<String, String>();
    private void initTokenColorMap() {
        // Green blue for types.
        tokenColorMap.put("typ", "#2b91af");
        // Blue for keywords.
        tokenColorMap.put("kwd", "#0000ff");
        // Pure black for literals.
        tokenColorMap.put("lit", "#000000");
        // Dark green for comments.
        tokenColorMap.put("com", "#008000");
        // Brick red for strings.
        tokenColorMap.put("str", "#a31515");
        // Pure black for punctuations.
        tokenColorMap.put("pun", "#000000");
        // Pure black for plain texts.
        tokenColorMap.put("pln", "#000000");
    }

    /**
     * @return the color string for the token type, or that for plain texts,
     * if the token type is not set up in the token color map.
     */
    private String getColorString(String tokenType) {
        if (tokenColorMap.containsKey(tokenType)) {
            return tokenColorMap.get(tokenType);
        }
        return tokenColorMap.get("pln");
    }

    CodeHighlighter() {
        initTokenColorMap();
    }

    /**
     * Syntax highlight Java source code
     * @param source Java source code
     * @return Highlighted, in HTML code.
     */
    public String highlight(String source) {
        StringBuilder ret = new StringBuilder();
        List<ParseResult> results = codeParser.parse("java", source);
        for(ParseResult result : results){
            String tokenType = result.getStyleKeys().get(0);
            // Get the token string as well as the spaces, etc. around the token.
            String content = source.substring(
                    result.getOffset(), result.getOffset() + result.getLength()
            );
            // Color the tokens with the HTML tag.
            ret.append(String.format(htmlColorTagPattern, getColorString(tokenType), content));
        }
        return ret.toString();
    }
}
