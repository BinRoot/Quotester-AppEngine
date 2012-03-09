package com.binroot;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
 * web: /add
 * arguments: ?targetName=[...]&password=[...]
 * output: new entity in datastore
 * 
 * used for creating new accounts
 */

@SuppressWarnings("serial")
public class AddTargetServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(AddTargetServlet.class.getName());
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text");
		
		boolean randomPassword = false;
		
		String targetName = req.getParameter("targetName");
		String password = req.getParameter("password");
		
		if(password==null) {
			
			password = (targetName.hashCode()+"").subSequence(1, 5).toString();
			
			
			randomPassword = true;
		}
		
		if(targetName!=null) {
			if(!targetName.contains(";") && !targetName.equals("")) {
				Key rootKey = KeyFactory.createKey("Root", "root");
				
				boolean exists = false;
				
				for(Entity e : Util.listChildren("Target", rootKey)) {
					if(e.getProperty("targetName").equals(targetName)
							&& e.getProperty("password").equals(password)) {
						exists = true;
						break;
					}
				}
				
				if(!exists) {
					Entity rootEntity = new Entity("Target", rootKey);
					rootEntity.setProperty("targetName", targetName);
					rootEntity.setProperty("password", password);
					Util.persistEntity(rootEntity);
					// TODO: check if that worked...
					if(randomPassword) {
						resp.getWriter().print("0 -- Successfully added new target ;"+password);
					}
					else {
						resp.getWriter().print("0 -- Successfully added new target ;");
					}
					
				}
				else {
					resp.getWriter().print("1 -- Target already exists");
				}
			}
			else {
				resp.getWriter().print("2 -- Name contains an invalid character");
			}
		}
		else {
			resp.getWriter().print("3 -- targetName is null");
		}
		
	}
}
