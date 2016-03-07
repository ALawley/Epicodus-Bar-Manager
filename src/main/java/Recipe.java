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

  public int getId() {
    return id;
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

  @Override
    public boolean equals(Object otherRecipe) {
      if(!(otherRecipe instanceof Recipe)) {
        return false;
      } else {
        Recipe newRecipe = (Recipe) otherRecipe;
        return this.getId() == newRecipe.getId()
         && this.getName().equals(newRecipe.getName())
         && this.getNotes().equals(newRecipe.getNotes())
         && this.getRating() == newRecipe.getRating()
         && this.getCreator().equals(newRecipe.getCreator())
         && this.getPrepTime().equals(newRecipe.getPrepTime());
      }
    }


  public void save() {
    String sql = "INSERT INTO  recipes (name, notes, rating, creator, prep_time) VALUES (:name, :notes, :rating, :creator, :prep_time);";
    try (Connection con = DB.sql2o.open()) {
    this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .addParameter("notes", notes)
      .addParameter("rating", rating)
      .addParameter("creator", creator)
      .addParameter("prep_time", prep_time)
      .executeUpdate()
      .getKey();
    }
  }

  public static Recipe find(int id) {
    String sql = "SELECT * FROM recipes WHERE id=:id;";
    try (Connection con = DB.sql2o.open()) {
    Recipe recipe =  con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Recipe.class);
    return recipe;
    }
  }

  public void updateRecipe(String newName, String newNotes, int newRating, String newCreator, String newPrepTime) {
  this.name = newName;
  this.notes = newNotes;
  this.rating = newRating;
  this.creator = newCreator;
  this.prep_time = newPrepTime;
  String sql = "UPDATE recipes SET name=:name, notes=:notes, rating=:rating, creator=:creator, prep_time=:prep_time WHERE id=:id;";
  try (Connection con = DB.sql2o.open()) {
    this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .addParameter("notes", notes)
      .addParameter("rating", rating)
      .addParameter("creator", creator)
      .addParameter("prep_time", prep_time)
      .addParameter("id", id)
      .executeUpdate()
      .getKey();
    }
  }

}
