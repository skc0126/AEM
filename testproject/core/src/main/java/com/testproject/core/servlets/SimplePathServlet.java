package com.testproject.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;



import org.apache.sling.servlets.annotations.SlingServletPaths;


import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.IOException;
import java.util.Iterator;

@Component(service = Servlet.class)
@SlingServletPaths(
    value = "/bin/aem"
)
@ServiceDescription("Simple Path based Servlet")
public class SimplePathServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(SimplePathServlet.class);


    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        final ResourceResolver resourceResolver = request.getResourceResolver();
        Page page =resourceResolver.adaptTo(PageManager.class).getPage("/content/testproject/language-master/en");
        JSONArray jsonArray =new JSONArray();
        try{
            Iterator<Page> childPages = page.listChildren();
            while (childPages.hasNext()){
                Page childPage = childPages.next();
                JSONObject jsonObject =new JSONObject();
                jsonObject.put(childPage.getTitle(),childPage.getPath().toString());
                jsonArray.put(jsonObject);
            }


        } catch (Exception e) {
            LOG.info("\n ERROR {} ", e.getMessage());

        }
        response.setContentType("application/json");
        response.getWriter().write(
                jsonArray.toString());
    }
}