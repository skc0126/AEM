package com.testproject.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = "testproject/components/page", selectors = "query-component", methods = HttpConstants.METHOD_POST)
@ServiceDescription("Query Builder Servlet")
public class QueryComponentServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {
        final Resource resource = request.getResource();
        ResourceResolver resourceResolver = request.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        try {
            JsonArray jsonArray = new JsonArray();
            Map<String, String> map = new HashMap<>();
            map.put("type", "nt:unstructured");
            map.put("path", "/content/testproject/us/en");
            map.put("p.limit", "-1");
            map.put("property", "sling:resourceType");
            map.put("property.value", "testproject/components/core-component/servletcomponent");
            
            QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
            Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
            SearchResult searchResult = query.getResult();
            List<Hit> hits = searchResult.getHits();
            for (Hit hit : hits) {
                String hitPath = hit.getPath();
                String pagePath = hitPath.split("/jcr:content")[0];
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Path", pagePath);
                jsonArray.add(jsonObject);
            }

            response.setContentType("application/json");
            response.getWriter()
                    .write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();

        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.logout();
            }
        }

    }
}
