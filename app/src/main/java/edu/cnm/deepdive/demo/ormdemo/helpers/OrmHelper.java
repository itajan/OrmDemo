package edu.cnm.deepdive.demo.ormdemo.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import edu.cnm.deepdive.demo.ormdemo.entities.Student;
import java.sql.SQLException;

public class OrmHelper extends OrmLiteSqliteOpenHelper {

  private static final String DATABASE_NAME = "students.db";
  private static final int DATABASE_VERSION = 1;

  // Data access object(Dao)
  private Dao<Student, Integer> studentDao = null;


  public OrmHelper (Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    try {
      TableUtils.createTable(connectionSource, Student.class);
      populateDatabase();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
      int newVersion) {

  }

  @Override
  public void close() {
    studentDao = null;
    super.close();
  }

  public synchronized Dao<Student, Integer> getStudentDao() throws SQLException {
    if (studentDao == null) {
      studentDao = getDao(Student.class);
    }
    return studentDao;
  }

  private void populateDatabase() throws SQLException {
    Student student = new Student();
    student.setName("Mortimer Snerd");
    getStudentDao().create(student);
    student = new Student();
    student.setName("Charlie McCarthy");
    getStudentDao().create(student);
  }
}
