import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/bar_manager_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteRecipeQuery = "DELETE FROM recipes *;";
      String deleteItemsQuery = "DELETE FROM items *;";
      String deleteItem_TypesQuery = "DELETE FROM item_types *;";
      String deleteIngredientsQuery = "DELETE FROM ingredients *;";
      con.createQuery(deleteRecipeQuery).executeUpdate();
      con.createQuery(deleteItemsQuery).executeUpdate();
      con.createQuery(deleteIngredientsQuery).executeUpdate();
      con.createQuery(deleteItem_TypesQuery).executeUpdate();
    }
  }
}
