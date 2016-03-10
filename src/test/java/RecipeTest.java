import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Recipe.all().size());
  }

  @Test
  public void equals_returnsIfNamesAreTheSame() {
    Recipe recipeOne = new Recipe("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    Recipe recipeTwo = new Recipe("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    assertTrue(recipeOne.equals(recipeTwo));
  }


  @Test
  public void save_savesObjectByIdIntoDB() {
    Recipe recipeOne = new Recipe("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    recipeOne.save();
    Recipe savedRecipe = Recipe.all().get(0);
    assertEquals(recipeOne.getId(), savedRecipe.getId());
  }

  @Test
  public void find_findsObjectByIdFromDB() {
    Recipe recipeOne = new Recipe("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    recipeOne.save();
    Recipe savedRecipe = Recipe.find(recipeOne.getId());
    assertTrue(recipeOne.equals(savedRecipe));
  }

  @Test
  public void updateRecipe_changesOldValueToNewValue() {
    Recipe recipeOne = new Recipe("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    recipeOne.save();
    recipeOne.updateRecipe("Tom Collins","good", "Adam West", "6 minutes", "serve in Collins glass");
    assertEquals("Tom Collins", recipeOne.getName());
  }

  @Test
  public void addIngredient_addsIngredientToRecipe() {
    Ingredient itemOne = new Ingredient(1, 36.99, "info1");
    itemOne.save();
    Recipe recipeOne = new Recipe("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    recipeOne.save();
    recipeOne.addIngredient(itemOne.getId());
    Ingredient savedIngredient = recipeOne.getIngredients().get(0);
    assertTrue(Ingredient.find(itemOne.getId()).equals(savedIngredient));
  }

  @Test
  public void addRating_addsRatingToRecipe() {
    Recipe recipeOne = new Recipe("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    recipeOne.save();
    recipeOne.addRating(5);
    Recipe savedRecipe = Recipe.find(recipeOne.getId());
    assertEquals(5, savedRecipe.getRating());
  }

  @Test
  public void getIngredients_getsIngredientsFromRecipe() {
    Ingredient itemOne = new Ingredient(1, 36.99, "info1");
    itemOne.save();
    Recipe recipeOne = new Recipe("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    recipeOne.save();
    recipeOne.addIngredient(itemOne.getId());
    List savedIngredients = recipeOne.getIngredients();
    assertEquals(savedIngredients.size(), 1);
  }


  @Test
  public void delete_DeletesAllIngredientsFromRecipe() {
    Ingredient itemOne = new Ingredient(1, 36.99, "info1");
    itemOne.save();
    Recipe recipeOne = new Recipe("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    recipeOne.save();
    recipeOne.addIngredient(itemOne.getId());
    recipeOne.delete();
    assertEquals(recipeOne.getIngredients().size(), 0);
  }


}
