import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class ItemTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Item.all().size());
  }

  @Test
  public void save_addsInstanceOfItemToList() {
    Item testItem = new Item("Tanqueray", 2, 25.36, 28);
    Item testItem1 = new Item("Maker's Mark", 1, 25.36, 31);
    testItem.save();
    testItem1.save();
    assertEquals(2, Item.all().size());
  }

  @Test
  public void update_updatesItem() {
    Item testItem = new Item("Tanqueray", 2, 25.36, 28);
    testItem.save();
    testItem.update("Maker's Mark", 1, 25.36, 31);
    Item savedItem = Item.find(testItem.getId());
    assertEquals(25.36, savedItem.getAmount(), .001);
  }

  @Test
  public void delete_removesItemFromDatabase() {
    Item testItem = new Item("Tanqueray", 2, 25.36, 28);
    testItem.save();
    testItem.delete();
    assertEquals(0, Item.all().size());
  }

  @Test
  public void find_findsInstanceOfItemById() {
    Item testItem = new Item("Tanqueray", 2, 25.36, 28);
    testItem.save();
    assertEquals(Item.find(testItem.getId()), testItem);
  }
}
