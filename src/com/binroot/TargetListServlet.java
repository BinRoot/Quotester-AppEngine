package com.binroot;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

/**
 * web: /list
 * arguments: N/A
 * output: list targetNames
 * 
 */

@SuppressWarnings("serial")
public class TargetListServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(TargetListServlet.class.getName());
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key rootKey = KeyFactory.createKey("Root", "root");
		
		resp.getWriter().print(writeTargetJSON(Util.listChildren("Target", rootKey)));
	}
	
	/**
	 * List the entities in JSON format
	 * 
	 * @param entities  entities to return as JSON strings
	 */
	public static String writeTargetJSON(Iterable<Entity> entities) {
		
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Entity result : entities) {
			Map<String, Object> properties = result.getProperties();
			for (String key : properties.keySet()) {
				if(key.equals("targetName")) {
					sb.append(properties.get(key));
					break;
				}
			}
			sb.append(";");
			i++;
		}
		if(i>0) {
			sb.deleteCharAt(sb.lastIndexOf(";"));
		}
		return sb.toString();
	}
}
