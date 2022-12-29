package com.sxl.tree;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author songxulu
 * @create 2022-12-29 17:00
 * @desc 算数表达式计算
 **/
public class Evaluate {

    public static void main(String[] args) {

        String[]  strs = {"1","+","(","(","2","+","3",")","*","(","4","*","5",")",")"};
        eval(strs);
        String[]  strs2 = {"1","+","(","5","*","(","4","*","5",")",")"};
        eval(strs2);

    }

    public static Integer eval(String[] strs) {

        Stack<String> ops = new Stack<>();
        Stack<Integer> vals = new Stack<>();
        for (String str : strs) {
            System.out.print(str);
            if (str.equals("(")) {
                continue;
            }else if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
                ops.push(str);
            } else if (str.equals(")")) {
                Integer num1 = vals.pop();
                Integer num2 = vals.pop();
                String opsPop = ops.pop();
                Integer result = 0;
                if (opsPop.equals("+") ) result = num1 + num2;
                if (opsPop.equals("-") ) result = num1 - num2;
                if (opsPop.equals("*") ) result = num1 * num2;
                if (opsPop.equals("/") ) result = num1 / num2;
                vals.push(result);
            }else {
                vals.push(Integer.parseInt(str));
            }
        }
        while (!ops.isEmpty()) {
            Integer num1 = vals.pop();
            Integer num2 = vals.pop();
            String opsPop = ops.pop();
            Integer result = 0;
            if (opsPop.equals("+") ) result = num1 + num2;
            if (opsPop.equals("-") ) result = num1 - num2;
            if (opsPop.equals("*") ) result = num1 * num2;
            if (opsPop.equals("/") ) result = num1 / num2;
            vals.push(result);
        }
        System.out.println();
        System.out.println(vals);
        return vals.pop();
    }

}
