package com.example.administrator.test;

import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String n = "11";
        for (int j = 0; j < n.length(); j++) {
            System.out.println(n.charAt(j));
        }
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testMethod() {
        long start = System.currentTimeMillis();
//        int result = digitCounts(1, Integer.MAX_VALUE/10);
        int result = nthUglyNumber2(7);
        System.out.println("cost time:" + (System.currentTimeMillis() - start) / 1000);
        System.out.println(result);
    }

    public int nthUglyNumber2(int n) {
        // write your code here
        Queue<Long> choushu = new PriorityQueue<>();
        if (n == 1)
            return 1;
        choushu.offer((long) 1);
        int[] factor = {2, 3, 5};
        for (int i = 2; i <= n; i++) {
            long num = choushu.poll();
            for (int f : factor) {
                long tmp = f * num;
                if (!choushu.contains(tmp))
                    choushu.offer(tmp);
            }
            System.out.println(MessageFormat.format("num:{0},list:{1}", num, printQueue(choushu)));
        }
        return choushu.poll().intValue();
    }

    private String printQueue(Queue<Long> queue) {
        Iterator<Long> iterator = queue.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next() + ",");
        }
        return stringBuilder.toString();
    }

    public int nthUglyNumber(int n) {
        // write your code
        if (n == 0) return 0;
        if (n == 1) return 1;
        int[] uglys = {2, 5, 3};
        int count = 1;
        for (int i = 2; ; i++) {
            int temp = i;
            for (int j = 0; j < uglys.length; j++) {
                int u = uglys[j];
                while (temp % u == 0) {
                    temp = temp / u;
                }
            }
            if (temp == 1) {
                count++;
            }
            if (count == n) {
                return i;
            }
        }
    }

    public int digitCounts(int k, int n) {
        // write your code here
        int result = 0;
        for (int i = 1; i <= n; i++) {
            String nStr = String.valueOf(i);
            for (int j = 0; j < nStr.length(); j++) {
                if (nStr.charAt(j) == Character.forDigit(k, 10)) {
                    result++;
                }
            }

        }
        return result;
    }

    @Test
    public void testHeapSort() {
        int[] nums = {1, 2, 3, 4, 5, 6, 8, 9, 10, 7};
        heapSort(nums);
        System.out.println(Arrays.toString(nums));
        System.out.println(nums[nums.length - 10]);
    }

    private void heapSort(int[] data) {
        if (data == null || data.length == 0) {
            return;
        }
        for (int i = 0; i < data.length; i++) {
            heapSort(data, data.length - i);
            swap(data, 0, data.length - 1 - i);
            System.out.println("----------------------------------");
        }
    }

    private void heapSort(int[] data, int size) {
        int lastIndex = size - 1;
        for (int i = size / 2 - 1; i >= 0; i--) {
            int parentIndex = i;
            while (parentIndex * 2 + 1 <= lastIndex) {
                int bigIndex = parentIndex * 2 + 1;
                if (bigIndex + 1 <= lastIndex && data[bigIndex] < data[bigIndex + 1]) {
                    bigIndex++;
                }
                if (data[bigIndex] > data[parentIndex]) {
                    swap(data, bigIndex, parentIndex);
                    parentIndex = bigIndex;
                } else {
                    break;
                }
            }
        }
    }

    private void swap(int[] data, int a, int b) {
        if (a == b) return;
        System.out.println(MessageFormat.format("swap a:{0}->b:{1}", a, b));
        System.out.println("before swap:" + Arrays.toString(data));
        data[a] = data[a] + data[b];
        data[b] = data[a] - data[b];
        data[a] = data[a] - data[b];
        System.out.println("after swap:" + Arrays.toString(data));
    }


    @Test
    public void testPECS() {
        List<? extends TextView> list = new ArrayList<>();
        TextView obj = list.get(0);


        List<? super Button> consumerList = new ArrayList<>();
        consumerList.add(new Button(null));
        Object object = consumerList.get(0);
    }

    @Test
    public void testRegex() {
        String txt = "ehsi sis \n anan";
        System.out.println(txt);
        System.out.println(txt.replaceAll("\\s", ""));
    }

    @Test
    public void testLengthOfLongestSubstring() {
        String str = "abcadbcd";

        HashMap<Character, Integer> map = new HashMap<>();
        int start = -1;
        int max = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (map.containsKey(c)) {
                Integer temp = map.get(c);
                if (temp >= start) {
                    start = temp;
                }
            }
            map.put(c, i);
            max = Math.max(max, i - start);
        }
        System.out.println("max length:" + max);
    }
}