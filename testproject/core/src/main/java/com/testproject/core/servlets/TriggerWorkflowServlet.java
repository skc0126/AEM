package com.testproject.core.servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.testproject.core.utils.MyServiceUser;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
           property = { "sling.servlet.paths=/bin/triggerworkflow",
                       "sling.servlet.methods=GET" })
public class TriggerWorkflowServlet extends SlingSafeMethodsServlet {


    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) 
            throws ServletException, IOException {
                ResourceResolver Resource = request.getResourceResolver();
                String payload=request.getRequestParameter("payload").getString();
                String status="workflow in progress";
                WorkflowSession workflowSession = Resource.adaptTo(WorkflowSession.class);
                WorkflowModel workflowModel;
                try {
                    workflowModel = workflowSession.getModel("/var/workflow/models/page-review-process");
                    WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH",payload);
                    status = workflowSession.startWorkflow(workflowModel,workflowData).getState();
                } catch (WorkflowException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }   
                response.setContentType("text/plain");      
                response.getWriter().write(status);
    }
}
