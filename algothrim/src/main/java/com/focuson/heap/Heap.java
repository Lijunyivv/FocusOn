package com.focuson.heap;

import com.focuson.util.GeneratorUtil;

import java.util.Arrays;

/**
 * @author lijunyi
 * @date 2022/3/27 12:25 下午
 * @Description TODO
 */
public class Heap<T extends Comparable<T>> {

    private Comparable[] container = new Comparable[20];

    private int heapSize = 0;

    public Heap() {
    }


    public Heap(Comparable[] container) {
        this.container = container;
    }

    public Heap(int initContainerSize) {
        container = new Comparable[initContainerSize];
    }

    public static void main(String[] args) {
        int[] ints = GeneratorUtil.generateIntArr(5, 20, true);
        System.out.println(Arrays.toString(ints));

//        convertHeapByInsert(ints);
        heapSortTest(ints);
        System.out.println(Arrays.toString(ints));

    }

    private static void heapSortTest(int[] ints) {
        Heap<Integer> tHeap = new Heap<>();
        tHeap.heapSort(ints);
    }

    public void heapSort(int[] ints) {

        heapSize = 0;
        for (int i = 0; i < ints.length; i++) {
            heapSize++;
            heapInsert(i, ints);
        }

        while (heapSize > 0) {
            heapSize--;
            swap(ints, 0, heapSize);
            heapify(ints, 0, heapSize);
        }


    }

    private void heapify(int[] ints, int index, int heapSize) {

        int sonLeft = findSonLeft(index);
        while (sonLeft < heapSize) {
            int largest = sonLeft + 1 < heapSize && ints[sonLeft] < ints[sonLeft + 1] ?
                    sonLeft + 1 : sonLeft;
            if (ints[index] >= ints[largest]){
                break;
            }
            swap(ints,index,largest);
            index = largest;
            sonLeft = findSonLeft(largest);
        }
    }

    private static void convertHeapByInsert(int[] ints) {
        Heap<Integer> integerHeap = new Heap<>();
        for (int element : ints) {
            integerHeap.add(element);
        }
        System.out.println(integerHeap);
    }

    public void add(T t) {
        container[heapSize] = t;
        heapSize++;
        heapInsert(heapSize - 1, container);
    }


    public void heapInsert(int index, Comparable[] container) {
        int currentIndex = heapSize;
        heapSize++;
        while (currentIndex > 0 && biggerThanFather(currentIndex)) {
            int fatherIndex = findFatherIndex(currentIndex);
            swap(container, currentIndex, fatherIndex);
        }
    }

    public void heapInsert(int index, int[] container) {
        while (index > 0) {
            int fatherIndex = findFatherIndex(index);
            if (container[index] < container[fatherIndex]) {
                break;
            }
            swap(container, index, fatherIndex);
            index = fatherIndex;
        }
    }

    public void heapify(int index, int heapSize) {
        int sonLeftIndex = findSonLeft(index);

        while (sonLeftIndex < heapSize) {
            int largest = sonLeftIndex + 1 < heapSize &&
                    container[sonLeftIndex].compareTo(container[sonLeftIndex + 1]) < 0 ?
                    sonLeftIndex + 1 : sonLeftIndex;
            largest = container[index].compareTo(container[largest]) >= 0 ? index : largest;
            if (largest == index) {
                break;
            } else {
                swap(container, index, largest);
                index = largest;
                sonLeftIndex = findSonLeft(index);
            }
        }

    }

    private int findSonLeft(int currentIndex) {
        return currentIndex * 2 + 1;
    }

    private int findFatherIndex(int currentIndex) {
        return currentIndex == 0 ? 0 : (currentIndex - 1) / 2;
    }

    private void swap(Object[] container, int currentIndex, int targetIndex) {
        Object temp = container[currentIndex];
        container[currentIndex] = container[targetIndex];
        container[targetIndex] = temp;
    }

    private void swap(int[] container, int currentIndex, int targetIndex) {
        int temp = container[currentIndex];
        container[currentIndex] = container[targetIndex];
        container[targetIndex] = temp;
    }

    private boolean biggerThanFather(int currentIndex) {
        return container[currentIndex].compareTo(container[(currentIndex - 1) / 2]) > 0;
    }

    @Override
    public String toString() {
        return "Heap{" +
                "container=" + Arrays.toString(container) +
                ", heapSize=" + heapSize +
                '}';
    }
}
