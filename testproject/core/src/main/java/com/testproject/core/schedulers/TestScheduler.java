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
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple demo for cron-job like tasks that get executed regularly.
 * It also demonstrates how property values can be set. Users can
 * set the property values in /system/console/configMgr
 */
@Designate(ocd=TestSchedulerConfig.class)
@Component(service=Runnable.class)
public class TestScheduler implements Runnable {
    

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private int schedulerID;
    @Reference
    private Scheduler scheduler;
    

    @Activate
    protected void activate(TestSchedulerConfig testSchedulerConfig) {
        schedulerID = testSchedulerConfig.schedulerName().hashCode();
        addScheduler(testSchedulerConfig);
    }

    @Deactivate
    protected void deactivate(TestSchedulerConfig testSchedulerConfig) {
        removeScheduler();
    }

    public void addScheduler(TestSchedulerConfig testSchedulerConfig){
     ScheduleOptions scheduleOptions =   scheduler.EXPR(testSchedulerConfig.cronExpression());
     scheduleOptions.name(String.valueOf(schedulerID));
     scheduleOptions.canRunConcurrently(false);
     scheduler.schedule(this, scheduleOptions);
     logger.info("Scheduler Added");
    }

    protected void removeScheduler() {
        scheduler.unschedule(String.valueOf(schedulerID));
    }

    @Override
    public void run() {
        logger.debug("SimpleScheduledTask is now running, schedulerID'{}'", schedulerID);
    }
}
