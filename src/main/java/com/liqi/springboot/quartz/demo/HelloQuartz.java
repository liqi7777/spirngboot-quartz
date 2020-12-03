package com.liqi.springboot.quartz.demo;

import com.alibaba.fastjson.JSONObject;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author Sky
 * create  2020-12-03 15:07
 * email sky.li@ixiaoshuidi.com
 *
 * https://blog.csdn.net/qq_39513430/article/details/103944567
 */
public class HelloQuartz implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail detail = context.getJobDetail();
        //获取工作的名称
        JobDataMap jobDataMap = detail.getJobDataMap();
        String name = detail.getJobDataMap().getString("name");
        System.out.println("工作名：" + name + "---->今日整点抢购，不容错过！");


    }

    public static void main(String[] args) throws Exception {
        //创建scheduler，执行计划
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //定义一个Trigger,触发条件类
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1).repeatForever()).build();
        JobDetail job = JobBuilder.newJob(HelloQuartz.class)
                .withIdentity("job1", "group1")
                .usingJobData("name", "quartz")
                .build();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}
