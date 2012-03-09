package com.binroot;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * web: /quotes
 * arguments: ?targetName=[...]&password=[...]
 * output: JSON of quotes
 */

@SuppressWarnings("serial")
public class DeleteQuoteServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(DeleteQuoteServlet.class.getName());

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
		String name = req.getParameter("name");

		if(targetName!=null && password!=null && name!=null) {
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
				
				for(Entity e : Util.listChildren("Quote", targetKey)) {
					
					String eName;
					
					if (e.getKey().getName() == null)
						eName = ""+e.getKey().getId();
					else
						eName = e.getKey().getName();
					
					if(eName.equals(name)) {
						Util.deleteEntity(e.getKey());
						resp.getWriter().println("0 -- Successfully deleted quote");
					}
				}
			}
			else {
				resp.getWriter().println("1 -- incorrect login");
			}
			
		}

	}
}
