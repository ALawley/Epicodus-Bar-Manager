import org.junit.*;
import static org.junit.Assert.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;


public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
   public void rootTest() {
     goTo("http://localhost:4567/");
     assertThat(pageSource()).contains("For people who like to drink at home");
   }

  @Test
  public void itemDisplaysTest() {
    Type type = new Type("Whiskey");
    type.save();
    Item newItem = new Item("Knob Creek", type.getId(), 36.99, 26.53);
    newItem.save();
    goTo("http://localhost:4567/inventory");
    assertThat(pageSource()).contains("Knob Creek");
  }
// item update and item delete
  @Test
  public void recipeIsCreatedTest() {
    Recipe myRecipe = new Recipe ("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    myRecipe.save();
    goTo("http://localhost:4567/recipes");
    assertThat(pageSource()).contains("Gin and tonic");
  }

  @Test
  public void addIngredientToRecipe() {
    Ingredient testIngredient = new Ingredient(3, 3.5, "Tanqueray");
    testIngredient.save();
    Recipe myRecipe = new Recipe ("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    myRecipe.save();
    String recipePath = String.format("http://localhost:4567/planner/%d",
    myRecipe.getId());
    goTo(recipePath);
    assertThat(pageSource()).contains("Gin and tonic");
  }

  // update Ingredient to Recipe
  // @Test
  // public void updateIngredientTest() {
  //   Ingredient testIngredient = new Ingredient (3, 3.5, "Tanqueray");
  //   testIngredient.save();
  //   String ingredientpage = String.format("http://localhost:4567/ingredient/%d", testIngredient.getId());
  //   goTo(ingredientpage);
  //   fill("#updateIngredient").with(4, 3.5, "Tanqueray");
  //   submit(".updateIngredient");
  //   assertThat(pageSource()).contains(4, 3.5, "Tanqueray");
  // }

  //  delete Ingredient from Recipe
  // @Test
  // public void deleteIngredientTest() {
  //   ingredient testIngredient = new ingredient (3, 3.5, "Tanqueray");
  //   testIngredient.save();
  //   String ingredientpage = String.format("http://localhost:4567/ingredient/%d", testIngredient.getId());
  //   goTo(ingredientpage);
  //   submit(".deleteIngredient");
  //   assertThat(pageSource()).doesNotcontain(3, 3.5, "Tanqueray");
  // }


  @Test
  public void getAllOfType_allIngredientsOfType() {
    Type testType = new Type("whiskey");
    testType.save();
    Item testItem = new Item("Maker's Mark", testType.getId(), 25.36, 31);
    testItem.save();
    String typePath = String.format("http://localhost:4567/inventory/%d", testItem.getId());
    goTo(typePath);
    assertThat(pageSource()).contains("Maker's Mark");
  }

  // test to get to planner update page
  // once clicked, then need to do test for how many is needed to create the drink
}
