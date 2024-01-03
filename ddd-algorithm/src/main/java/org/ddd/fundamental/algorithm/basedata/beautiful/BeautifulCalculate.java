package org.ddd.fundamental.algorithm.basedata.beautiful;

import java.util.*;

public class BeautifulCalculate {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (int i = 0 ; i < length; i++) {
            String input = scanner.nextLine();
            int ret = calculate(input);
            System.out.println(ret);
        }
    }

    /**
     * 计算漂亮度的问题转化成对字符计算个数统计
     * @param input
     * @return
     */
    private static int calculate(String input) {
        //分组统计
        Map<String,Integer> map = new HashMap();
        String[] arrs = input.split("");
        for (int i = 0 ; i < arrs.length;i++) {
            String key = arrs[i];
            if (!map.containsKey(key)) {
                map.put(key,1);
            } else {
                Integer count = map.get(key);
                map.put(key,count+1);
            }
        }
        //排序
        List<Integer> integerList = new ArrayList<>();
        integerList.addAll(map.values());
        Collections.sort(integerList,(a,b)->b-a);
        //计算
        int initValue = 26;
        int sum = 0;
        for (int j = 0 ; j < integerList.size();j++){
            int temp = integerList.get(j);
            sum +=temp* (initValue-j);
        }
        return sum;
    }


}
