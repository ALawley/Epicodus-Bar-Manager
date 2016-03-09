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
    String sql = "INSERT INTO ingredients(type_id, amount, info) VALUES (:type_id, :amount, :info)";
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
    String sql = "SELECT * FROM ingredients";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Ingredient.class);
    }
  }

  public static Ingredient find(int id) {
    String sql = "SELECT * FROM ingredients WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Ingredient.class);
    }
  }

  public Type getType() {
    String sql = "SELECT item_types.* FROM item_types WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", type_id)
        .executeAndFetchFirst(Type.class);
    }
  }

  public String getTypeName() {
    String sql = "SELECT * FROM item_types WHERE id=:type_id";
    try (Connection con = DB.sql2o.open()) {
      Type type = con.createQuery(sql)
      .addParameter("type_id", this.getTypeId())
      .executeAndFetchFirst(Type.class);
      return type.getType();
    }
  }

  //UPDATE
  public void update(int newTypeId, double newAmount, String newInfo) {
    String sql = "UPDATE ingredients SET type_id = :type_id, amount = :amount, info = :info WHERE id = :id";
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
      String sql = "DELETE FROM ingredients WHERE id = :id";
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
      }
    }
  }
