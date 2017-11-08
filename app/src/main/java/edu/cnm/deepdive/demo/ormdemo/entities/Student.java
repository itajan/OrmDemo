package edu.cnm.deepdive.demo.ormdemo.entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@DatabaseTable(tableName = "STUDENT")
public class Student {

  @DatabaseField(columnName = "STUDENT_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", format = "yyyy-MM-dd HH:mm:ss", canBeNull = false, readOnly = true)
  private Timestamp created;

  @DatabaseField(columnName = "NAME", canBeNull = false)
  private String name;


  public int getId() {

    return id;
  }

  public String getName() {
    return name;
  }

  public Timestamp getCreated() {
    return created;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("name", created);
    return map.toString();
  }
}
