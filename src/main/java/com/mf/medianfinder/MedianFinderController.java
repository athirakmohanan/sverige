package com.mf.medianfinder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MedianFinderController {
	
	@Autowired
	MedianFinderService medianFinderService;
	
	@RequestMapping(value = "/medianOfCurrency", method = RequestMethod.GET)
	public String index(Model model) {
		
		Date currentdate=new Date();
    	Calendar calendar=Calendar.getInstance();
    	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    	calendar.setTime(currentdate);
    	calendar.add(Calendar.YEAR, -1);
    	calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
    	String prevYearStartDate=dateFormat.format(calendar.getTime());    	
    	calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
    	String prevYearEndDate=dateFormat.format(calendar.getTime());
    	
		double medianValue=medianFinderService.findMedian(prevYearStartDate,prevYearEndDate);
		String message = "Welcome Reviewer !!! Below is the median of exchange rates for USD to SEK for last year:";

		model.addAttribute("message", message);
		model.addAttribute("medianValue", medianValue);

		return "index";
	}
}
