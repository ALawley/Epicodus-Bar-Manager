import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Type {
  private int id;
  private String type;


  public int getId() {
    return id;
  }

  public String getType() {
    return type;
  }


  public Type(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(Object otherType){
    if (!(otherType instanceof Type)) {
      return false;
    } else {
      Type newType = (Type) otherType;
      return this.getType().equals(newType.getType()) &&
        this.getId() == newType.getId();
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO item_types (type) VALUES (:type)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("type", type)
      .executeUpdate()
      .getKey();
    }
  }

  //READ
  public static List<Type> all() {
    String sql = "SELECT * FROM item_types";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Type.class);
    }
  }

  public static Type find(int id) {
    String sql = "SELECT * FROM item_types WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Type.class);
    }
  }

  public List<Item> getAllOf() {
    String sql = "SELECT items.* FROM items JOIN item_types ON (item_types.id = items.type_id) WHERE item_types.id = :id ORDER BY name,price";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Item.class);
    }
  }
}
