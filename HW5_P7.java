import java.util.*;
import java.util.stream.Collectors;
import java.Comparator;

public class HW5_P7 {
    
    public static <E extends Comparable<E>> void insertionSort(ArrayList<E> data) {
        int  n = data.size();
        for (int k = 1; k < n; k++) {
            E key = data.get(k);
            int j = k;
            while (j > 0 && data.get(j-1).compareTo(key) > 0) {
                data.set(j, data.get(j - 1));
                j--;
            }
            data.set(j, key);
        }
    }

    private static <E extends Comparable<E>> void makeHeap(ArrayList<E> data, int size, int i) {
        int max = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && data.get(left).compareTo(data.get(max)) > 0) {
            max = left;
        }

        if (right < size && data.get(right).compareTo(data.get(max)) > 0) {
            max = right;
        }

        if (max != i) {
            E temp = data.get(i);
            data.set(i, data.get(max));
            data.set(max, temp);

            makeHeap(data, size, max);
        }
    }

    public static <E extends Comparable<E>> void heapSort(ArrayList<E> data) {
        int size = data.size();

        for (int i = size/2 -1; i >=0; i--) {
            makeHeap(data, size, i);
        }

        for (int i = size - 1; i >= 0; i--) {
            E temp = data.get(0);
            data.set(0, data.get(i));
            data.set(i, temp);

            makeHeap(data, i, 0);
        }
    }

    private static <E> void merge(ArrayList<E> left, ArrayList<E> right, ArrayList<E> data, Comparator <E> comp) {
        int i = 0; 
        int j = 0;
        while (i + j < data.size()) {
            if (j == right.size() || (i < left.size() && comp.compare(left.get(i), right.get(j)) < 0)) {
                data.set(i + j, left.get(i));
                i++;
            }
            else {
                data.set(i+j, right.get(j));
                j++;
            }
        }
    }

    public static <E> void mergeSort(ArrayList<E> data, Comparator<E> comp) {
        int n = data.size();
        if (n < 2) return;

        int mid = n/2;
        ArrayList<E> left = (ArrayList<E>) data.stream().limit(mid).collect(Collectors.toList());
        ArrayList<E> right = (ArrayList<E>) data.stream().skip(mid).limit(data.size()).collect(Collectors.toList());

        mergeSort(left, comp);
        mergeSort(right, comp);

        merge(left, right, data, comp);
    }

    public static <E> void quickSort(ArrayList <E> S, Comparator<E> comp, int a, int b) {
        if (a >= b) return;
        int left = a;
        int right = b - 1;
        E pivot  = S.get(b);
        E temp;

        while (left <= right) {
            while (left <= right && comp.compare(S.get(left), pivot) < 0) {
                 left++;
            }
            while (left <= right && comp.compare(S.get(right), pivot) > 0) {
                right--;
            }

            if (left <= right) {
                temp = S.get(left); S.set(right, S.get(right));
                S.set(right, temp);
                left++;
                right--;
            }
        }

        temp = S.get(left);
        S.set(left, S.get(b));
        S.set(b, temp);

        quickSort(S, comp, a, left-1);
        quickSort(S, comp, left+1, b);
    }
    public static void main(String[] args) {
        int input = 100_000;
        Comparator<Integer> comp = new Comparator<>() {
            @Override
            public int compare(Integer e, Integer t1) {
                return e.compareTo(t1);
            }
        }; 

        System.out.println("Beginning sort test:");
        for (int iter = 0;  iter < 10; iter++) {
            ArrayList<Integer> s = new ArrayList<>(input);
            Random r = new Random();
            for (int i = 0; i < input; i++) {
                s.add(r.nextInt(1_000_000) + 1);
            }
            ArrayList<Integer> insertArray = new ArrayList<>(s);

            long start = System.currentTimeMillis();
            insertionSort(insertArray);
            long end = System.currentTimeMillis();
            System.out.println("insertionSort " + (iter + 1) + ": " + (end - start) + "ms");
            input += 10_000;
        }

        for (int iter = 0;  iter < 10; iter++) {
            ArrayList<Integer> s = new ArrayList<>(input);
            Random r = new Random();
            for (int i = 0; i < input; i++) {
                s.add(r.nextInt(1_000_000) + 1);
            }

            ArrayList<Integer> heapArray = new ArrayList<>(s);

            long start = System.currentTimeMillis();
            heapSort(heapArray);
            long end = System.currentTimeMillis();
            System.out.println("heapSort " + (iter + 1) + ": " + (end - start) + "ms");
            input += 10_000;
        }

        for (int iter = 0;  iter < 10; iter++) {
            ArrayList<Integer> s = new ArrayList<>(input);
            Random r = new Random();
            for (int i = 0; i < input; i++) {
                s.add(r.nextInt(1_000_000) + 1);
            }

            ArrayList<Integer> quickArray = new ArrayList<>(s);

            long start = System.currentTimeMillis();
            quickSort(quickArray, comp, 0, 10_000);
            long end = System.currentTimeMillis();
            System.out.println("quickSort " + (iter + 1) + ": " + (end - start) + "ms");
            input += 10_000;
            
        }

        for (int iter = 0;  iter < 10; iter++) {
            
            ArrayList<Integer> s = new ArrayList<>(input);
            Random r = new Random();
            for (int i = 0; i < input; i++) {
                s.add(r.nextInt(1_000_000) + 1);
            }

            ArrayList<Integer> mergeArray = new ArrayList<>(s);

            long start = System.currentTimeMillis();
            mergeSort(mergeArray, comp);
            long end = System.currentTimeMillis();
            System.out.println("mergeSort " + (iter + 1) + ": " + (end - start) + "ms");
            input += 10_000;
        }
        System.out.println("End sort test.");
    }
}