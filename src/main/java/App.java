import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("types", Type.all());
      model.put("items", Item.all());
      model.put("template", "templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/item/added", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int typeId = Integer.parseInt(request.queryParams("typeId"));
      String name = request.queryParams("name");
      double amount = Double.parseDouble(request.queryParams("amount"));
      double price = Double.parseDouble(request.queryParams("price"));
      Item newItem = new Item(name, typeId, amount, price);
      newItem.save();
      response.redirect("/");
      return null;
    });

    get("/planner/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      model.put("recipe", recipe);
      model.put("template", "templates/planner.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/planner/:id/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      int itemId =  Integer.parseInt(request.queryParams("ingredientStated"));
      Item item = Item.find(itemId);

      //In a for loop to then see how many servings can be made
      item.decrementItem(item.getAmount());

      //number of times it loops before hitting '0'
      model.put("numberOfServings", item.getAmount());

      model.put("recipe", recipe);
      model.put("template", "templates/planner-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //if enough servings decrement from inventory, return to recipe page
    post("/planner/:id/createdDrink", (request, response) -> {

      //goes to recipe page
      response.redirect("/");
      return null;
    });
  }
}
// Item.decrementItem(double pourAmount)
