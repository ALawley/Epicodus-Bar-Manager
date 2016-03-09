import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.Collections;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String notes = request.queryParams("notes");
      String creator = request.queryParams("creator");
      String preptime = request.queryParams("preptime");
      String directions = request.queryParams("directions");
      Recipe newRecipe = new Recipe(name, notes, creator, preptime, directions);
      newRecipe.save();
      for (int i = 1; i <= 8; i++) {
        String amountfield = String.format("amount%d", i);
        String typefield = String.format("type%d", i);
        String infofield = String.format("info%d", i);
        String amountValue = request.queryParams(amountfield);
        int typeId = Integer.parseInt(request.queryParams(typefield));
        String info = request.queryParams(infofield);
        if (amountValue == "" || typeId == 0) {} else {
          Double amount = Double.parseDouble(amountValue);
            Ingredient newIngredient = new Ingredient(typeId, amount, info);
            newIngredient.save();
            newRecipe.addIngredient(newIngredient.getId());
        }
      }
      response.redirect("/recipes");
      return null;
    });

    get("/recipes", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("types", Type.all());
      model.put("recipes", Recipe.all());
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

    get("/inventory", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("types", Type.all());
      model.put("items", Item.all());
      model.put("template", "templates/inventory.vtl");
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
      response.redirect("/inventory");
      return null;
    });

    // post("/planner/:id/update", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
    //
    //   //error: incompatible types: String[] cannot be converted to String
    //   //May have to create String Array to loop through then convert to int to be stored into a diff array
    //   // ArrayList<Integer> itemIds = new ArrayList<Integer>();
    //
    //   Integer[] itemIds = Integer.parseInt(request.queryParamsValues("ingredientStated"));
    //
    //   List<Ingredient> ingredients = recipe.getIngredients();
    //   ArrayList<Item> items = new ArrayList<Item>();
    //   for (int itemId : itemIds) {
    //     items.add(Item.find(itemId));
    //   }
    //   ArrayList<Integer> servings = new ArrayList<Integer>();
    //   for (int i=0; i<items.size(); i++) {
    //     int amountCanMake = (int) Math.round(items.get(i).getAmount()/ingredients.get(i).getAmount());
    //     servings.add(amountCanMake);
    //   }
    //   Item limitingIngredient =  items.get(servings.indexOf(Collections.min(servings)));
    //   int maxServings = Collections.min(servings);
    //
    //   model.put("numberOfServings", maxServings);
    //   model.put("recipe", recipe);
    //   model.put("template", "templates/planner-update.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

    post("/planner/:id/createdDrink", (request, response) -> {

      //goes to recipes page///
      response.redirect("/recipes");
      return null;
    });
  }


}
// Item.decrementItem(double pourAmount)
