package org.ddd.fundamental.algorithm.basedata.acm;

import java.util.Scanner;

public class AcmMain1 {
    public static void main(String[] args) {
        System.out.println("Enter some numbers to add: ");
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        //第二个读取的是换行符实际上是一个空格
        String secondLine = scanner.nextLine();
        String thirdLine = scanner.nextLine();
        System.out.println(firstLine+"-"+thirdLine);
    }
}
