package dev._2lstudios.advancedparties.utils;

public class ArrayUtils {
  public static String[] removeFirstElement(String[] arr) {
      String newArr[] = new String[arr.length - 1];
      for (int i = 1; i < arr.length; i++) {
          newArr[i-1] = arr[i];
      }
      return newArr;
  }
}