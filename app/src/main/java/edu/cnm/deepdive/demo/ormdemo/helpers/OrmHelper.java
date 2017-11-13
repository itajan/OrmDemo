package edu.cnm.deepdive.demo.ormdemo.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import edu.cnm.deepdive.demo.ormdemo.entities.Absence;
import edu.cnm.deepdive.demo.ormdemo.entities.Student;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import junit.framework.Assert;

public class OrmHelper extends OrmLiteSqliteOpenHelper {

  private static final String DATABASE_NAME = "students.db";
  private static final int DATABASE_VERSION = 1;

  // Data access object(Dao)
  private Dao<Student, Integer> studentDao = null;
  private Dao<Absence, Integer> absenceDao = null;


  public OrmHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    try {
      TableUtils.createTable(connectionSource, Student.class);
      TableUtils.createTable(connectionSource, Absence.class);
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

  public synchronized Dao<Absence, Integer> getAbsenceDao() throws SQLException {
    if (absenceDao == null) {
      absenceDao = getDao(Absence.class);
    }
    return absenceDao;
  }

  private void populateDatabase() throws SQLException {
    Calendar calendar = Calendar.getInstance();
    {
      Student student = new Student();
      student.setName("Mortimer Snerd");
      getStudentDao().create(student);

      Absence absence = new Absence();
      absence.setStudent(student);
      absence.setDate(new Date());
      getAbsenceDao().create(absence);

      absence = new Absence();
      absence.setStudent(student);
      calendar.set(2017, 9, 31);
      absence.setDate(calendar.getTime());
      absence.setExcused(true);
      getAbsenceDao().create(absence);

      List<Student> testList = getStudentDao().queryForAll();
      Assert.assertEquals(testList.size(), 1);
      Assert.assertEquals(testList.get(0).getAbsences().size(), 2);

    }
    {
      Student student = new Student();
      student.setName("Charlie McCarthy");
      getStudentDao().create(student);

      Absence absence = new Absence();
      absence.setStudent(student);
      absence.setDate(new Date());
      getAbsenceDao().create(absence);

      List<Student> testList = getStudentDao().queryForAll();
      Assert.assertEquals(testList.size(), 2);

    }

  }

  public interface OrmInteraction {

    OrmHelper getHelper();

  }
}
