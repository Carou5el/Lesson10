package kulkov.examples;

public class Main {

    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;
    static float[] arr;
    static float[] arr2;

    public static void main(String[] args) {
        method1();
        method2();
    }

    public static void method1()    {

        // Создание 1-мерного массива.
        arr = new float[SIZE];
        // Заполнение массива '1'.
        for (int i = 0; i < arr.length; i++) {
            arr[i]  = 1;
        }
        long a = System.currentTimeMillis();
        // Замена значений в массиве.
        for (int i = 0; i < arr.length; i++) {
            arr[i]  = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long b  = System.currentTimeMillis();
        System.out.printf("Execution time (single thread): %d\n", (b - a));
    }

    public static void method2()    {
        arr2 = new float[SIZE];

        float[] arr2Thd1 = new float[HALF];
        float[] arr2Thd2 = new float[HALF];

        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, arr2Thd1, 0, HALF);
        System.arraycopy(arr, HALF, arr2Thd2, 0, HALF);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < arr2Thd1.length; i++) {
                arr2Thd1[i]  = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < arr2Thd2.length; i++) {
                arr2Thd2[i]  = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        thread2.start();

        System.arraycopy(arr2Thd1, 0, arr2, 0, HALF);
        System.arraycopy(arr2Thd2, 0, arr2, HALF, HALF);

        long b  = System.currentTimeMillis();
        System.out.printf("Execution time (multithread): %d\n", (b - a));
    }
}
