package com.magic.my_project.controller;

import com.alibaba.fastjson.JSON;

import java.util.Random;

/**
 * @author caodeju
 * @date 2020/8/31 下午5:12
 */
public class MySort {

    public static void main(String[] args) {

        int[] arr = new Random().ints(11, 0, 30).toArray();
        System.out.println(JSON.toJSONString(arr));
        //快速排序
        //quickSort(0, arr.length - 1, arr);
        //选择排序
        //selectionSort(arr);
        //冒泡排序
        //bubbleSort(arr);
        //插入排序
        //insertSort(arr);
        //希尔排序
        shellSort(arr);
        System.out.println(JSON.toJSONString(arr));
    }

    /**
     * 快速排序
     * 分治法思想
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

    /**
     * 选择排序
     *
     * @param arr 待排序数组
     */
    public static void selectionSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            int k = i;
            //找出最小数的下标
            for (int j = k + 1; j < arr.length; j++) {
                if (arr[j] < arr[k]) {
                    k = j;
                }
            }
            //内循环结束表示找出了最小数的下标
            if (i != k) {
                int temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
            }
        }

    }

    /**
     * 冒泡排序
     *
     * @param arr 要排序数组
     */
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    /**
     * 插入排序
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {

            for (int k = i; k > 0 && arr[k] < arr[k - 1]; k--) {
                int temp = arr[k];
                arr[k] = arr[k - 1];
                arr[k - 1] = temp;
            }

            /*int j = i;
            while (j > 0) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    j--;
                } else {
                    break;
                }
            }*/
        }
    }

    /**
     * 希尔排序
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {
        int length = arr.length;
        //gap为步长，每次减为原来的一般
        for (int gap = length / 2; gap > 0; gap /= 2) {
            //共gap组，对每一组进行直接插入排序
            for (int i = 0; i < gap; i++) {
                //如果a[j] < a[j-gap]，则寻找a[j]位置，并将后面数据的位置都后移。
                for (int j = i + gap; j < length; j += gap) {
                    if (arr[j] < arr[j - gap]) {
                        int temp = arr[j];
                        int k = j - gap;
                        while (k >= 0 && arr[k] > temp) {
                            arr[k + gap] = arr[k];
                            k -= gap;
                        }
                        arr[k + gap] = temp;
                    }

                }
            }
        }
    }

}
