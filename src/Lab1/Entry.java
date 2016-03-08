package Lab1;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 * Entry class has fields including a name, postal address, phone number, 
 * email address, and a note. To save to and read from input file, 
 * it includes method to convert an entry to JSONObject, method to convert 
 * JSONObject to an entry.
 * <p>
 * Since there are five class fields, Builder pattern is used here
 * to construct entry object for safety and clearness.
 * 
 * @author BINLI
 *
 */
public class Entry {
  private String name;
  private String posAddress;
  private String phoneNum;
  private String email;
  private String note;
  
  /**
   * Constructs Entry object using Builder pattern.
   */
  public static class Builder {
    private String name;
    private String posAddress;
    private String phoneNum;
    private String email;
    private String note;
    
    /**
     * Sets name field.
     * @param val assigned name.
     * @return inner Builder class with name attribute initialized.
     */
    public Builder setName(String val) {
      name = val;
      return this;
    }
    
    /**
     * Sets postal address field. 
     * @param val assigned postal address.
     * @return inner Builder class with postal address attribute initialized.
     */
    public Builder setPosAddr(String val) {
      posAddress = val;
      return this;
    }
    
    /**
     * Sets phone number field.
     * @param val assigned phone number. 
     * @return inner Builder class with phone number attribute initialized.
     */
    public Builder setPhoneNum(String val) {
      phoneNum = val;
      return this;
    }
    
    /**
     * Sets email address field.
     * @param val assigned email address.
     * @return inner Builder class with email address attribute initialized.
     */
    public Builder setEmail(String val) {
      email = val;
      return this;
    }
    
    /**
     * Sets note field.
     * @param val assigned note.
     * @return inner Builder class with note attribute initialized.
     */
    public Builder setNote(String val) {
      note = val;
      return this;
    }
    
    /**
     * Calls Entry constructor.
     * @return Entry object with this builder.
     */
    public Entry build() {
      return new Entry(this);
    }
  }
  
  /**
   * Initializes entry fields with all builder fields.
   * @param builder An object of inner Builder class.
   */
  private Entry(Builder builder) {
    name = builder.name;
    posAddress = builder.posAddress;
    phoneNum = builder.phoneNum;
    email = builder.email;
    note = builder.note;
  }
  
  /**
   * Gets all fields declared in this class, including name, posAddress, 
   * phoneNum, email and note.
   * @return A list of fields represented as String.
   */
  private List<String> getFields() {
    List<String> fieldList = new ArrayList<>();
    Field[] fields = this.getClass().getDeclaredFields();
    for (Field f : fields) {
      fieldList.add(f.getName());
    }
    return fieldList;
  }
  
  /**
   * Gets the corresponding value for a give field. For example, name = 
   * "Katie Bryant", "name" is the input field. "Katie Bryant" will be the 
   * return value.
   * @param field Such as "name", "posAddress"
   * @return Value of field
   * @exception NoSuchFieldException, IllegalArgumentException, 
   *            IllegalAccessException
   */ 
  private Object getFieldValue(String field) {
    Object value = new Object();
    try {
      value = this.getClass().getDeclaredField(field).get(this);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return value;
  }
  
  /**
   * Given a fieldValue, find if there is the matched field value for each field 
   * declared in this class.
   * @param fieldValue This can be any value of those five fields.
   * @return true if there is matched value, or false if there is no such value.
   */
  public boolean hasFieldValue(String fieldValue) {
    List<String> fieldList = getFields();
    for (String field : fieldList) {
      if (getFieldValue(field).equals(fieldValue)) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Converts an entry into <Key, Value> JSON object.
   * @return JSON object representation of an entry
   */
  public JSONObject toJSONObject() {
    JSONObject json = new JSONObject();
    List<String> fieldList = getFields();
    for (String field : fieldList) {
      json.put(field, getFieldValue(field));
    }
    return json;
  }
  
  /**
   * Converts JSON object representation of an entry into an entry object.
   * @param json An JSONObject representation of an entry.
   * @return an entry object.
   */
  public static Entry toEntry(JSONObject json) {
    Entry entry = null;
    String nameVal = (String)json.get("name");
    String posAddresVal = (String)json.get("posAddress");
    String phoneNumVal = (String)json.get("phoneNum");
    String emailVal = (String)json.get("email");
    String noteVal = (String)json.get("note");
    // Build entry object.
    entry = new Entry.Builder().setName(nameVal).setPosAddr(posAddresVal).
              setPhoneNum(phoneNumVal).setEmail(emailVal).setNote(noteVal).build();
    return entry;
  }
  
  /**
   * Override toString() to return JSON string.
   */
  @Override
  public String toString() {
    return this.toJSONObject().toJSONString();
  }
}
