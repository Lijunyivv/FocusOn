package com.focuson.util;


import java.util.Arrays;
import java.util.Random;

/**
 * 描述:
 * integer generator
 *
 * @author 李俊毅
 * @create 2021-04-01 23:16
 */
public class GeneratorUtil {

    private static Random random = new Random();

    public static int[] generateIntArr(Integer amout) {
        return random.ints(amout).toArray();
    }

    public static int[] generateIntArr(Integer amout, boolean abs) {
        if (abs) {
            return Arrays.stream(random.ints(amout).toArray()).map(Math::abs).toArray();
        }
        return generateIntArr(amout);
    }
    public static int[] generateIntArr(Integer amout,Integer max, boolean abs) {
        if (abs) {
            return Arrays.stream(random.ints(amout).toArray()).map(Math::abs).map(i-> i % max).toArray();
        }
        return generateIntArr(amout);
    }



    public static int generateInt(Integer max) {
        return random.nextInt(max);
    }

}
