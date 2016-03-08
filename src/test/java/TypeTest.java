import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class TypeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();



  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Type.all().size());
  }

  @Test
  public void save_addsInstanceOfTypeToList() {
    Type testType = new Type("whiskey");
    Type testType1 = new Type( "gin");
    testType.save();
    testType1.save();
    assertEquals(2, Type.all().size());
  }

  @Test
  public void find_findsObjectByIdFromDB() {
    Type typeOne = new Type("whiskey");
    typeOne.save();
    Type savedType = Type.find(typeOne.getId());
    assertTrue(typeOne.equals(savedType));
  }

  @Test
  public void getAllOf_findsAllIngredientsOfType() {
    Type testType = new Type("whiskey");
    testType.save();
    Item testItem = new Item("Maker's Mark", testType.getId(), 25.36, 31);
    testItem.save();
    assertEquals(1, testType.getAllOf().size());
  }
}
