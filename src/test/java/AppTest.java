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
     assertThat(pageSource()).contains("");
   }

  @Test
  public void itemDisplaysTest() {
    Type type = new Type("Whiskey");
    type.save();
    Item newItem = new Item("Knob Creek", type.getId(), 36.99, 26.53);
    newItem.save();
    goTo("http://localhost:4567/item/added");
    // not created yet, the routing for adding new item ****
    assertThat(pageSource()).contains("Knob Creek");
  }

  @Test
  public void recipeIsCreatedTest() {
    Recipe myRecipe = new Recipe ("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
    myRecipe.save();
    goTo("http://localhost:4567/recipes");
    assertThat(pageSource()).contains("Gin and tonic","good", "Rob Lowe", "5 minutes", "add gin and tonic over ice");
  }
}
