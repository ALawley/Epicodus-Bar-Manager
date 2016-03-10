import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Item {
  private int id;
  private String name;
  private int type_id;
  private double amount;
  private double price;
  private double priceperoz;

  public Item(String name, int type_id, double amount, double price) {
    this.name = name;
    this.type_id = type_id;
    this.amount = amount;
    this.price = price;
    this.priceperoz = price/amount;
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
    return priceperoz;
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
        this.getPricePerOz() == newItem.getPricePerOz() &&
        this.getPrice() == newItem.getPrice();
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO items(name, type_id, amount, price, priceperoz) VALUES (:name, :type_id, :amount, :price, :priceperoz)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .addParameter("type_id", type_id)
      .addParameter("amount", amount)
      .addParameter("price", price)
      .addParameter("priceperoz", priceperoz)
      .executeUpdate()
      .getKey();
    }
  }

  //READ
  public static List<Item> all() {
    String sql = "SELECT * FROM items";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Item.class);
    }
  }

  public static Item find(int id) {
    String sql = "SELECT * FROM items WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Item.class);
    }
  }

  //UPDATE
  public void update(String newName, int newTypeId, double newAmount, double newPrice) {
    double newPricePerOz = newPrice/newAmount;
    String sql = "UPDATE items SET name = :name, type_id = :type_id, amount = :amount, price = :price, priceperoz = :priceperoz WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("name", newName)
        .addParameter("type_id", newTypeId)
        .addParameter("amount", newAmount)
        .addParameter("price", newPrice)
        .addParameter("priceperoz", newPricePerOz)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void amountSet(double newAmount) {
    String sql = "UPDATE items SET amount = :amount WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("amount", newAmount)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void decrementItem(double pourAmount) {
    this.amount -= pourAmount;
    String sql = "UPDATE items SET amount=:amount WHERE id=:id;";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("id", id)
      .addParameter("amount", amount)
      .executeUpdate();
    }
  }

  //DELETE
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM items WHERE id = :id";
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
      }
    }
  }
