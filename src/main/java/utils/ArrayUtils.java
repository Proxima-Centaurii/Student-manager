package utils;

public class ArrayUtils {

	public static <T extends Comparable<T>> void insertionSort(T[] arr){
        int len = arr.length;
        if(len < 2)
            return;
            
        for(int i = 1, j; i < len; i++){
            T aux = arr[i];
            for(j = i; j > 0; j--){
                if(arr[j-1].compareTo(aux) > 0)
                    arr[j] = arr[j-1];
                else{
                    arr[j] = aux;
                    break;
                }
            }
            if(j == 0)
                arr[0] = aux;
        }
    }
	
	public static void reverse(Object[] arr){
        int left, right, length, halfLength;
        length = arr.length;
        halfLength = length/2;
        Object aux;
        
        for(left = 0; left < halfLength; left++){
            right = length - 1 - left;
            aux = arr[left];
            arr[left] = arr[right];
            arr[right] = aux;
        }
    }
	
}//End of class
