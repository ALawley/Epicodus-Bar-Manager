import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class IngredientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Ingredient.all().size());
  }

  @Test
  public void save_addsInstanceOfIngredientToList() {
    Ingredient testIngredient = new Ingredient(2, 3.5, "Tanqueray");
    Ingredient testIngredient1 = new Ingredient(4, 1.5, "Maker's Mark");
    testIngredient.save();
    testIngredient1.save();
    assertEquals(2, Ingredient.all().size());
  }

  @Test
  public void update_updatesIngredient() {
    Ingredient testIngredient = new Ingredient(2, 3.5, "Tanqueray");
    testIngredient.save();
    testIngredient.update(4, 1.5, "Maker's Mark");
    Ingredient savedIngredient = Ingredient.find(testIngredient.getId());
    assertEquals(1.5, savedIngredient.getAmount(), .001);
  }

  @Test
  public void delete_removesIngredientFromDatabase() {
    Ingredient testIngredient = new Ingredient(2, 3.5, "Tanqueray");
    testIngredient.save();
    testIngredient.delete();
    assertEquals(0, Ingredient.all().size());
  }

  @Test
  public void find_findsInstanceOfIngredientById() {
    Ingredient testIngredient = new Ingredient(2, 3.5, "Tanqueray");
    testIngredient.save();
    assertEquals(Ingredient.find(testIngredient.getId()), testIngredient);
  }
}
