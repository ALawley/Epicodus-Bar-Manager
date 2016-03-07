import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Ingredient {
  private int id;
  private int recipe_id;
  private int type_id;
  private double amount;
  private String info;

  public Ingredient(int type_id, double amount, String info) {
    this.type_id = type_id;
    this.amount = amount;
    this.info = info;
  }

  public int getId() {
      return id;
  }

  public int getRecipeId() {
    return recipe_id;
  }

  public int getTypeId() {
    return type_id;
  }

  public double getAmount() {
    return amount;
  }

  public String getInfo() {
    return info;
  }


  @Override
  public boolean equals(Object otherIngredient){
    if (!(otherIngredient instanceof Ingredient)) {
      return false;
    } else {
      Ingredient newIngredient = (Ingredient) otherIngredient;
      return this.getInfo().equals(newIngredient.getInfo()) &&
        this.getId() == newIngredient.getId() &&
        this.getTypeId() == newIngredient.getTypeId() &&
        this.getAmount() == newIngredient.getAmount() &&
        this.getRecipeId() == newIngredient.getRecipeId();
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO recipes_items(type_id, amount, info) VALUES (:type_id, :amount, :info)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("type_id", type_id)
      .addParameter("amount", amount)
      .addParameter("info", info)
      .executeUpdate()
      .getKey();
    }
  }

  //READ
  public static List<Ingredient> all() {
    String sql = "SELECT * FROM recipes_items";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Ingredient.class);
    }
  }

  public static Ingredient find(int id) {
    String sql = "SELECT * FROM recipes_items WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Ingredient.class);
    }
  }

  //UPDATE
  public void update(int newTypeId, double newAmount, String newInfo) {
    String sql = "UPDATE recipes_items SET type_id = :type_id, amount = :amount, info = :info WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("type_id", newTypeId)
        .addParameter("amount", newAmount)
        .addParameter("info", newInfo)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  //DELETE
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM recipes_items WHERE id = :id";
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
      }
    }
  }
