package org.ddd.fundamental.algorithm.basedata.mp4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mp4Main {
    private static int initIndex = 1;

    public static void main(String[] args) {
        String opStr = "UDDUUDUDDUUDDDDDDUUDUDDUUUU";
        List<Integer> ret = batchOps(107, opStr);
        String str = ret.stream().map(v->v+"").collect(Collectors.joining(" "));
        System.out.println(str);
        System.out.println(initIndex);
    }

    private static List<Integer> initSongList(int count) {
        List<Integer> songList = new ArrayList<>();
        int index = count <= 4 ? count : 4;
        for (int i = 0 ; i < index; i++) {
            songList.add(i + 1 );
        }
        return songList;
    }
    private static List<Integer> batchOps(int count, String opStr) {
        List<Integer> initSong = initSongList(count);
        String[] ops = opStr.split("");
        for (int i = 0 ; i < ops.length; i++) {
            initSong = opList(initSong, ops[i], count);
            System.out.println(ops[i]);
            System.out.println(initSong);
            System.out.println(initIndex);
        }
        return initSong;
    }

    /**
     * 执行一个操作以后的列表
     */
    private static List<Integer> opList(List<Integer> songList, String op,
                                        int count) {
        if (count <= 4) {
            if (op.equals("U") ) {
                if (initIndex == 0) {
                    initIndex = count - 1;
                } else {
                    initIndex -= 1;
                }

            }
            if (op.equals("D") ) {
                if (initIndex == count - 1) {
                    initIndex = 0;
                } else {
                    initIndex += 1;
                }
            }
        } else {
            if (op.equals("U")) {
                if (isSpecialUpRange()) {
                    songList = specialUp(songList,count);
                    return songList;
                } else if (isUpRange(songList)) {
                    songList = up(songList);
                    return songList;
                } else {
                    initIndex = initIndex-1;
                }
            }
            if (op.equals("D")) {
                if (isSpecialDownRange(count)) {
                    songList = specialDown(songList,count);
                    return songList;
                } else if (isDownRange(songList,count)) {
                    songList = down(songList);
                    return songList;
                } else{
                    initIndex = initIndex+1;
                }
            }
        }
        return songList;
    }

    private static boolean isUpRange(List<Integer> list) {
        int top = list.get(0);
        if (top != 1) {
            return top == initIndex;
        } else {
            return false;
        }
    }

    private static List<Integer> up(List<Integer> list) {
        initIndex = initIndex-1;
        list.clear();
        for (int i = initIndex; i< initIndex+4; i++) {
            list.add(i);
        }
        return list;
    }
    private static boolean isDownRange(List<Integer> list, int count) {
        int bottom = list.get(3);
        if (bottom != count) {
            return initIndex == bottom;
        } else {
            return false;
        }
    }

    private static List<Integer> down(List<Integer> list) {
        initIndex = initIndex+1;
        list.clear();
        for (int i = initIndex-3; i<= initIndex; i++) {
            list.add(i);
        }
        return list;
    }
    private static boolean isSpecialUpRange() {
        return initIndex == 1;

    }

    private static List<Integer> specialUp(List<Integer> list, int count) {
        initIndex = count;
        list.clear();
        for (int i = initIndex -3; i<= initIndex;i++) {
            list.add(i);
        }
        return list;
    }

    private static boolean isSpecialDownRange(int count) {
        return initIndex == count;
    }

    private static List<Integer> specialDown(List<Integer> list, int count) {
        initIndex = 1;
        list.clear();
        for (int i = 0; i< 4;i++) {
            list.add(i+1);
        }
        return list;
    }
}
