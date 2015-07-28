package controllers;

import java.util.List;

import play.*;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;	
import models.SalesEntry;

public class Application extends Controller {

	@Transactional
    public Result index() {
        List< Object[]> objList = SalesEntry.generateReport();

         String output = "Product Name\tTotal# sold\tTotal Amount\n";
            for (Object [] c : objList) {
                output += c[0].toString() + "\t\t" + c[1].toString() + "\t\t" + c[2].toString() + "\n";
            }
        return ok(output);
//    	return ok("testess");
    }

}
