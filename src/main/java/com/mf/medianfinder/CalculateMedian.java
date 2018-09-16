package com.mf.medianfinder;

public class CalculateMedian {
	
	public double findMedian(Exchange[] exchangeObjectArray) {
		
		if (exchangeObjectArray == null || exchangeObjectArray.length == 0) {
            return 0;
        }
        if (exchangeObjectArray.length == 1) {
            return exchangeObjectArray[0].getRates().get("SEK");
        }
        int exchangeArrLength = exchangeObjectArray.length;
        return resultExchangeObject(exchangeObjectArray, 0, exchangeArrLength - 1, (exchangeArrLength - 1)/ 2);
	}

	private double resultExchangeObject(Exchange[] exchangeObjectArray, int start, int end, int target) {

		int index = splitExchangeArray(exchangeObjectArray, start, end);
        if (index == target) {
        	Double median=(Double)((exchangeObjectArray[index].getRates().get("SEK")+exchangeObjectArray[index+1].getRates().get("SEK"))/2);
            return median;
        } else if (index < target) {
            return resultExchangeObject(exchangeObjectArray, index + 1, end, target);
        } else {
            return resultExchangeObject(exchangeObjectArray, start, index - 1, target);
        }
	}
	
	private int splitExchangeArray(Exchange[] exchangeObjectArray, int low, int high) {
        int pivot = high;
        Exchange pivotValue = exchangeObjectArray[pivot];

        while (low < high) {
            while (low < high && exchangeObjectArray[low].getRates().get("SEK") < pivotValue.getRates().get("SEK")) {
                low++;
            }
            while (low < high && exchangeObjectArray[high].getRates().get("SEK") >= pivotValue.getRates().get("SEK")) {
                high--;
            }
            swap(exchangeObjectArray, low, high);
        }
        
        swap(exchangeObjectArray, low, pivot);

        return low;
    }
	
	private void swap(Exchange[] exchangeObjectArray, int x, int y) {
		Exchange temp = exchangeObjectArray[x];
        exchangeObjectArray[x] = exchangeObjectArray[y];
        exchangeObjectArray[y] = temp;
    }
}
