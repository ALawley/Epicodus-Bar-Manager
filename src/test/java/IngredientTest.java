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
    Ingredient testIngredient = new Ingredient("Tanqueray", 2, 25.36, 28);
    Ingredient testIngredient1 = new Ingredient("Maker's Mark", 1, 25.36, 31);
    testIngredient.save();
    testIngredient1.save();
    assertEquals(2, Ingredient.all().size());
  }

  @Test
  public void update_updatesIngredient() {
    Ingredient testIngredient = new Ingredient("Tanqueray", 2, 25.36, 28);
    Ingredient newIngredient = new Ingredient("Maker's Mark", 1, 25.36, 31);
    testIngredient.save();
    testIngredient.update("Maker's Mark", 1, 25.36, 31);
    newIngredient.save();
    Ingredient savedIngredient = Ingredient.find(testIngredient.getId());
    assertTrue(savedIngredient.equals(newIngredient));
  }

  @Test
  public void delete_removesIngredientFromDatabase() {
    Ingredient testIngredient = new Ingredient("Tanqueray", 2, 25.36, 28);
    testIngredient.save();
    testIngredient.delete();
    assertEquals(0, Ingredient.all().size());
  }

  @Test
  public void find_findsInstanceOfIngredientById() {
    Ingredient testIngredient = new Ingredient("Tanqueray", 2, 25.36, 28);
    testIngredient.save();
    assertEquals(Ingredient.find(testIngredient.getId()), testIngredient);
  }
}
