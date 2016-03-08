package Lab1;
import java.util.List;

public class Main {
  public static void main (String[] args) {
    AddressBook book = AddressBook.getInstance();
    System.out.println("Initialize the AddressBook with two entries...\n");
    Entry entry1 = new Entry.Builder().setName("Bin Li").setPosAddr("101 Avenue, New York").
                    setPhoneNum("112-112-3211").setEmail("bl1810@nyu.edu").
                    setNote("This is a test of Lab1 for Bin Li").build();
    Entry entry2 = new Entry.Builder().setName("Jin Li").setPosAddr("102 Avenue, New York").
        setPhoneNum("112-112-3213").setEmail("zl1810@nyu.edu").
        setNote("This is a test of Lab1 for Jin Li").build();
    book.add(entry1);
    book.add(entry2);
    System.out.println("There are two entries added. They are...\n");
    System.out.println(book.toString());
    List<Entry> result = book.search("Jin Li");
    if (result.isEmpty()) {
      System.out.println("There is something wrong for searching...\n");
    } else {
      for (Entry entry : result) {
        System.out.println(entry.toString());
      }
    }
    book.save("Lab1_AddressBook");
    System.out.println("\nSuccessfully saved in current folder!\n");
    System.out.println("---------------------------------------");
    System.out.println("Read from input file...");
    System.out.println(book.read("Lab1_AddressBook"));
    book.remove(entry1);
    System.out.println("Removing entry 1...");
    System.out.println(book);
  }
}
