package com.shub.scheduler;

import org.quartz.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by Shubhanshuc on 24/06/17.
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class PrintNameJob implements Job{

    public static final String NAME = "name";
    public static final String COUNT = "count";
    private String flag = "new object";
    //@Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        //fetch parameters from JobDataMap
        String name = dataMap.getString(NAME);
        int count = dataMap.getInt(COUNT);
        JobKey jobKey = context.getJobDetail().getKey();
        System.out.println(jobKey+": "+ name+"-"+count+": flag="+flag);

        RestTemplate restTemplate = new RestTemplate();

        //String consumeJSONString = restTemplate.getForObject("http://localhost:8080/hello", String.class);
        Long f = 8L;
        String consumeJSONString = restTemplate.postForObject("http://localhost:8080/hello2", f, String.class);

        System.out.println("JSON String: "+consumeJSONString);

        System.out.println("Date - Time: "+ new Date(java.util.Calendar.getInstance().getTimeInMillis()));

        count++;
        //add next counter to JobDataMap
        dataMap.put(COUNT, count);
        flag= "object changed";

    }

}
