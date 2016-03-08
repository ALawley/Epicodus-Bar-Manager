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
      // model.put("type", )
      model.put("items", Item.all());
      model.put("template", "templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/item/added/$item.getId()", (request, response) -> {
      int typeId = Integer.parseInt(request.queryParams("typeId"));
      String name = request.queryParams("name");
      double amount =Integer.parseInt(request.queryParams("amount"));
      double price =Integer.parseInt(request.queryParams("price"));
      Item newItem = new Item(name, typeId, amount, price);
      newItem.save();
      response.redirect("/");
      return null;
    });

  }
}
