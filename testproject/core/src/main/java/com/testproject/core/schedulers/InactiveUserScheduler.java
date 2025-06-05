package com.testproject.core.schedulers;

import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Iterator;

@Component(service = Runnable.class, immediate = true)
public class InactiveUserScheduler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(InactiveUserScheduler.class);
    private static final String CRON_EXPRESSION = "0 0 0 * * ?"; // Runs daily at midnight
    //private static final String CRON_EXPRESSION = "0 */5 * * * ?"; // Runs daily at midnight

    @Reference
    private Scheduler scheduler;

    @Activate
    @Modified
    protected void activate() {
        ScheduleOptions options = scheduler.EXPR(CRON_EXPRESSION);
        options.name("InactiveUserScheduler");
        options.canRunConcurrently(false);
        scheduler.schedule(this, options);
        LOG.info("Inactive User Scheduler initialized.");
    }

    @Override
    public void run() {
        LOG.info("Checking for inactive users...");
        try {
            Session session = getSession();
            Node usersNode = session.getNode("/home/users");
            Iterator<Node> users = usersNode.getNodes();

            while (users.hasNext()) {
                Node userNode = users.next();
                if (isInactive(userNode)) {
                    LOG.info("Inactive User: {}", userNode.getName());
                }
            }
        } catch (RepositoryException e) {
            LOG.error("Error fetching inactive users", e);
        }
    }

    private boolean isInactive(Node userNode) throws RepositoryException {
        return userNode.hasProperty("lastLogin") && userNode.getProperty("lastLogin").getLong() < System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000);
    }

    private Session getSession() {
        // Implement session retrieval logic
        return null;
    }
}
