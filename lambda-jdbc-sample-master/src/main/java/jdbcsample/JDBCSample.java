package jdbcsample;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCSample implements RequestHandler<String, String>{

  public String getCurrentTime(Context context) {
    LambdaLogger logger = context.getLogger();
    logger.log("Invoked JDBCSample.getCurrentTime");

    String currentTime = "unavailable";

    // Get time from DB server
    try {
      String url = "jdbc:mysql://foodie.cl8mn8l8ahnm.us-east-1.rds.amazonaws.com:3306";
      String username = "Lokmeer";
      String password = "Welcome123";

      Connection conn = DriverManager.getConnection(url, username, password);
      Statement stmt = conn.createStatement();
      //ResultSet resultSet = stmt.executeQuery("SELECT NOW()");
      ResultSet resultSet = stmt.executeQuery("SELECT building_nm FROM FoodieDB.tb_Building;");

      if (resultSet.next()) {
        currentTime = resultSet.getObject(1).toString();
      }

      logger.log("Successfully executed query.  Result for LokMeer Project " + currentTime);

    } catch (Exception e) {
      e.printStackTrace();
      logger.log("Caught exception: " + e.getMessage());
    }

    return currentTime;
  }

public String handleRequest(String arg0, Context arg1) {
	// TODO Auto-generated method stub
	return null;
}
}
