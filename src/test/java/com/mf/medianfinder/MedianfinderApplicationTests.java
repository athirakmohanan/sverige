package com.mf.medianfinder;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedianfinderApplicationTests {

	@Test
	public void testMedianFinder() {
		//input.json file has 6 json responses whose median value of currency SEK is 10.4025
		double expectedValue=10.4025;
		List<Exchange> exchangeList=new ArrayList<Exchange>();		
		ObjectMapper objectMapper=new ObjectMapper();
		try {
			//Reading input.json file from classpath to test the functionality
			URL url = getClass().getResource("/input.json");
			File file = new File(url.getFile());
			exchangeList=objectMapper.readValue(file, new TypeReference<List<Exchange>>() {});
			//Convert list to array to iterate each record
			Exchange[] exchangeObjectArray=(Exchange[]) exchangeList.toArray(new Exchange[exchangeList.size()]);
    		//
    		double calculatedValue=new CalculateMedian().findMedian(exchangeObjectArray);
    		
    		assertEquals(expectedValue, calculatedValue,4);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(exchangeList);
	}

}
