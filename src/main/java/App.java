import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
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
    
    get("/recipes", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("types", Type.all());
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/planner/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      model.put("recipe", recipe);
      model.put("template", "templates/planner.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
