package com.butabuta.parser;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author liangjiongsheng
 */
public class JsParser {

    public static String IF = "if";
    public static String ELSE = "else";
    public static String VAR = "var";
    public static String FOR = "for";
    public static String WHILE = "while";
    public static String BREAK = "break";
    public static String CONTINUE = "continue";
    public static String NEW = "new";
    public static String FUNCTION = "function";
    public static String THIS = "this";

    public static String[] keyWords = {"if", "else", "var", "for",
            "while", "break", "continue", "new", "function", "this"};
    public static String[] specialWords = {",", ";", "'", "\"", "/", "%", "<<", ">>", "|", "&", "^",
            "*", "=", "!","!=", "+", "-", "||", "<<<", ">>>", "{", "}", "[", "]", "(", ")", "++", "--", "==", "+=", "-=", "*=",
            "/=", "%=", "|=", "&=", "^=", "<<=", ">>=", "<<<=", ">>>=", ">", "<", ">=", "<=", "\\", "?", "\\."};
    private StringBuffer buff = new StringBuffer();
    private LinkedList<String> stack = new LinkedList<>();
    private LinkedList<Map<String, Object>> variableStack = new LinkedList<>();
    private int overallLevelIndex = 0;
    private static JsParser jsParser = new JsParser();

    public static JsParser getInstance() {
        return jsParser;
    }

    public void test(String input) {
        //解析出整个文档的关键词
        step1(input);
        step2(stack);
    }

    public static boolean isKeyWord(String word) {
        for (String item : keyWords) {
            if (word.equals(item)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isSpecialWord(String word) {
        for (String item : specialWords) {
            if (word.equals(item)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 解析出变量、关键词和操作符
     * @param input
     */
    private void step1(String input) {
        int index = 0;
        while(true) {
            char c = input.charAt(index);
            if (isEmptyChar(c)) {
                addToStack(buff);
            } else if (isSpecialWord(String.valueOf(c))) {
                addToStack(buff);
                buff.append(c);
                while(true) {
                    index++;
                    if (index >= input.length()) {
                        addToStack(buff);
                        break;
                    }
                    String word = buff.toString();
                    c = input.charAt(index);
                    if (isSpecialWord(word + c)) {
                        buff.append(c);
                        continue;
                    } else {
                        addToStack(buff);
                        if (isSpecialWord(String.valueOf(c))) {
                            index --;
                        } else if (!isEmptyChar(c)) {
                            buff.append(c);
                        }
                        break;
                    }
                }
            } else {
                buff.append(c);
            }

            index++;
            if (index >= input.length()) {
                addToStack(buff);
                break;
            }
        }

    }

    private void step2(LinkedList<String> stack) {
        if (stack != null) {
            int index = 0;
            int len = stack.size();
            Node cur = null;
            Node star = null;
            while (index < len) {
                String item = stack.get(index);
//                if (VAR.equals(item)) {
//
//                }
//
//                if (IF.equals(item) || WHILE.equals(item) || FOR.equals(item) || FUNCTION.equals(item)) {
//                    overallLevelIndex ++;
//                    setStackValue(null, null);
//                }
                if (cur == null) {
                    cur = new Node(item);
                    star = cur;
                } else {
                    cur.next = new Node(item);
                    cur.next.pre = cur;
                    cur = cur.next;
                }

                index ++;
            }
            try {
                Node node = new Node(null);
                node.child = star;
                node.build();
                node.printTree(node.child);
                Object reObj = node.getReturn();
                Log.e("xxxxx", "parse:"+reObj);
            } catch (Throwable e) {
                e.printStackTrace();
            }

        }
    }

    private void addToStack(StringBuffer buff) {
        String word = buff.toString();
        if (!TextUtils.isEmpty(word)) {
            stack.add(word);
            buff.setLength(0);
        }
    }

    public void setStackValue(String key, Object value) {
        int levelIndex = overallLevelIndex;
        if (TextUtils.isEmpty(key)) {
            variableStack.set(levelIndex, null);
        } else {
            Map<String, Object> map = null;
            try {
                map = variableStack.get(levelIndex);
            } catch (Exception e) {
            }
            if (map == null) {
                map = new HashMap<>();
                variableStack.add(map);
            }
            map.put(key, value);
        }

    }

    public Object getStackValue(String key) throws Exception {
        int levelIndex = overallLevelIndex;
        while (levelIndex > -1) {
            Map<String, Object> map = variableStack.get(levelIndex);
            if (map != null) {
                if (map.containsKey(key)) {
                    return map.get(key);
                }
            }
            levelIndex --;
        }

        throw new Exception("变量没有声明，请先声明再使用！");
    }

    public boolean isContainKey(String key) throws Exception {
        int levelIndex = overallLevelIndex;
        while (levelIndex > -1) {
            Map<String, Object> map = null;
            try {
                map = variableStack.get(levelIndex);
            } catch (Exception e) {
            }
            if (map != null) {
                if (map.containsKey(key)) {
                    return true;
                }
            }
            levelIndex --;
        }

        return false;
    }

    private boolean isEmptyChar(char c) {
        if (' ' == c || '\t' == c || '\r' == c || '\n' == c) {
            return true;
        }

        return false;
    }

}