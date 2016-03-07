import java.util.List;
import org.sql2o.*;

public class Recipe {
  private int id;
  private String name;
  private String notes;
  private int rating;
  private String creator;
  private String prep_time;


  public Recipe (String name, String notes, int rating, String creator, String prep_time) {
    this.name = name;
    this.notes = notes;
    this.rating = rating;
    this.creator = creator;
    this.prep_time = prep_time;
  }


  public String getName() {
    return name;
  }

  public String getNotes() {
    return notes;
  }

  public int getRating() {
    return rating;
  }
  public String getCreator() {
    return creator;
  }

  public String getPrepTime() {
    return prep_time;
  }

  public static List<Recipe> all() {
  String sql = "SELECT * FROM recipes ORDER BY name";
  try (Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Recipe.class);
  }
}

}
