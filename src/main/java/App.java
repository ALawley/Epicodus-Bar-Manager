import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.Collections;
import static spark.Spark.*;
import java.text.DecimalFormat;

public class App {
  public static void main(String[] args) {
    DecimalFormat formatter = new DecimalFormat("#0.00");
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("types", Type.all());
      model.put("recipes", Recipe.all());
      model.put("template", "templates/recipes.vtl");
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

    get("/planner/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      model.put("recipe", recipe);
      model.put("formatter", formatter);
      model.put("template", "templates/planner.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("planner/:id/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      Boolean update = true;
      model.put("update", update);
      model.put("recipe", recipe);
      model.put("formatter", formatter);
      model.put("types", Type.all());
      model.put("template", "templates/planner.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("planner/:id/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String notes = request.queryParams("notes");
      String creator = request.queryParams("creator");
      String preptime = request.queryParams("preptime");
      String directions = request.queryParams("directions");
      Recipe recipeUpdated = Recipe.find(Integer.parseInt(request.params(":id")));

      recipeUpdated.updateRecipe(name, notes, creator, preptime, directions);
      recipeUpdated.clearIngredients();

      for (int i = 1; i <= 8; i++) {
        String amountfield = String.format("amount%d", i);
        String typefield = String.format("type%d", i);
        String infofield = String.format("info%d", i);
        String amountValue = request.queryParams(amountfield);
        Integer typeId = Integer.parseInt(request.queryParams(typefield));
        String info = request.queryParams(infofield);
        if (amountValue == "" || typeId == 0) {} else {
          Double amount = Double.parseDouble(amountValue);
          Ingredient newIngredient = new Ingredient(typeId, amount, info);
          newIngredient.save();
          recipeUpdated.addIngredient(newIngredient.getId());
        }
      }
      response.redirect("/recipes");
      return null;
    });

    get("/planner/:id/make", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      double price = 0;
      ArrayList<Integer> itemIds = new ArrayList<Integer>();
      String[] itemIdStrings = request.queryParamsValues("ingredientStated");
      for (String itemIdString : itemIdStrings) {
        itemIds.add(Integer.parseInt(itemIdString));
      }

      List<Ingredient> ingredients = recipe.getIngredients();
      ArrayList<Item> items = new ArrayList<Item>();
      for (int itemId : itemIds) {
        items.add(Item.find(itemId));
      }
      ArrayList<Integer> servings = new ArrayList<Integer>();
      for (int i=0; i<items.size(); i++) {
        int amountCanMake = (int) Math.round(items.get(i).getAmount()/ingredients.get(i).getAmount());
        servings.add(amountCanMake);
        double ozprice = items.get(i).getPricePerOz();
        double ozamount = ingredients.get(i).getAmount();
        price += ozprice * ozamount;
      }
      Item limitingItem =  items.get(servings.indexOf(Collections.min(servings)));
      int maxServings = Collections.min(servings);

      model.put("maxservings", maxServings);
      model.put("drinkcost", price);
      model.put("itemIds", itemIds);
      model.put("limitingItem", limitingItem);
      model.put("recipe", recipe);
      model.put("formatter", formatter);
      model.put("template", "templates/planner-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/planner/:id/confirm", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      Integer servings = Integer.parseInt(request.queryParams("servingNumber"));
      Integer maxServings = Integer.parseInt(request.queryParams("maxservings"));
      System.out.println("max servings: " + maxServings);
      ArrayList<Integer> itemIds = new ArrayList<Integer>();
      String[] itemIdStrings = request.queryParamsValues("itemid");
      for (String itemIdString : itemIdStrings) {
        itemIds.add(Integer.parseInt(itemIdString));
      }
      if (servings > maxServings) {
        Boolean notEnough = true;
        double price = Double.parseDouble(request.queryParams("price"));
        Item limitingItem = Item.find(Integer.parseInt(request.queryParams("limitingItem")));
        model.put("notenough", notEnough);
        model.put("maxservings", maxServings);
        model.put("drinkcost", price);
        model.put("itemIds", itemIds);
        model.put("limitingItem", limitingItem);
        model.put("recipe", recipe);
        model.put("formatter", formatter);
        model.put("template", "templates/planner-update.vtl");
        return new ModelAndView(model, layout);
      } else {
        ArrayList<Item> drinkIngredients = new ArrayList<Item>();

        for (int itemId : itemIds) {
          drinkIngredients.add(Item.find(itemId));
        }
        List<Ingredient> ingredients = recipe.getIngredients();
        for (int i=0; i<drinkIngredients.size(); i++) {
          double pourAmount = ingredients.get(i).getAmount() * servings;
          drinkIngredients.get(i).decrementItem(pourAmount);
        }
        model.put("types", Type.all());
        model.put("recipes", Recipe.all());
        model.put("template", "templates/recipes.vtl");
        return new ModelAndView(model, layout);
      }
    }, new VelocityTemplateEngine());

    get("/inventory", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("types", Type.all());
      model.put("items", Item.all());
      model.put("formatter", formatter);
      model.put("template", "templates/inventory.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/inventory/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      for (Item updateItem : Item.all()) {
        String formname = String.format("amount%d", updateItem.getId());
        double newAmount = Double.parseDouble(request.queryParams(formname));
        updateItem.amountSet(newAmount);
      }
      model.put("types", Type.all());
      model.put("items", Item.all());
      model.put("formatter", formatter);
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
  }
}
