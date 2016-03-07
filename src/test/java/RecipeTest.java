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
}
