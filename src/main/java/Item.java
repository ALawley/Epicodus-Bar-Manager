import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Item {
  private int id;
  private String name;
  private int type_id;
  private double amount;
  private double price;

  public Item(String name, int type_id, double amount, double price) {
    this.name = name;
    this.type_id = type_id;
    this.amount = amount;
    this.price = price;
  }

  public int getId() {
      return id;
  }

  public String getName() {
    return name;
  }

  public int getTypeId() {
    return type_id;
  }

  public double getAmount() {
    return amount;
  }

  public double getPrice() {
    return price;
  }

  public double getPricePerOz() {
    return price/amount;
  }

  @Override
  public boolean equals(Object otherItem){
    if (!(otherItem instanceof Item)) {
      return false;
    } else {
      Item newItem = (Item) otherItem;
      return this.getName().equals(newItem.getName()) &&
        this.getId() == newItem.getId() &&
        this.getTypeId() == newItem.getTypeId() &&
        this.getAmount() == newItem.getAmount() &&
        this.getPrice() == newItem.getPrice();
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO ingredients(name, type_id, amount, price) VALUES (:name, :type_id, :amount, :price)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .addParameter("type_id", type_id)
      .addParameter("amount", amount)
      .addParameter("price", price)
      .executeUpdate()
      .getKey();
    }
  }

  //READ
  public static List<Item> all() {
    String sql = "SELECT * FROM ingredients";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Item.class);
    }
  }

  public static Item find(int id) {
    String sql = "SELECT * FROM ingredients WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Item.class);
    }
  }

  //UPDATE
  public void update(String newName, int newTypeId, double newAmount, double newPrice) {
    String sql = "UPDATE ingredients SET name = :name, type_id = :type_id, amount = :amount, price = :price WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("name", newName)
        .addParameter("type_id", newTypeId)
        .addParameter("amount", newAmount)
        .addParameter("price", newPrice)
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
