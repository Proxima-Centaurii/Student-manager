import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import utils.ArrayUtils;

public class ArrayUtilsTest {

	@Test
	public void sorting_random_order() {
		Integer[] arr1 = new Integer[] {4,1,3,2,0};
		Integer[] arr2 = arr1.clone();
		
		ArrayUtils.insertionSort(arr1);
		Arrays.sort(arr2);
		
		Assert.assertArrayEquals("Sorting 1 failed",arr2, arr1);
	}
	
	@Test
	public void sorting_reverse_order() {
		Integer[] arr1 = new Integer[] {4,3,2,1,0};
		Integer[] arr2 = arr1.clone();
		
		ArrayUtils.insertionSort(arr1);
		Arrays.sort(arr2);
		
		Assert.assertArrayEquals("Sorting 2 failed",arr2, arr1);
	}
	
	@Test
	public void aleady_sorted() {
		Integer[] arr1 = new Integer[] {0,1,2,3,4};
		Integer[] arr2 = arr1.clone();
		
		ArrayUtils.insertionSort(arr1);
		Arrays.sort(arr2);
		
		Assert.assertArrayEquals("Sorting 2 failed",arr2, arr1);
	}
	
	@Test
	public void reverse_array() {
		Integer[] arr = new Integer[] {1,2,3,4,5};
		Integer[] reversed = new Integer[] {5,4,3,2,1};
		
		ArrayUtils.reverse(arr);
		
		Assert.assertArrayEquals("Sorting 1 failed",reversed, arr);
	}

}
