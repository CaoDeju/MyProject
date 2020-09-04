package com.magic.my_project.controller;

import com.alibaba.fastjson.JSON;

import java.util.Random;

/**
 * @author caodeju
 * @date 2020/8/31 下午5:12
 */
public class MySort {

    public static void main(String[] args) {

        int[] arr = new Random().ints(10, 0, 15).toArray();
        System.out.println(JSON.toJSONString(arr));
        quickSort(0, arr.length - 1, arr);
        System.out.println(JSON.toJSONString(arr));
    }

    /**
     * 快速排序
     *
     * @param left  左下标
     * @param right 右下标
     * @param arr   要排序的数组
     */
    public static void quickSort(int left, int right, int[] arr) {
        if (left >= right) {
            return;
        }
        //基准数
        int temp = arr[left];
        int i = left;
        int j = right;
        while (i != j) {
            //从右边寻找小于基准数的下表
            while (arr[j] >= temp && j > i) {
                j--;
            }
            //从左边寻找大于基准数的下表
            while (arr[i] <= temp && i < j) {
                i++;
            }
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
        //基准数归位
        arr[left] = arr[i];
        arr[i] = temp;

        //继续处理左边的
        quickSort(left, i - 1, arr);
        //继续处理右边的
        quickSort(i + 1, right, arr);
    }



}
