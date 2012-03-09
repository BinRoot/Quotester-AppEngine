package com.binroot;

import java.io.IOException;
import java.util.List;

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
 * web: /login
 * arguments: ?username=[...]&password=[...]
 * output: 1 or 0 depending on successful combination
 */

@SuppressWarnings("serial")
public class LogInServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text");
		//PrintWriter out = resp.getWriter();

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		if(username!=null && password!=null) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key rootKey = KeyFactory.createKey("Root", "root");
			Query query = new Query("Target", rootKey);
			List<Entity> targets = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
			
			for(Entity e : targets) {
				if(e.getProperty("targetName").equals(username)) {
						if(e.getProperty("password").equals(password)) {
							resp.getWriter().print("0 -- Successful login");
							return;
						}
						else {
							resp.getWriter().print("2 -- Wrong password");
							return;
						}
				}
				
			}
			
		}
		
		resp.getWriter().print("1 -- Username not found");
	}
}
