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
    Item newItem = new Item("Knob Creek", type.getId(), 25.36, 28);
    newItem.save();
    goTo("http://localhost:4567/inventory");
    assertThat(pageSource()).contains("Knob Creek");
  }

  @Test
  public void updateItemTest() {
    Type type = new Type("Whiskey");
    type.save();
    Item newItem = new Item("Knob Creek", type.getId(), 25.36, 28);
    newItem.save();
    newItem.update("Maker's Mark" , type.getId(), 25.36, 38);
    String itempage = String.format("http://localhost:4567/inventory");
    goTo(itempage);
    assertThat(pageSource()).contains("Maker's Mark");
  }

  @Test
  public void deleteItemTest() {
    Type type = new Type("Whiskey");
    type.save();
    Item newItem = new Item("Knob Creek", type.getId(), 25.36, 28);
    newItem.save();
    newItem.delete();
    String itempage = String.format("http://localhost:4567/inventory");
    goTo(itempage);
    assertThat(!(pageSource()).contains("Knob Creek"));
  }

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

  @Test
  public void updateIngredientTest() {
    Type type = new Type("Gin");
    type.save();
    Recipe myRecipe = new Recipe("Gin and tonic", "good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    myRecipe.save();
    Ingredient testIngredient = new Ingredient (3, 3.5, "Tanqueray");
    testIngredient.save();
    testIngredient.update(4, 5.5, "Tequilla");
    String ingredientPage = String.format("http://localhost:4567/planner/%d", myRecipe.getId());
    goTo(ingredientPage);
    assertThat(pageSource()).contains("Gin");
  }

  @Test
  public void deleteIngredientTest() {
    Type type = new Type("Gin");
    type.save();
    Recipe myRecipe = new Recipe("Gin and tonic", "good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    myRecipe.save();
    Ingredient testIngredient = new Ingredient (3, 3.5, "Tanqueray");
    testIngredient.save();
    testIngredient.delete();
    String ingredientPage = String.format("http://localhost:4567/planner/%d", myRecipe.getId());
    goTo(ingredientPage);
    assertThat(!(pageSource()).contains("Gin"));
  }

  @Test
  public void getAllOfType_allIngredientsOfType() {
    Type testType = new Type("whiskey");
    testType.save();
    Item testItem = new Item("Maker's Mark", testType.getId(), 25.36, 31);
    testItem.save();
    String typePath = String.format("http://localhost:4567/inventory");
    goTo(typePath);
    assertThat(pageSource()).contains("Maker's Mark");
  }

  @Test
  public void displaysRecipeOnPlannerPageTest() {
    Type type = new Type("Gin");
    type.save();
    Item testItem = new Item("Aviation", type.getId(), 25.36, 29);
    testItem.save();
    Recipe myRecipe = new Recipe("Gin and tonic", "good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    myRecipe.save();
    Ingredient testIngredient = new Ingredient (3, 3.5, "Tanqueray");
    testIngredient.save();
    String plannerPage= String.format("http://localhost:4567/planner/%d", myRecipe.getId());
    goTo(plannerPage);
    assertThat(pageSource()).contains("Rob Lowe");
  }

  // @Test
  // public void updateADrinkTest() {
  //   Type type = new Type("Gin");
  //   type.save();
  //   Item testItem = new Item("Aviation", type.getId(), 25.36, 29);
  //   testItem.save();
  //   Ingredient testIngredient = new Ingredient (3, 3.5, "Tanqueray");
  //   testIngredient.save();
  //   Recipe myRecipe = new Recipe("Gin and tonic", "good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
  //   myRecipe.save();
  //   Recipe secondRecipe = new Recipe("Jack on rocks", "good", "John Roll", "3 minutes", "add jack over ice");
  //   goTo("http://localhost:4567/planner/");
  //   fill("#name").with("Gin and tonic");
  //   fill("#notes").with("good");
  //   fill("#creator").with("John Roll");
  //   fill("#preptime").with("3 minutes");
  //   fill("#direction").with("add jack over ice");
  //   submit(".btn btn-primary");
  //   click("a", withText("Add recipe"));
  //   assertThat(pageSource()).contains("Jack and Coke");
  // }
}
