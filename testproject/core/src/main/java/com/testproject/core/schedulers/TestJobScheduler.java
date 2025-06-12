/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.testproject.core.schedulers;

import com.testproject.core.config.TestSchedulerConfig;
import org.apache.sling.commons.scheduler.Job;
import org.apache.sling.commons.scheduler.JobContext;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import org.osgi.service.metatype.annotations.Designate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple demo for cron-job like tasks that get executed regularly.
 * It also demonstrates how property values can be set. Users can
 * set the property values in /system/console/configMgr
 */
@Component(immediate = true, service = Job.class)
@Designate(ocd=TestSchedulerConfig.class)
public class TestJobScheduler implements Job {
    

    private final Logger logger = LoggerFactory.getLogger(TestJobScheduler.class);

    private int schedulerJobID;

    @Reference
    private Scheduler scheduler;
    

    @Activate
    protected void activate(TestSchedulerConfig config) {
        schedulerJobID = config.schedulerName().hashCode();
        addScheduler(config);
    }

    @Deactivate
    protected void deactivate(TestSchedulerConfig config) {
        removeSchedulerJob();
    }

    public void addScheduler(TestSchedulerConfig config){
        ScheduleOptions in = scheduler.EXPR("0 03 17 1/1 * ? *");
        Map<String, Serializable> inMap=new HashMap<>();
        inMap.put("country","IN");
        inMap.put("url","www.in.com");
        in.config(inMap);

        scheduler.schedule(this,in);
        ScheduleOptions de = scheduler.EXPR("0 04 17 1/1 * ? *");
        Map<String, Serializable> deMap=new HashMap<>();
        deMap.put("country","DE");
        deMap.put("url","www.de.com");
        de.config(deMap);
        scheduler.schedule(this,de);

        ScheduleOptions us = scheduler.EXPR("0 05 17 1/1 * ? *");
        Map<String, Serializable> usMap=new HashMap<>();
        usMap.put("country","US");
        usMap.put("url","www.us.com");
        us.config(usMap);
        scheduler.schedule(this,us);
    }

    protected void removeSchedulerJob() {
        scheduler.unschedule(String.valueOf(schedulerJobID));
    }

    
    public void execute(JobContext jobContext) {
        logger.debug("Test Job Scheduler is now running, schedulerJobID'{}'", schedulerJobID);
    }
}
