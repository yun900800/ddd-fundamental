package org.ddd.fundamental.algorithm.basedata.acm;

import java.util.Arrays;
import java.util.Scanner;

public class AcmMain {
    public static void main(String[] args) {
        System.out.println("请输入:");
        Scanner scanner = new Scanner(System.in);
        int number1 = scanner.nextInt();
        int number2 = scanner.nextInt();
        int[] num1 =new int[number1];
        int[] num2 =new int[number2];
        for (int i = 0 ; i<number1;i++) {
            num1[i] = scanner.nextInt();
        }
        for (int i = 0 ; i<number2;i++) {
            num2[i] = scanner.nextInt();
        }
        System.out.println("输出:");
        System.out.println(Arrays.toString(num1));
        System.out.println(Arrays.toString(num2));
    }
}
