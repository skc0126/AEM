package com.testproject.core.config;



import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;




@ObjectClassDefinition(name = "Simple Scheduler Config", description = "This is a scheduler config")
public @interface TestSchedulerConfig {

    @AttributeDefinition(name = "Scheduler name", description = "Enter the name of the Scheduler", type = AttributeType.STRING)
    public String schedulerName() default "Default Scheduler Name";

    @AttributeDefinition(name = "Cron Expression", description = "Cron Expression used by the scheduler", type = AttributeType.STRING)
    public String cronExpression() default "0/20 * * * * ?"; //run on every 10 sec.

}
