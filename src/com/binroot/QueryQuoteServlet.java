package com.binroot;

import java.io.IOException;
import java.util.List;
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
 * web: /quotes
 * arguments: ?targetName=[...]&password=[...]
 * output: JSON of quotes
 */

@SuppressWarnings("serial")
public class QueryQuoteServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(QueryQuoteServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text");
		
		String targetName = req.getParameter("targetName");
		String password = req.getParameter("password");

		if(targetName!=null && password!=null) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			//Key targetKey = KeyFactory.createKey("Target", targetName);
			Key rootKey = KeyFactory.createKey("Root", "root");
			
			boolean correctPassword = false;
			
			for(Entity e : Util.listChildren("Target", rootKey)) {
				if(e.getProperty("targetName").equals(targetName)
						&& e.getProperty("password").equals(password)) {
						correctPassword = true;
						break;
				}
			}
			
			
			if(correctPassword) {
				Key targetKey = KeyFactory.createKey("Target", targetName);
				resp.getWriter().print(Util.writeJSON(Util.listChildren("Quote", targetKey)));
			}
			else {
				resp.getWriter().println("1 -- incorrect password");
			}
			
		}

	}
}
