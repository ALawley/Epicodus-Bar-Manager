import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/bar_manager_test", null, null);

   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteFirstClassQuery = "DELETE FROM first_class *;";
      String deleteSecondClassQuery = "DELETE FROM second_class *;";
      con.createQuery(deleteFirstClassQuery).executeUpdate();
      con.createQuery(deleteSecondClassQuery).executeUpdate();
    }
  }
}
