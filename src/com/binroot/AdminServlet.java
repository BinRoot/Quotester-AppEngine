package com.binroot;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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
 * web: /admin
 * arguments: N/A
 * output: list targetNames
 * 
 */

@SuppressWarnings("serial")
public class AdminServlet extends HttpServlet {

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
		
		resp.getWriter().print(Util.writeJSON(Util.listChildren("Target", rootKey)));
	}
}
