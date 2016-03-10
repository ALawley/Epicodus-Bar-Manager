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
  public void getAmount_returnsAmountFromItemLocally() {
    Item testItem = new Item("Tanqueray", 2, 25.36, 28);
    assertEquals(25.36, testItem.getAmount(), .01);
  }

  @Test
  public void getAmount_returnsAmountFromItemInDatabase() {
    Item testItem = new Item("Tanqueray", 2, 25.36, 28);
    testItem.save();
    Item savedItem = Item.find(testItem.getId());
    assertEquals(25.36, savedItem.getAmount(), .01);
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
    assertEquals("Maker's Mark", savedItem.getName());
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

  @Test
  public void getPricePerOz_returnsPricePerOunce() {
    Item testItem = new Item("Tanqueray", 2, 4, 28);
    testItem.save();
    assertEquals(7.00, testItem.getPricePerOz(), .001);
  }

  @Test
  public void decrementItem_decreaseAmountCorrectly() {
    Item testItem = new Item("Tanqueray", 2, 25.36, 28);
    testItem.save();
    testItem.decrementItem(7);
    assertEquals(18.36, testItem.getAmount(), .001);
  }

  @Test
  public void getTypeName_getsNameByType() {
    Type type = new Type("Whiskey");
    type.save();
    Item testItem = new Item ("Federal Reserve", type.getId(), 25.36, 59.99);
    testItem.save();
    assertEquals("Whiskey", testItem.getTypeName());
  }
}
