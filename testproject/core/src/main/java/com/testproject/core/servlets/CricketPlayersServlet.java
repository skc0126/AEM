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
package com.testproject.core.servlet;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "testproject/components/page", selectors = "cricket-players", methods = HttpConstants.METHOD_POST,extensions="json")
@ServiceDescription("Simple Demo Servlet")
public class CricketPlayersServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Gson GSON = new Gson();

	@Override
	protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		//final Resource resource = req.getResource();
		ResourceResolver resolver = req.getResourceResolver();
		Session session = resolver.adaptTo(Session.class);
		try {
			JsonArray resultArray = new JsonArray();
			Map<String, String> map = new HashMap<>();
			map.put("type", "dam:Asset");
			map.put("path", "/content/dam/testproject-shared/player-cf");
			map.put("p.limit", "-1");
			map.put("property", "jcr:content/data/cq:model");
			map.put("property.value", "/conf/testproject-shared/settings/dam/cfm/models/player-database");
			QueryBuilder queryBuilder = resolver.adaptTo(QueryBuilder.class);
			Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
			SearchResult searchResult = query.getResult();
			List<Hit> hits = searchResult.getHits();
			for (Hit hit : hits) {
				Resource resource = hit.getResource();
				Resource playerData = resource.getChild("jcr:content/data/master");
				JsonObject playerDataJson = new JsonObject();
				playerDataJson.addProperty("title", resource.getName());
				if(playerData != null ) {
					JsonObject dataJson = new JsonObject();
                 playerData.getValueMap().forEach((key,value) -> {
                	 if (!(key.contains("@LastModified") || key.contains("@ContentType"))){
                	 JsonElement jsonElement = GSON.toJsonTree(value);
                	 dataJson.add(key, jsonElement);
                 }
                 });
                 playerDataJson.add("data", dataJson);
			}
				resultArray.add(playerDataJson);
			}
			resp.setContentType("application/json");
			resp.getWriter().write(resultArray.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
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