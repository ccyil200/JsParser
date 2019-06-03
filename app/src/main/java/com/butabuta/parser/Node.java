package com.butabuta.parser;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liangjiongsheng
 */
public class Node {
    public String val;
    public Node pre;
    public Node next;
    public Node child;
    public Node parent;
    public IVariable variable;
    public static int MAX_OP_PRE = 14;
    public boolean isLoop;
    public boolean isFunction;

    public static Map<String, Integer> opPre = new HashMap<>();
    static {
        opPre.put("(", 1);
        opPre.put(")", 1);
        opPre.put(".", 1);
        opPre.put("!", 2);
        opPre.put("++", 2);
        opPre.put("--", 2);
        opPre.put("*", 3);
        opPre.put("/", 3);
        opPre.put("%", 3);
        opPre.put("+", 4);
        opPre.put("-", 4);
        opPre.put("<<", 5);
        opPre.put(">>", 5);
        opPre.put("<<<", 5);
        opPre.put(">>>", 5);
        opPre.put("<", 6);
        opPre.put(">", 6);
        opPre.put("<=", 6);
        opPre.put(">=", 6);
        opPre.put("==", 7);
        opPre.put("!=", 7);
        opPre.put("&", 8);
        opPre.put("^", 9);
        opPre.put("|", 10);
        opPre.put("&&", 11);
        opPre.put("||", 12);
        opPre.put("?", 13);
        opPre.put(":", 13);
        opPre.put("=", 14);
        opPre.put("+=", 14);
        opPre.put("-=", 14);
        opPre.put("*=", 14);
        opPre.put("/=", 14);
        opPre.put("%=", 14);
        opPre.put("&=", 14);
        opPre.put("|=", 14);
        opPre.put("^=", 14);
        opPre.put("<<=", 14);
        opPre.put(">>=", 14);
        opPre.put(">>>=", 14);
    }

    public Node(String val) {
        this.val = val;
    }

    public Object getReturn() throws Throwable {
        if (child != null) {
            Object obj1 = null;
            Object returnValue = null;
            HashMap<Integer, Object> objs = new HashMap<>();
            Node point = child;
            while (true) {
                if (point == null) {
                    break;
                }

                obj1 = point.getReturn();
                objs.put(point.hashCode(), obj1);
                returnValue = obj1;

                if ("!".equals(obj1)) {
                    return !Boolean.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("++".equals(obj1)) {
                    if (point.pre != null) {
                        return (1 + Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))));
                    } else {
                        return (1 + Integer.valueOf(String.valueOf(point.next.getReturn())));
                    }
                } else if ("--".equals(obj1)) {
                    if (point.pre != null) {
                        return (Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) - 1);
                    } else {
                        return (Integer.valueOf(String.valueOf(point.next.getReturn())) - 1);
                    }
                } else if ("=".equals(String.valueOf(obj1))) {
                    Object obj2 = objs.get(point.pre.hashCode());
                    Object obj3 = point.next.getReturn();
                    if (JsParser.getInstance().isContainKey(String.valueOf(obj2))) {
                        JsParser.getInstance().setStackValue(String.valueOf(obj2), obj3);
                    }
                    return obj3;
                } else if ("*".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) * Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("/".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) / Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("+".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) + Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("-".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) - Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("%".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) % Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("*=".equals(String.valueOf(obj1))) {
                    returnValue = Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) * Integer.valueOf(String.valueOf(point.next.getReturn()));
                    if (JsParser.getInstance().isContainKey(String.valueOf(objs.get(point.pre.hashCode())))) {
                        JsParser.getInstance().setStackValue(String.valueOf(objs.get(point.pre.hashCode())), returnValue);
                    }
                } else if ("/=".equals(String.valueOf(obj1))) {
                    returnValue = Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) / Integer.valueOf(String.valueOf(point.next.getReturn()));
                    if (JsParser.getInstance().isContainKey(String.valueOf(objs.get(point.pre.hashCode())))) {
                        JsParser.getInstance().setStackValue(String.valueOf(objs.get(point.pre.hashCode())), returnValue);
                    }
                } else if ("%=".equals(String.valueOf(obj1))) {
                    returnValue = Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) % Integer.valueOf(String.valueOf(point.next.getReturn()));
                    if (JsParser.getInstance().isContainKey(String.valueOf(objs.get(point.pre.hashCode())))) {
                        JsParser.getInstance().setStackValue(String.valueOf(objs.get(point.pre.hashCode())), returnValue);
                    }
                } else if ("+=".equals(String.valueOf(obj1))) {
                    returnValue = Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) + Integer.valueOf(String.valueOf(point.next.getReturn()));
                    if (JsParser.getInstance().isContainKey(String.valueOf(objs.get(point.pre.hashCode())))) {
                        JsParser.getInstance().setStackValue(String.valueOf(objs.get(point.pre.hashCode())), returnValue);
                    }
                } else if ("-=".equals(String.valueOf(obj1))) {
                    returnValue = Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) - Integer.valueOf(String.valueOf(point.next.getReturn()));
                    if (JsParser.getInstance().isContainKey(String.valueOf(objs.get(point.pre.hashCode())))) {
                        JsParser.getInstance().setStackValue(String.valueOf(objs.get(point.pre.hashCode())), returnValue);
                    }
                } else if ("|=".equals(String.valueOf(obj1))) {
                    returnValue = Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) | Integer.valueOf(String.valueOf(point.next.getReturn()));
                    if (JsParser.getInstance().isContainKey(String.valueOf(objs.get(point.pre.hashCode())))) {
                        JsParser.getInstance().setStackValue(String.valueOf(objs.get(point.pre.hashCode())), returnValue);
                    }
                } else if ("^=".equals(String.valueOf(obj1))) {
                    returnValue = Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) ^ Integer.valueOf(String.valueOf(point.next.getReturn()));
                    if (JsParser.getInstance().isContainKey(String.valueOf(objs.get(point.pre.hashCode())))) {
                        JsParser.getInstance().setStackValue(String.valueOf(objs.get(point.pre.hashCode())), returnValue);
                    }
                } else if ("&=".equals(String.valueOf(obj1))) {
                    returnValue = Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) & Integer.valueOf(String.valueOf(point.next.getReturn()));
                    if (JsParser.getInstance().isContainKey(String.valueOf(objs.get(point.pre.hashCode())))) {
                        JsParser.getInstance().setStackValue(String.valueOf(objs.get(point.pre.hashCode())), returnValue);
                    }
                } else if (">>=".equals(String.valueOf(obj1))) {
                    returnValue = Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) >> Integer.valueOf(String.valueOf(point.next.getReturn()));
                    if (JsParser.getInstance().isContainKey(String.valueOf(objs.get(point.pre.hashCode())))) {
                        JsParser.getInstance().setStackValue(String.valueOf(objs.get(point.pre.hashCode())), returnValue);
                    }
                } else if ("<<=".equals(String.valueOf(obj1))) {
                    returnValue = Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) << Integer.valueOf(String.valueOf(point.next.getReturn()));
                    if (JsParser.getInstance().isContainKey(String.valueOf(objs.get(point.pre.hashCode())))) {
                        JsParser.getInstance().setStackValue(String.valueOf(objs.get(point.pre.hashCode())), returnValue);
                    }
                } else if (">>>=".equals(String.valueOf(obj1))) {
                    returnValue = Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) >>> Integer.valueOf(String.valueOf(point.next.getReturn()));
                    if (JsParser.getInstance().isContainKey(String.valueOf(objs.get(point.pre.hashCode())))) {
                        JsParser.getInstance().setStackValue(String.valueOf(objs.get(point.pre.hashCode())), returnValue);
                    }
                } else if (">".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) > Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("<".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) < Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("==".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))).equals(Integer.valueOf(String.valueOf(point.next.getReturn())));
                } else if (">=".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) >= Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("<=".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) <= Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("!=".equals(String.valueOf(obj1))) {
                    return !(Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))).equals(Integer.valueOf(String.valueOf(point.next.getReturn()))));
                } else if (">>".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) >> Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("<<".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) << Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if (">>>".equals(String.valueOf(obj1))) {
                    return Integer.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) >>> Integer.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("&&".equals(String.valueOf(obj1))) {
                    return Boolean.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) && Boolean.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("||".equals(String.valueOf(obj1))) {
                    return Boolean.valueOf(String.valueOf(objs.get(point.pre.hashCode()))) || Boolean.valueOf(String.valueOf(point.next.getReturn()));
                } else if ("IF".equals(String.valueOf(obj1))) {
                    if (Boolean.valueOf(String.valueOf(point.next.getReturn()))) {
                        return point.next.next.getReturn();
                    } else {
                        Node node = point.next.next.next;
                        while (node != null) {
                            if ("ELSE".equals(node.val)) {
                                if ("IF".equals(node.next.val)) {
                                    if (Boolean.valueOf(String.valueOf(node.next.next.getReturn()))) {
                                        return node.next.next.next.getReturn();
                                    }
                                    node = node.next.next;
                                } else {
                                    return node.next.getReturn();
                                }
                            }
                        }
                        return null;
                    }
                } else if ("FOR".equals(String.valueOf(obj1))) {

                } else if ("WHILE".equals(String.valueOf(obj1))) {
                    while (Boolean.valueOf(String.valueOf(point.next.getReturn()))) {
                        Node node = point.next.next.next;
                        if (node != null) {
                            node.getReturn();
                        }
                    }
                } else if ("FUNCTION".equals(String.valueOf(obj1))) {

                } else if ("var".equals(String.valueOf(obj1))) {
                    Object obj2 = point.next.getReturn();
                    JsParser.getInstance().setStackValue(String.valueOf(obj2), null);
                    return obj2;
                } else if ("break".equals(String.valueOf(obj1))) {

                } else if ("continue".equals(String.valueOf(obj1))) {

                }

                point = point.next;

            }

            return returnValue;
        } else {
            return val;
        }
    }

    public void build() throws Throwable {
        opKeyword();
        opPre1();
        opBrace();
        opSemicolon();
        opVar();
        opPre2();
        opPre3();
        opPre4();
        opPre5();
        opPre6();
        opPre7();
        opPre8();
        opPre9();
        opPre10();
        opPre11();
        opPre12();
        opPre13();
        opPre14();
//        opIf();
    }

    public void printTree(Node sub) {
        Node node = sub;
        while (node != null) {
            if (node.val != null) {
                Log.e("xxxxx", node.val);
            }
            if (node.child != null) {
                printTree(node.child);
            }
            node = node.next;
        }
    }

    public void opKeyword() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("if".equals(cur.val)) {
                    cur.val = "IF";
                    Node node = cur;
                    while (true) {
                        node = findCloseBrace(node);

                        if (node.next == null) {
                            break;
                        }

                        if (!"else".equals(node.next.val)) {
                            break;
                        } else {
                            node.next.val = "ELSE";
                            if (node.next.next != null && "if".equals(node.next.next.val)) {
                                node.next.next.val = "IF";
                            }
                        }

                        node = node.next;
                        if (node == null) {
                            break;
                        }
                    }
                    Node insertNode = new Node(null);
                    if (cur.pre == null) {
                        child = insertNode;
                    } else {
                        insertNode.pre = cur.pre;
                    }
                    cur.pre = null;
                    insertNode.next = node.next;
                    insertNode.child = cur;
                    if (node.next != null) {
                        node.next.pre = insertNode;
                    }
                    cur.parent = insertNode;
                    node.next = null;
                    cur = insertNode;
                    insertNode.build();

                    hasOp = true;
                } else if ("while".equals(cur.val)) {
                    cur.val = "WHILE";
                    Node node = cur;
                    node = findCloseBrace(node);

                    Node insertNode = new Node(null);
                    if (cur.pre == null) {
                        child = insertNode;
                    } else {
                        insertNode.pre = cur.pre;
                    }
                    cur.pre = null;
                    insertNode.next = node.next;
                    insertNode.child = cur;
                    insertNode.isLoop = true;
                    if (node.next != null) {
                        node.next.pre = insertNode;
                    }
                    cur.parent = insertNode;
                    node.next = null;
                    cur = insertNode;
                    insertNode.build();

                    hasOp = true;
                } else if ("function".equals(cur.val)) {
                    cur.val = "FUNCTION";
                    Node node = cur;
                    node = findCloseBrace(node);

                    Node insertNode = new Node(null);
                    if (cur.pre == null) {
                        child = insertNode;
                    } else {
                        insertNode.pre = cur.pre;
                    }
                    insertNode.isFunction = true;
                    cur.pre = null;
                    insertNode.next = node.next;
                    insertNode.child = cur;
                    if (node.next != null) {
                        node.next.pre = insertNode;
                    }
                    cur.parent = insertNode;
                    node.next = null;
                    cur = insertNode;
                    insertNode.build();

                    hasOp = true;
                }

                cur = cur.next;
            }

            if (!hasOp) {
                break;
            }
        }
    }

    public void opSemicolon() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            Node startNode = child;
            while (cur != null) {
                if (";".equals(cur.val)) {
                    Node insertNode = new Node(null);
                    if (startNode.pre == null) {
                        child = insertNode;
                    } else {
                        insertNode.pre = startNode.pre;
                        insertNode.pre.next = insertNode;
                    }
                    if (cur.pre != null) {
                        cur.pre.next = null;
                    }

                    startNode.pre = null;
                    insertNode.next = cur.next;
                    if (cur.next != null) {
                        cur.next.pre = insertNode;
                    }
                    insertNode.child = startNode;
                    startNode.parent = insertNode;
                    startNode = insertNode.next;
                    cur.next = null;
                    cur = insertNode;
                    insertNode.build();

                    hasOp = true;
                } else if (cur.val == null) {
                    if (cur.next != null) {
                        startNode = cur.next;
                    }
                }

                cur = cur.next;
            }

            if (!hasOp) {
                break;
            }
        }
    }

    public void opVar() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("var".equals(cur.val)) {
                    Node insertNode = new Node(null);
                    if (cur.pre != null) {
                        cur.pre.next = insertNode;
                        insertNode.pre = cur.pre;
                    } else {
                        child = insertNode;
                    }

                    insertNode.next = cur.next.next;
                    insertNode.child = cur;
                    if (cur.next.next != null) {
                        cur.next.next.pre = insertNode;
                    }
                    cur.parent = insertNode;
                    cur.next.next = null;
                    cur.pre = null;

                    cur = insertNode;

                    hasOp = true;
                }
                cur = cur.next;
            }

            if (!hasOp) {
                break;
            }
        }
    }

    public void opBrace() throws Throwable {
        while (true) {
            Node start = null;
            Node cur = child;
            int lc = 0;
            boolean hasOp = false;
            while (cur != null) {
                if ("{".equals(cur.val)) {
                    if (start == null) {
                        start = cur;
                    }
                    lc++;
//                    if (!JsParser.isSpecialWord(cur.next.val) && !JsParser.isKeyWord(cur.next.val)) {
//                        Node insertNode = new Node(null);
//                    }
                    hasOp = true;
                } else if ("}".equals(cur.val)) {
                    lc--;
                    if (lc == 0) {
                        Node insertNode = new Node(null);
                        if (start.pre == null) {
                            child = insertNode;
                        } else {
                            start.pre.next = insertNode;
                        }

                        insertNode.next = cur.next;
                        if (cur.next != null) {
                            cur.next.pre = insertNode;
                        }
                        cur.pre.next = null;
                        insertNode.child = start.next;
                        start.next.pre = null;
                        start.next.parent = insertNode;
                        insertNode.build();

                        start = null;
                    }
                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre1() throws Throwable {
        while (true) {
            Node start = null;
            Node cur = child;
            int lc = 0;
            boolean hasOp = false;
            while (cur != null) {
                if ("(".equals(cur.val)) {
                    if (start == null) {
                        start = cur;
                    }
                    lc++;
//                    if (!JsParser.isSpecialWord(cur.next.val) && !JsParser.isKeyWord(cur.next.val)) {
//                        Node insertNode = new Node(null);
//                    }
                    hasOp = true;
                } else if ("\\.".equals(cur.val)) {
                    if (JsParser.isSpecialWord(cur.next.val) || JsParser.isKeyWord(cur.next.val)) {
                        throw new Exception("语法错误");
                    }
//                    if ("(".equals(cur.next.next.val)) {
//                        //这里是函数调用的处理
//                    }
                    hasOp = true;
                } else if (")".equals(cur.val)) {
                    lc--;
                    if (lc == 0) {
                        Node insertNode = new Node(null);
                        if (start.pre == null) {
                            child = insertNode;
                        } else {
                            start.pre.next = insertNode;
                        }

                        insertNode.next = cur.next;
                        if (cur.next != null) {
                            cur.next.pre = insertNode;
                        }
                        cur.pre.next = null;
                        insertNode.child = start.next;
                        start.next.pre = null;
                        start.next.parent = insertNode;
                        insertNode.build();

                        start = null;
                    }
                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre2() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("!".equals(cur.val)) {
                    if (JsParser.isSpecialWord(cur.next.val) || JsParser.isKeyWord(cur.next.val)) {
                        throw new Exception("语法错误");
                    }
                    Node insertNode = new Node(null);
                    cur.pre.next = insertNode;
                    insertNode.next = cur.next.next;
                    insertNode.child = cur;
                    cur.parent = insertNode;
                    cur.next.next = null;
                    hasOp = true;
                } else if ("++".equals(cur.val) || "--".equals(cur.val)) {
                    if (isCommWord(cur.pre.val) && !isCommWord(cur.next.val)) {
                        Node insertNode = new Node(null);
                        cur.pre.pre.next = insertNode;
                        insertNode.next = cur.next;
                        insertNode.child = cur.pre;
                        cur.parent = insertNode;
                        cur.next = null;
                    } else if (!isCommWord(cur.pre.val) && isCommWord(cur.next.val)) {
                        Node insertNode = new Node(null);
                        cur.pre.next = insertNode;
                        insertNode.next = cur.next.next;
                        insertNode.child = cur;
                        cur.parent = insertNode;
                        cur.next = null;
                    } else {
                        throw new Exception("语法错误");
                    }
                    hasOp = true;
                }

                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre3() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("*".equals(cur.val) || "/".equals(cur.val) || "%".equals(cur.val)) {
                    Node insertNode = insertNode2(cur);
                    if (insertNode != null) {
                        cur = insertNode;
                    }

                    hasOp = true;
                }

                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre4() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("+".equals(cur.val) || "-".equals(cur.val)) {
                    Node insertNode = insertNode2(cur);
                    if (insertNode != null) {
                        cur = insertNode;
                    }

                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre5() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("<<".equals(cur.val) || ">>".equals(cur.val) || "<<<".equals(cur.val) || ">>>".equals(cur.val)) {
                    Node insertNode = insertNode2(cur);
                    if (insertNode != null) {
                        cur = insertNode;
                    }

                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre6() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("<".equals(cur.val) || ">".equals(cur.val) || "<=".equals(cur.val) || ">=".equals(cur.val)) {
                    Node insertNode = insertNode2(cur);
                    if (insertNode != null) {
                        cur = insertNode;
                    }

                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre7() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("==".equals(cur.val) || "!=".equals(cur.val)) {
                    Node insertNode = insertNode2(cur);
                    if (insertNode != null) {
                        cur = insertNode;
                    }

                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre8() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("&".equals(cur.val)) {
                    Node insertNode = insertNode2(cur);
                    if (insertNode != null) {
                        cur = insertNode;
                    }

                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre9() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("^".equals(cur.val)) {
                    Node insertNode = insertNode2(cur);
                    if (insertNode != null) {
                        cur = insertNode;
                    }

                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre10() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("|".equals(cur.val)) {
                    Node insertNode = insertNode2(cur);
                    if (insertNode != null) {
                        cur = insertNode;
                    }

                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre11() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("&&".equals(cur.val)) {
                    Node insertNode = insertNode2(cur);
                    if (insertNode != null) {
                        cur = insertNode;
                    }

                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre12() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("||".equals(cur.val)) {
                    Node insertNode = insertNode2(cur);
                    if (insertNode != null) {
                        cur = insertNode;
                    }

                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre13() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("?".equals(cur.val) && ":".equals(cur.next.next.val)) {
                    if (isCommWord(cur.pre.val) && isCommWord(cur.next.val)) {
                        Node insertNode = new Node(null);
                        cur.pre.next = insertNode;
                        insertNode.next = cur.next.next;
                        insertNode.child = cur.pre;
                        cur.pre.child = cur.next;
                        cur.pre.child.child = child.next.next;
                        cur.pre.parent = insertNode;
                        cur.next.next.next = null;
                    } else {
                        throw new Exception("语法错误");
                    }
                    hasOp = true;
                }
                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    public void opPre14() throws Throwable {
        while (true) {
            Node cur = child;
            boolean hasOp = false;
            while (cur != null) {
                if ("=".equals(cur.val) || "+=".equals(cur.val) || "-=".equals(cur.val) || "*=".equals(cur.val)
                        || "/=".equals(cur.val) || "%=".equals(cur.val) || "&=".equals(cur.val) || "|=".equals(cur.val) || "^=".equals(cur.val)
                    || "<<=".equals(cur.val) || ">>=".equals(cur.val) || ">>>=".equals(cur.val)) {
                    Node insertNode = insertNode2(cur);
                    if (insertNode != null) {
                        cur = insertNode;
                    }

                    hasOp = true;
                }

                cur = cur.next;
            }
            if (!hasOp) {
                break;
            }
        }
    }

    private Node insertNode2(Node cur) {
        Node insertNode = new Node(null);
        if (cur.pre.pre != null) {
            cur.pre.pre.next = insertNode;
            insertNode.pre = cur.pre.pre;
        }
        insertNode.next = cur.next.next;
        insertNode.child = cur.pre;
        if (cur.next.next != null) {
            cur.next.next.pre = insertNode;
        }
        cur.pre.parent = insertNode;
        cur.next.next = null;

        if (insertNode.pre == null) {
//            insertNode.parent = child.parent;
            this.child = insertNode;
        }

        return insertNode;
    }

    private boolean isCommWord(String word) {
        if (TextUtils.isEmpty(word)) {
            return false;
        }
        if (JsParser.isSpecialWord(word) || JsParser.isKeyWord(word)) {
            return false;
        }

        return true;
    }

    private Node findCloseBrace(Node in) {
        Node cur = in;
        Node start = null;
        int lc = 0;
        while (cur != null) {
            if ("{".equals(cur.val)) {
                if (start == null) {
                    start = cur;
                }
                lc++;
            } else if ("}".equals(cur.val)) {
                lc--;
                if (lc == 0) {
                    return cur;
                }
            }
            cur = cur.next;
        }

        return null;
    }

}
