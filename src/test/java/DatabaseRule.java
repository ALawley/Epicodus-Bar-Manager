import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/bar_manager_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteRecipeQuery = "DELETE FROM recipes *;";
      String deleteIngredientsQuery = "DELETE FROM ingredients *;";
      String deleteIngredient_TypesQuery = "DELETE FROM ingredient_types *;";
      String deleteRecipes_IngredientsQuery = "DELETE FROM recipes_ingredients *;";
      con.createQuery(deleteRecipeQuery).executeUpdate();
      con.createQuery(deleteIngredientsQuery).executeUpdate();
      con.createQuery(deleteRecipes_IngredientsQuery).executeUpdate();
      con.createQuery(deleteIngredient_TypesQuery).executeUpdate();
    }
  }
}
