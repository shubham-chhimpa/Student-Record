package sample;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


final class Student extends RecursiveTreeObject<Student> {
    final StringProperty name;
    final StringProperty rollNo;
    final StringProperty year;
    final StringProperty sem;
    final StringProperty branch;



    Student( String name, String rollNo,String year,String sem,String branch) {
        this.name = new SimpleStringProperty(name);
        this.rollNo = new SimpleStringProperty(rollNo);
        this.year = new SimpleStringProperty(year);
        this.sem = new SimpleStringProperty(sem);
        this.branch = new SimpleStringProperty(branch);  }
}
