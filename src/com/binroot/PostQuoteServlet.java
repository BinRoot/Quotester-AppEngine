package com.binroot;

import java.io.IOException;
import java.util.Date;
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
 * web: /post
 * arguments: ?targetName=[...]&password=[...]&content=[...]&points=[...]&author=[...]
 * output: new entity in Quote/"[targetName]"
 */

@SuppressWarnings("serial")
public class PostQuoteServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text");
		//PrintWriter out = resp.getWriter();

		String targetName = req.getParameter("targetName");
		String password = req.getParameter("password");
		String content = req.getParameter("content");
		Date date = new Date();
		String points = req.getParameter("points");
		if(points==null) {
			points = "0";
		}
		int pts = 0;
		try {
			pts = Integer.parseInt(points);
		}
		catch(NumberFormatException e) {}
		
		String author = req.getParameter("author");

		if(targetName!=null && password!=null && content!=null) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			//Key targetKey = KeyFactory.createKey("Target", targetName);

			Key rootKey = KeyFactory.createKey("Root", "root");


			//Query query = new Query("Target", rootKey);
			//List<Entity> targets = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

			boolean passwordCorrect = false;

			for(Entity e : Util.listChildren("Target", rootKey)) {
				if(e.getProperty("targetName").equals(targetName)
						&& e.getProperty("password").equals(password)) {
					passwordCorrect = true;
					break;
				}
			}

			if(passwordCorrect) {
				Key targetKey = KeyFactory.createKey("Target", targetName);

				Entity quoteEntity = new Entity("Quote", targetKey);
				quoteEntity.setProperty("user", targetName);
				quoteEntity.setProperty("date", date);
				quoteEntity.setProperty("content", content);
				quoteEntity.setProperty("points", pts);
				quoteEntity.setProperty("author", author);

				Util.persistEntity(quoteEntity);
				resp.getWriter().print("0 -- Success!");
			}
			else {
				resp.getWriter().print("3 -- incorrect password");
			}
		}
	}
}
