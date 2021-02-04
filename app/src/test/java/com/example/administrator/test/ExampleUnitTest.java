package com.example.administrator.test;

import android.support.annotation.IntDef;
import android.support.annotation.IntegerRes;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.test.kotlinTest.IntKObj;
import com.example.administrator.test.kotlinTest.KObj;
import com.example.administrator.test.kotlinTest.StringKObj;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE;

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

    @Test
    public void testLock() throws InterruptedException {

        Semaphore semaphore = new Semaphore(0);
        System.out.println("start time:" + new Date());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
            }
        }).start();
        semaphore.tryAcquire(2000, TimeUnit.MILLISECONDS);
        System.out.println("start time:" + new Date());
        System.out.println("ha ha");
    }

    @Test
    public void testSudoku() {
        char c = '1';

        char[][] board = {
                {'8', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

//        char[] cs = {'a', 'b', 'c'};
//        cs[2] = (char) (cs[2] + cs[0]);
//        System.out.println(isVaildSudoku(board));

    }


    public boolean isVaildSudoku(char[][] board) {
//        int hSize = board.length;
//        int vSize = board[0].length;
        for (int i = 0; i < 9; i++) {
            byte[] recordH = new byte[9];
            byte[] recordV = new byte[9];
            byte[] recordC = new byte[9];
            for (int j = 0; j < 9; j++) {
                //检查横向的
                char h = board[i][j];
                if ('.' != h) {
                    h -= '1';
                    if (recordH[h] != 0) {
                        return false;
                    }
                    recordH[h] = 1;
                }

                //检查纵向的
                char v = board[j][i];
                if ('.' != v) {
                    v -= '1';
                    if (recordV[v] != 0) {
                        return false;
                    }
                    recordV[v] = 1;
                }

                //检查宫格的
                System.out.println("i:" + i + "--j:" + j);
                int x = (i / 3) * 3 + j / 3;
                int y = (i % 3) * 3 + j % 3;
                System.out.println("x:" + x + "--y:" + y);
                char c = board[x][y];
                System.out.println(MessageFormat.format("x:{0}  y:{1}  current:{2}", x, y, c));
                if ('.' != c) {
                    c -= '1';
                    if (recordC[c] != 0) {
                        return false;
                    }
                    recordC[c] = 1;
                }
            }
            for (Map.Entry<Object, Object> entry : new HashMap<>().entrySet()) {

            }
            System.out.println(Arrays.toString(recordC));
        }

        return true;
    }


    Disposable mD = null;

    @Test
    public void testRxjava() {


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Thread.sleep(20000);
                emitter.onNext("AAAA");
            }
        }).timeout(1500, TimeUnit.MILLISECONDS)
                .doOnSubscribe(disposable -> mD = disposable)
                .map(s -> s + s)
                .subscribe(s -> System.out.println(s), throwable -> {
                    if (mD != null) {
                        System.out.println("dipose");
                        mD.dispose();
                    }
                    System.out.println(System.currentTimeMillis() + "  " + throwable);
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("onComplete");
                    }
                });

        System.out.println(System.currentTimeMillis() + "  " + "finish");
    }

    @Test
    public void testCompute() {
        int[] nums = {5, 4, 2, 1, 6, 3};
        heapSort1(nums, nums.length);
        System.out.println(Arrays.toString(nums));
    }

    public void heapSort1(int[] arr, int length) {
        if (length <= 1) return;
        int start = (length - 2) / 2;
        while (start >= 0) {
            int smallIndex = (start * 2) + 1;
            if (smallIndex + 1 < length && arr[smallIndex + 1] < arr[smallIndex]) {
                smallIndex++;
            }
            if (arr[start] > arr[smallIndex]) {
                arr[start] = arr[start] + arr[smallIndex];
                arr[smallIndex] = arr[start] - arr[smallIndex];
                arr[start] = arr[start] - arr[smallIndex];
            }
            if (start > 0) {
                start--;
            } else {
                arr[start] = arr[start] + arr[length - 1];
                arr[length - 1] = arr[start] - arr[length - 1];
                arr[start] = arr[start] - arr[length - 1];
                break;
            }
        }
        heapSort1(arr, length - 1);
    }

    @Test
    public void testCap() {
        char[][] board = {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'p', '.', '.', '.', '.'},
                {'.', '.', '.', 'R', '.', '.', '.', 'p'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'p', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
//        System.out.println(numRookCaptures(board));
//        System.out.println(hasGroupsSizeX(new int[]{1,1,1,2,2,2,3,3}));
        System.out.println(minimumLengthEncoding(new String[]{"time", "time", "time", "time"}));
    }

    public int minimumLengthEncoding(String[] words) {
        String matchStr = null;
        StringBuilder sb = new StringBuilder();
        for (String s : words) {
            sb.append(s).append("#");
        }
        matchStr = sb.toString();
        int len = matchStr.length();
        for (String s : words) {
            int index = matchStr.indexOf(s);
            if (index == -1) continue;
            while ((index = matchStr.indexOf(s, index + s.length())) != -1) {
                len -= (s.length() + 1);
            }
        }
        return len;
    }

    public boolean hasGroupsSizeX(int[] deck) {
        Map<Integer, Integer> m = new HashMap<>();
        int maxCount = 0;
        for (int n : deck) {
            if (m.containsKey(n)) {
                maxCount = Math.max(maxCount, m.get(n) + 1);
                m.put(n, m.get(n) + 1);
            } else {
                m.put(n, 1);
            }
        }
        if (m.size() <= 0 || maxCount <= 1) return false;
        for (Map.Entry<Integer, Integer> entry : m.entrySet()) {
            if (entry.getValue() < 2) return false;
            if (entry.getValue() != maxCount && (maxCount % 2 != 0 || entry.getValue() % 2 != 0))
                return false;
        }
        return true;
    }

    public int numRookCaptures(char[][] board) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 'R') {
                    count += numRookCaptures(board, i, 8, j, false);
                    count += numRookCaptures(board, i, -1, j, false);
                    count += numRookCaptures(board, j, 8, i, true);
                    count += numRookCaptures(board, j, -1, i, true);
                }
            }
        }
        return count;
    }

    private int numRookCaptures(char[][] board, int s, int e, int fixedPos, boolean vertical) {
        int add = e > s ? 1 : -1;
        int count = 0;
        for (int i = s; i != e; i += add) {
            char c = vertical ? board[fixedPos][i] : board[i][fixedPos];
            if ('B' == c) break;
            if ('p' == c) count++;
        }
        return count;
    }

    @Test
    public void testCompute2() {
//        int[] nums = {2, 5, 4, 7, 3};
//        quickSort(nums, 0, nums.length - 1);
//        System.out.println(Arrays.toString(nums));

        int[][] n = {{1, 7, 3}, {4, 5, 6}};
        int[][] m = new int[3][3];
        System.arraycopy(n, 0, m, 0, n.length);
        n[1][1] = 10;
        for (int i = 0; i < n.length; i++) {
            System.out.println(Arrays.toString(m[i]));
        }

        long l = (long) Integer.MAX_VALUE + 1;
        int num = (int) l;
        System.out.println(n + "   " + l);
        Deque<Integer> queue = new ArrayDeque<>();
    }

    private void quickSort(int[] nums, int start, int end) {
        if (start >= end) return;
        int pivot = patition(nums, start, end);
        quickSort(nums, start, pivot - 1);
        quickSort(nums, pivot + 1, end);
    }

    private int patition(int[] nums, int start, int end) {
        int pivot = start++;
        while (start <= end) {
            while (start <= end && nums[start] <= nums[pivot]) start++;
            while (start <= end && nums[end] > nums[pivot]) {
                end--;
            }
            if (start >= end) continue;
            nums[end] = nums[start] + nums[end];
            nums[start] = nums[end] - nums[start];
            nums[end] = nums[end] - nums[start];
        }
        if (pivot != end) {
            nums[end] = nums[end] + nums[pivot];
            nums[pivot] = nums[end] - nums[pivot];
            nums[end] = nums[end] - nums[pivot];
        }
        return end;
    }

    @Test
    public void testgenerateParenthesis() {

        ArrayList<String> nums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            nums.add(i + "");
        }

//        nums.forEach(new java.util.function.Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                if("1".equals(s)){
//                    nums.remove(s);
//                }
//            }
//        });
        int size = nums.size();
        for (int i = 0; i < size; i++) {
            String s = nums.get(i);
            if("1".equals(s)){
                nums.remove(s);
            }
        }
        System.out.println(nums);
    }

    List<String> res = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        dfs(n, n, "");
        return res;
    }

    private void dfs(int left, int right, String curStr) {
        if (left == 0 && right == 0) { // 左右括号都不剩余了，递归终止
            res.add(curStr);
            return;
        }

        if (left > 0) { // 如果左括号还剩余的话，可以拼接左括号
            dfs(left - 1, right, curStr + "(");
        }
        if (right > left) { // 如果右括号剩余多于左括号剩余的话，可以拼接右括号
            dfs(left, right - 1, curStr + ")");
        }
    }


    @Test
    public void testRxJava() {
        long start = System.currentTimeMillis();
        Observable<Integer> observable1 = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            emitter.onNext(10);
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.computation())
//                .timeout(1, TimeUnit.SECONDS)
                .onErrorResumeNext(new Function<Throwable, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> apply(Throwable throwable) {
                        System.out.println("observable1 error:" + throwable.getMessage());
                        return Observable.just(0);
                    }
                })
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) {
                        System.out.println("observable1 get data");
//                        Thread.sleep(5000);
                        return integer * 10;
                    }
                });

        Observable<String> observable2 = Observable.create(
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {

                        }
//                        emitter.onError(new Exception("Test Error"));
                        emitter.onNext("Android");
                        emitter.onComplete();
                    }
                }
        ).subscribeOn(Schedulers.computation())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("observable2 receive :" + s);
                    }
                }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
//                        System.out.println("observable2 receive error:"+throwable.getMessage());
                        return Observable.just(throwable.getMessage());
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.just(s + " end");
                    }
                })
//                .timeout(2, TimeUnit.SECONDS)
//                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
//                    @Override
//                    public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
////                        System.out.println("observable2 receive error:"+throwable.getMessage());
//                        return Observable.just(throwable.getMessage());
//                    }
//                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("observable2 receive second res:" + s);
                    }
                });
        Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Object lock = new Object();
                System.out.println("start prepare :"+Thread.currentThread().getName());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("prepared :"+Thread.currentThread().getName());
                        synchronized (lock){
                            lock.notify();
                        }
                    }
                }).start();
                synchronized (lock){
                    System.out.println("start wait");
                    lock.wait();
                    System.out.println("after wait");
                }
                return true;
            }
        })

       /* Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(20L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        emitter.onNext(true);
                        emitter.onComplete();
                        System.out.println("prepared");
                    }
                });
                emitter.onNext(true);
            }
        })*/.flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                       @Override
                       public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                           return Observable.zip(observable1, observable2, new BiFunction<Integer, String, Boolean>() {
                               @Override
                               public Boolean apply(Integer integer, String s) throws Exception {
                                   System.out.println("i : " + integer + " s:" + s);
                                   return true;
                               }
                           });
                       }
                   }

        ).timeout(2,TimeUnit.SECONDS)
                .blockingSubscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        System.out.println("total res:" + aBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("final error:"+throwable.getMessage());
                    }
                });
        System.out.println("total time:"+ (System.currentTimeMillis() - start) / 1000f);
    }


    @Test
    public void testKObj(){
        ArrayList<KObj> kObjs = new ArrayList<>();
        KObj<Integer> kObj = new KObj<>();
        kObj.setObj(10);
//        kObj.init(integer -> {
//            System.out.println(integer);
//            return null;
//        });

        KObj<String> kObj1 = new KObj<>();
        kObj1.setObj("Hello");
        kObjs.add(kObj);
        kObjs.add(kObj1);

        kObjs.forEach(new java.util.function.Consumer<KObj>() {
            @Override
            public void accept(KObj kObj) {
                if (kObj instanceof IntKObj) {
                    ((IntKObj)kObj).init(integer -> {
                        System.out.println("i:"+integer.byteValue());
                        return null;
                    });
                }else if (kObj instanceof StringKObj) {
                    ((StringKObj)kObj).init(s -> {
                        System.out.println("s:"+s.replace("","s"));
                        return null;
                    });
                }
//                kObj.init(new Function1() {
//                    @Override
//                    public Object invoke(Object o) {
//                        if (o instanceof String) {
//
//                        }else if(o instanceof Integer){
//
//                        }
//                        return null;
//                    }
//                });
            }
        });
    }


    @Test
    public void testFor(){
//        ArrayList<Integer> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            list.add(i);
//        }
////        for (int i = 0; i < 10; i++) {
////            if(i == 3) list.remove(3);
////        }
////        for (int i : list.iterator()){
////            if(i == 3) list.remove(3);
////        }
//        System.out.println(list);
//        System.out.println(tableSizeFor(1023));
        HashMap<Integer, Integer> map = new HashMap<>(1000);
        try {
            Method capacity = map.getClass().getDeclaredMethod("capacity",null);
            Field threshold = map.getClass().getDeclaredField("threshold");
            threshold.setAccessible(true);
            capacity.setAccessible(true);
            System.out.println("capacity:"+capacity.invoke(map));
            System.out.println("threshold:"+threshold.get(map));
            map.put(1, 1);
            System.out.println("new capacity:"+capacity.invoke(map));
            System.out.println("new threshold:"+threshold.get(map));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}