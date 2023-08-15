package org.ddd.fundamental.algorithms.basedata.linklist;

import org.ddd.fundamental.algorithm.basedata.linklist.LinkList;
import org.ddd.fundamental.algorithm.basedata.linklist.LinkNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class LinkListTest {

    private LinkList init() {
        LinkList<String> linkList = new LinkList();
        linkList.addData("yun900000");
        linkList.addData("yun900100");
        linkList.addData("yun900200");
        return linkList;
    }

    private LinkList<Integer> initIntegerLinkList() {
        LinkList<Integer> linkList = new LinkList();
        linkList.addData(8);
        linkList.addData(5);
        linkList.addData(0);
        return linkList;
    }

    @Test
    public void testAddData() {
        LinkList linkList = init();
        Assert.assertEquals("yun900200",
                linkList.getHead().getData());
    }

    @Test
    public void testForIterator() {
        LinkList linkList = init();
        List<String> result = linkList.forIterator();
        String expect = "yun900200->yun900100->yun900000";
        String actual = result.stream().collect(Collectors.joining("->"));
        Assert.assertEquals(expect,actual);
    }

    @Test
    public void testRecursivePre() {
        LinkList linkList = init();
        List<String> result = linkList.recursivePre();
        String expect = "yun900200->yun900100->yun900000";
        String actual = result.stream().collect(Collectors.joining("->"));
        Assert.assertEquals(expect,actual);
    }

    @Test
    public void testRecursivePost() {
        LinkList linkList = init();
        List<String> result = linkList.recursivePost();
        String expect = "yun900000->yun900100->yun900200";
        String actual = result.stream().collect(Collectors.joining("->"));
        Assert.assertEquals(expect,actual);
    }

    @Test
    public void testFindPreOfCur() {
        LinkList<Integer> linkList = initIntegerLinkList();
        LinkNode<Integer> preNode = linkList.findPreOfCur(4);
        Assert.assertEquals(0,preNode.getData(),2);

        preNode = linkList.findPreOfCur(6);
        Assert.assertEquals(5,preNode.getData(),2);

        preNode = linkList.findPreOfCur(0);
        Assert.assertEquals(null,preNode);
        preNode = linkList.findPreOfCur(5);
        Assert.assertEquals(0,preNode.getData(),2);
     }

     @Test
     public void testAddSortData() {
         LinkList<Integer> linkList = initIntegerLinkList();
         linkList.addSortData(6);
         linkList.addSortData(-1);
         linkList.addSortData(0);
         linkList.addSortData(20);
         List<Integer> result = linkList.forIterator();
         Integer[] expect = new Integer[]{-1, 0, 0, 5, 6, 8, 20};
         Assert.assertArrayEquals(expect,result.toArray());
     }

    @Test
     public void testMergeLinkList() {
        LinkList<Integer> linkList1 = new LinkList<>();
        linkList1.addSortData(2);
        linkList1.addSortData(5);
        linkList1.addSortData(8);
        LinkList<Integer> linkList2 = new LinkList<>();
        linkList2.addSortData(1);
        linkList2.addSortData(4);
        linkList2.addSortData(7);
        linkList2.addSortData(9);
        LinkNode<Integer> head = linkList1.mergeLinkList(linkList1.getHead(),linkList2.getHead());
        LinkList<Integer> mergeList = new LinkList(head.getNext());
        List<Integer> result = mergeList.forIterator();
        Integer[] expect = new Integer[]{1, 2, 4, 5, 7,8,9 };
        Assert.assertArrayEquals(expect,result.toArray());
    }

    @Test
    public void testFindLastK() {
        LinkList<Integer> linkList1 = new LinkList<>();
        linkList1.addSortData(2);
        linkList1.addSortData(5);
        linkList1.addSortData(8);
        linkList1.addSortData(9);
        linkList1.addSortData(10);
        linkList1.addSortData(12);
        Integer actual = linkList1.findLastK(3).getData();
        Assert.assertEquals(9,actual,0);
        Assert.assertNull(linkList1.findLastK(0));
        Assert.assertNull(linkList1.findLastK(-5));
        Assert.assertNull(linkList1.findLastK(7));
        Assert.assertNull(linkList1.findLastK(11));
        actual = linkList1.findLastK(6).getData();
        Assert.assertEquals(2,actual,0);
    }

}
