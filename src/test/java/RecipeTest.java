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
    Recipe recipeOne = new Recipe("Gin and tonic","good", 5, "Rob Lowe", "5 minutes");
    Recipe recipeTwo = new Recipe("Gin and tonic","good", 5, "Rob Lowe", "5 minutes");
    assertTrue(recipeOne.equals(recipeTwo));
  }


  @Test
  public void save_savesObjectByIdIntoDB() {
    Recipe recipeOne = new Recipe("Gin and tonic","good", 5, "Rob Lowe", "5 minutes");
    recipeOne.save();
    Recipe savedRecipe = Recipe.all().get(0);
    assertEquals(recipeOne.getId(), savedRecipe.getId());
  }

  @Test
  public void find_findsObjectByIdFromDB() {
    Recipe recipeOne = new Recipe("Gin and tonic","good", 5, "Rob Lowe", "5 minutes");
    recipeOne.save();
    Recipe savedRecipe = Recipe.find(recipeOne.getId());
    assertTrue(recipeOne.equals(savedRecipe));
  }

  @Test
  public void updateRecipe_changesOldValueToNewValue() {
    Recipe recipeOne = new Recipe("Gin and tonic","good", 5, "Rob Lowe", "5 minutes");
    recipeOne.save();
    recipeOne.updateRecipe("Tom Collins","good", 5, "Adam West", "6 minutes");
    assertEquals("Tom Collins", recipeOne.getName());
  }

  @Test
  public void addItem_addsItemToRecipe() {
    Item itemOne = new Item("vodka", 1, 35.23, 36.99);
    itemOne.save();
    Recipe recipeOne = new Recipe("Gin and tonic","good", 5, "Rob Lowe", "5 minutes");
    recipeOne.save();
    recipeOne.addItem(itemOne);
    Item savedItem = recipeOne.getItems().get(0);
    assertTrue(itemOne.equals(savedItem));
  }

  @Test
  public void getItems_getsItemsFromRecipe() {
    Item itemOne = new Item("vodka", 1, 35.23, 36.99);
    itemOne.save();
    Recipe recipeOne = new Recipe("Gin and tonic","good", 5, "Rob Lowe", "5 minutes");
    recipeOne.save();
    recipeOne.addItem(itemOne);
    List savedItems = recipeOne.getItems();
    assertEquals(savedItems.size(), 1);
  }

}
