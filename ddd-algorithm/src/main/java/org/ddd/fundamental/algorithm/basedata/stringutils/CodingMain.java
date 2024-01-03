package org.ddd.fundamental.algorithm.basedata.stringutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CodingMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<String> list = new ArrayList<>();
        String lines = scanner.nextLine();
        lines = lines.substring(1,lines.length());
        String[] arrs = lines.split("");
        boolean mode = false;
        StringBuilder sb = null;
        int k = 0;
        for (int i = 0 ; i <arrs.length; i++) {
            if ( i% 9 == 0 ) {
                mode = mode(arrs[i]);
                String temp = lines.substring(i+1,i+9);
                if(mode) {
                    temp = new StringBuilder().append(temp).reverse().toString();
                    list.add(temp);
                    k++;
                } else {
                    list.add(temp);
                    k++;
                }
            }
        }
        String str = list.stream().collect(Collectors.joining(" "));
        System.out.println(str);
    }

    /**
     * 判断编码模式
     * @param str
     * @return
     */
    private static boolean mode(String str) {
        return str.equals("0");
    }
}
