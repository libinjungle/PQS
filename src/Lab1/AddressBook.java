package Lab1;

import java.util.List;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Implements an address book library which supports add, remove 
 * and search operation. It can also save the AddressBook object 
 * into a JSON file and read from an input file and convert input 
 * contents into an AddressBook object.
 * 
 * @author BINLI
 *
 */
public class AddressBook {
  // Holds all contact entries.
  private List<Entry> entryList = new ArrayList<>();
  static final Charset ENCODING = StandardCharsets.UTF_8;
  
  /**
   * Does not allow to call constructor from outside.
   */
  private AddressBook() { }
  
  /**
   * Gets an instance of class object.
   * @return AddressBook instance.
   */
  public static AddressBook getInstance() {
    return new AddressBook();
  }
  
  /**
   * Adds a contact entry to entryList.
   * @param entry an entry user wants to add.
   * @return true if a non-empty and non-duplicate entry is added, 
   *         false if entry is empty or duplicate.
   */
  public boolean add(Entry entry) {
    if (entry == null || entryList.contains(entry)) {
      return false;
    }
    entryList.add(entry);
    return true;
  }
  
  /**
   * Remove a contact entry from the entryList.
   * @param entry an entry user wants to remove.
   * @return true if the specified entry is removed, false if that entry 
   *         is not in the entryList.
   */
  public boolean remove(Entry entry) {
    if (!entryList.contains(entry)) {
      return false;
    }
    entryList.remove(entry);
    return true;
  }
  
  /**
   * Find all entries with the user specified entity.
   * @param entity This can be value of name, postal address, phone number, 
   *        email address, or a note.
   * @return a list of entries that match the give entity.
   */
  public List<Entry> search(String entity) {
    List<Entry> matchedEntry = new ArrayList<>();
    for (Entry entry : entryList) {
      if (entry.hasFieldValue(entity)) {
        matchedEntry.add(entry);
      }
    }
    return matchedEntry;
  }
  
  /**
   * Saves this address book into a JSON formatted file. Converts each entry into 
   * a JSON object.
   * @param file A specific file name that is used to save address book to file.
   * @exception IOException.
   */
  public void save(String fileName) {
    Path path = Paths.get(fileName);
    try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)) {
      writer.write(this.toString());
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /** 
   * Parses a file to JSONArray and convert each JSONObject in JSONArray to an entry.
   * Adds each entry to the AddressBook object.
   * @param fileName A file to read from.
   * @return an AddressBook object.
   * @exception FileNotFoundException, IOException, ParseException.
   */
  public AddressBook read(String fileName) {
    AddressBook book = new AddressBook();
    JSONParser parser = new JSONParser();
    try {
      FileReader reader = new FileReader(fileName);
      JSONArray jArray = (JSONArray)parser.parse(reader);
      for (int i=0; i<jArray.size(); i++) {
        JSONObject jObject = (JSONObject)jArray.get(i);
        if (jObject != null) {
          book.add(Entry.toEntry(jObject));
        }
      } 
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return book;
  }
  
  /**
   * Converts entryList to JSON array. 
   */
  @Override
  public String toString() {
    JSONArray jArray = new JSONArray();
    for (Entry entry : entryList) {
      jArray.add(entry.toJSONObject());
    }
    return jArray.toJSONString();
  }
}
