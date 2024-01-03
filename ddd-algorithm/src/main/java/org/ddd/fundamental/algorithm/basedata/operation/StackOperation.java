package org.ddd.fundamental.algorithm.basedata.operation;

import java.util.*;
import java.util.stream.Collectors;

public class StackOperation {

    private static Set<String> operator = new HashSet<>();
    static {
        operator.add("&");
        operator.add("!");
        operator.add("|");
    }

    private static Set<String> brankets = new HashSet<>();
    static {
        brankets.add("(");
        brankets.add(")");
    }

    private static Set<String> numbers = new HashSet<>();
    static {
        numbers.add("0");
        numbers.add("1");
    }

    private static Map<String,Integer> priority = new HashMap<>();
    static {
        priority.put("(",0);
        priority.put("!",1);
        priority.put("&",2);
        priority.put("|",3);
    }

    public static int calculateValueFromString(String source) {
        return 0;
    }

    public static String midToLast(String source) {
        int length = source.length();
        Stack<String> operatorStack = new Stack<>(length,String.class);
        Stack<String> dataStack = new Stack<>(length, String.class);
        String[] arrays = source.split("");
        for (int i = 0 ; i< length; i++) {
            String temp = arrays[i];
            if (isNumber(temp)) {
                //如数据栈
                dataStack.push(temp);
            }
            if ("(".equals(temp)) {
                operatorStack.push(temp);
            }
            if (isOperator(temp)) {
                if (operatorStack.isEmpty() || (!operatorStack.isEmpty() && operatorStack.peek().equals("("))) {
                    operatorStack.push(temp);
                } else {
                    //获取操作栈顶元素
                    String topEle = operatorStack.peek();

                    boolean priority = priority(temp,topEle);
                    //当前操作符比栈顶操作符的优先级大
                    if (priority) {
                        operatorStack.push(temp);
                    } else {
                        dataStack.push(operatorStack.pop());
                        while (priority==false) {
                            if (operatorStack.isEmpty()) {
                                operatorStack.push(temp);
                                break;
                            }
                            topEle = operatorStack.peek();
                            if ("(".equals(topEle)) {
                                operatorStack.push(temp);
                                break;
                            }
                            priority = priority(temp,topEle);
                            if (!priority) {
                                dataStack.push(operatorStack.pop());
                            } else {
                                operatorStack.push(temp);
                            }
                        }
                    }
                }
            }
            if (")".equals(temp)) {
                while (!operatorStack.peek().equals("(") ){
                    dataStack.push(operatorStack.pop());
                }
                operatorStack.pop();
            }
        }
        while (!operatorStack.isEmpty()) {
            dataStack.push(operatorStack.pop());
        }
        String[] str = dataStack.getArrays();
        for (int i = 0 ; i< length; i++) {
            String topE  = str[i];
            if (isOperator(topE)) {
                if ("!".equals(topE)) {
                    String data = operatorStack.pop();
                    String result = data.equals("0")?"1":"0";
                    operatorStack.push(result);
                }
                if ("&".equals(topE)) {
                    String left = operatorStack.pop();
                    String right = operatorStack.pop();
                    if (left.equals("1") && right.equals("1")) {
                        operatorStack.push("1");
                    } else {
                        operatorStack.push("0");
                    }
                }
                if ("|".equals(topE)) {
                    String left = operatorStack.pop();
                    String right = operatorStack.pop();
                    if (left.equals("0") && right.equals("0")) {
                        operatorStack.push("0");
                    } else {
                        operatorStack.push("1");
                    }
                }
            } else {
                operatorStack.push(topE);
            }
        }

//        Stack<String> calculate = new Stack<>(length,String.class);
//        while (!dataStack.isEmpty()) {
//            calculate.push(dataStack.pop());
//        }
//
//
//        //计算值
//        while (!calculate.isEmpty()) {
//            String topE  = calculate.pop();
//            if (isOperator(topE)) {
//                if ("!".equals(topE)) {
//                    String data = operatorStack.pop();
//                    String result = data=="0"?"1":"0";
//                    operatorStack.push(result);
//                }
//                if ("&".equals(topE)) {
//                    String left = operatorStack.pop();
//                    String right = operatorStack.pop();
//                    if (left.equals("1") && right.equals("1")) {
//                        operatorStack.push("1");
//                    } else {
//                        operatorStack.push("0");
//                    }
//                }
//                if ("|".equals(topE)) {
//                    String left = operatorStack.pop();
//                    String right = operatorStack.pop();
//                    if (left.equals("0") && right.equals("0")) {
//                        operatorStack.push("0");
//                    } else {
//                        operatorStack.push("1");
//                    }
//                }
//            } else {
//                operatorStack.push(topE);
//            }
//        }

        System.out.println(Arrays.toString(dataStack.getArrays()));
        List<String> list = new ArrayList<>();
        String[] strArr = dataStack.getArrays();
        for (int i = 0 ; i< length; i++) {
            if (null!= strArr[i]) {
                list.add(strArr[i]);
            }

        }
        return list.stream().collect(Collectors.joining());
    }

    private static boolean isOperator(String c) {
        return operator.contains(c);
    }

    private static boolean isBranket(String c) {
        return brankets.contains(c);
    }

    private static boolean isNumber(String c) {
        return numbers.contains(c);
    }

    private static boolean priority(String a,String b) {
        return priority.get(a).compareTo(priority.get(b)) < 0;
    }
}
