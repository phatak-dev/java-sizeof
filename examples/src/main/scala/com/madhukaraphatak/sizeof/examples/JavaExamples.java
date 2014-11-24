package com.madhukaraphatak.sizeof.examples;

import com.madhukaraphatak.sizeof.SizeEstimator;
import scala.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * This examples shows how you can use library inside java code
 */
public class JavaExamples {

    public static void main(String[] args) {
        //call for simple strings
        System.out.println("size of character a is" +SizeEstimator.estimate('a'));
        System.out.println("size of string hello is " +SizeEstimator.estimate("hello"));

        //array
        int[] array = {10,20,30};
        System.out.println("size of array is " +SizeEstimator.estimate(array));

        //collection
        System.out.println("size of empty arraylist is " +SizeEstimator.estimate(new ArrayList<String>()));
        List<Integer> values = new ArrayList<Integer>();
        values.add(10);
        values.add(20);
        values.add(30);
        System.out.println("size of values is" +SizeEstimator.estimate(values));


    }
}
