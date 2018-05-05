package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dizitart.no2.*;
import org.dizitart.no2.event.ChangeInfo;
import org.dizitart.no2.event.ChangeListener;
import org.dizitart.no2.event.ChangedItem;
import org.dizitart.no2.objects.ObjectRepository;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.dizitart.no2.Document.createDocument;
import static org.dizitart.no2.objects.filters.ObjectFilters.elemMatch;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class Main extends Application {
    private static final String LEFT = "LEFT";
    JFXDrawer leftDrawer;
    private  NitriteCollection collection;
    JFXTreeTableView<Student> treeView;
    @Override
    public void start(Stage primaryStage) throws Exception{
        ObservableList<Student> students = FXCollections.observableArrayList();



        //test code
        //java initialization
        Nitrite db = Nitrite.builder()
            .compressed()
            .filePath("test.db")
            .openOrCreate("user", "password");
        collection = db.getCollection("test");
        AnchorPane anchorPane = new AnchorPane();
        primaryStage.setTitle("Student Record");
        primaryStage.setScene(new Scene(anchorPane, 600, 800));
        anchorPane.getStylesheets().add(Main.class.getResource("css/jfoenix-components.css").toExternalForm());
//Label

        Label header = new Label("Second Year Minor Project Developed");

        header.setLayoutY(80);
        header.setLayoutX(80);
        header.setStyle(" -fx-font-size: 24px;    -fx-font-weight: BOLD;");

        Label header2 = new Label("By SHUBHAM CHHIMPA");

        header2.setLayoutY(110);
        header2.setLayoutX(140);
        header2.setStyle(" -fx-font-size: 24px;    -fx-font-weight: BOLD;");

        //TOP BAR

        HBox topBar = new HBox(10);
        JFXHamburger h1 = new JFXHamburger();
        HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(h1);
        burgerTask.setRate(-1);
        h1.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
            if(leftDrawer.isShown()){
                leftDrawer.close();
            }
            else {
                leftDrawer.open();
            }

        });


        topBar.setStyle("-fx-background-color: blue;");
        topBar.setPrefSize(610,50);
        topBar.setPadding(new Insets(10,10,10,15));
        topBar.getChildren().addAll(h1);
        //drawer


        leftDrawer = new JFXDrawer();
        VBox leftDrawerPane = new VBox(30);
        leftDrawerPane.setPrefSize(400,750);
        JFXButton addStudent = new JFXButton("ADD STUDENT");



        addStudent.setOnAction(event -> {
            Stage dialog = new Stage(); // new stage
            dialog.initModality(Modality.APPLICATION_MODAL);
            // Defines a modal window that blocks events from being
            // delivered to any other application window.
            dialog.initOwner(primaryStage);
            VBox vb = new VBox(30);
            vb.setStyle("-fx-background-color:WHITE;-fx-padding:40;");

            JFXTextField nameField = new JFXTextField();
            nameField.setLabelFloat(true);
            nameField.setPromptText("STUDENT NAME");

            JFXTextField rollNoField = new JFXTextField();
            rollNoField.setLabelFloat(true);
            rollNoField.setPromptText("ROLL NO");
            JFXTextField yearField = new JFXTextField();
            yearField.setLabelFloat(true);
            yearField.setPromptText("YEAR");
            JFXTextField semField = new JFXTextField();
            semField.setLabelFloat(true);
            semField.setPromptText("SEMESTER");
            JFXTextField branchField = new JFXTextField();
            branchField.setLabelFloat(true);
            branchField.setPromptText("BRANCH");
            JFXButton saveStudent = new JFXButton("SAVE");
            saveStudent.getStyleClass().add("button-raised");
            saveStudent.setOnAction(event1 -> {
                Document doc = createDocument("name", nameField.getText())
                    .put("rollNo",rollNoField.getText())

                    .put("year",yearField.getText())
                    .put("sem",semField.getText())
                    .put("branch",branchField.getText());
                if(nameField.getText().isEmpty()){
                    System.out.print("empty h \n");

                }else {

                    if(rollNoField.getText().isEmpty()){
                        System.out.print("empty h \n");

                    }else {


                        if(yearField.getText().isEmpty()){
                            System.out.print("empty h \n");

                        }else {
                            if(semField.getText().isEmpty()){
                                System.out.print("empty h \n");

                            }else {
                                if(branchField.getText().isEmpty()){
                                    System.out.print("empty h \n");

                                }else {
                                    collection.insert(doc);
                                    Cursor cursor = collection.find();
                                    for (Document document : cursor) {
                                        // process the document
                                        System.out.print("Results : " + document.get("name"));
                                    }
                                    dialog.close();
                                    updateStudent(students);
                                }

                            }

                        }
                    }
                }

            });
            vb.getChildren().setAll(nameField,rollNoField,yearField,semField,branchField,saveStudent);
            Scene dialogScene = new Scene(vb, 400, 500);
            vb.getStylesheets().add(Main.class.getResource("css/jfoenix-components.css").toExternalForm());

            dialog.setScene(dialogScene);
            dialog.show();
        });


        //    addStudent.setStyle("-fx-background-color: blue;");
        addStudent.getStyleClass().add("button-raised");

        JFXButton removeStudent = new JFXButton("REMOVE STUDENT");
        removeStudent.getStyleClass().add("button-raised");
        removeStudent.setOnAction(event -> {


            Stage dialog = new Stage(); // new stage
            dialog.initModality(Modality.APPLICATION_MODAL);
            // Defines a modal window that blocks events from being
            // delivered to any other application window.
            dialog.initOwner(primaryStage);
            VBox vb = new VBox(30);
            vb.setStyle("-fx-background-color:WHITE;-fx-padding:40;");

            JFXTextField rollNoDeleteField = new JFXTextField();
            rollNoDeleteField.setLabelFloat(true);
            rollNoDeleteField.setPromptText("Enter Roll No");

            JFXButton deleteStudent = new JFXButton("DELETE");
            deleteStudent.getStyleClass().add("button-raised");
            deleteStudent.setOnAction(event1 -> {
                removeStudent(rollNoDeleteField.getText());
                System.out.println();
                dialog.close();

            });
            vb.getChildren().setAll(rollNoDeleteField,deleteStudent);
            Scene dialogScene = new Scene(vb, 300, 300);
            vb.getStylesheets().add(Main.class.getResource("css/jfoenix-components.css").toExternalForm());

            dialog.setScene(dialogScene);
            dialog.show();

        });

        leftDrawerPane.setPadding(new Insets(30,10,10,50));
        leftDrawerPane.getChildren().addAll(addStudent,removeStudent);
        leftDrawer.setSidePane(leftDrawerPane);
        leftDrawer.setLayoutY(50);

        leftDrawer.setDefaultDrawerSize(300);
        leftDrawer.setResizeContent(true);
        leftDrawer.setOverLayVisible(false);
        leftDrawer.setResizableOnDrag(true);





/*
// create a document to populate data
        HashMap<String,Integer> midterm1Marks =  new HashMap<String,Integer>();
        midterm1Marks.put("4cs1",17);
        midterm1Marks.put("4cs2",18);
        midterm1Marks.put("4cs3",16);
        midterm1Marks.put("4cs4",17);
        midterm1Marks.put("4cs5",18);
        midterm1Marks.put("4cs6",18);
*/

// insert the document

        //table test

        JFXTreeTableColumn<Student,String> nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setPrefWidth(150);
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, String> param) -> {
            if (nameColumn.validateValue(param)) {
                return param.getValue().getValue().name;
            } else {
                return nameColumn.getComputedValue(param);
            }
        });


        JFXTreeTableColumn<Student,String> rollNoColumn = new JFXTreeTableColumn<>("Roll No");
        rollNoColumn.setPrefWidth(150);
        rollNoColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, String> param) -> {
            if (rollNoColumn.validateValue(param)) {
                return param.getValue().getValue().rollNo;
            } else {
                return rollNoColumn.getComputedValue(param);
            }
        });
        JFXTreeTableColumn<Student,String> yearColumn = new JFXTreeTableColumn<>("Year");
        yearColumn.setPrefWidth(80);
        yearColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, String> param) -> {
            if (yearColumn.validateValue(param)) {
                return param.getValue().getValue().year;
            } else {
                return yearColumn.getComputedValue(param);
            }
        });
        JFXTreeTableColumn<Student,String> semColumn = new JFXTreeTableColumn<>("Semester");
        semColumn.setPrefWidth(80);
        semColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, String> param) -> {
            if (semColumn.validateValue(param)) {
                return param.getValue().getValue().sem;
            } else {
                return semColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Student,String> branchColumn = new JFXTreeTableColumn<>("Branch");
        branchColumn.setPrefWidth(80);
        branchColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, String> param) -> {
            if (branchColumn.validateValue(param)) {
                return param.getValue().getValue().branch;
            } else {
                return branchColumn.getComputedValue(param);
            }
        });



        // build tree
        final TreeItem<Student> root = new RecursiveTreeItem<>(students, RecursiveTreeObject::getChildren);

        treeView = new JFXTreeTableView<>(root);
        treeView.setShowRoot(false);

        treeView.getColumns().setAll(nameColumn,rollNoColumn,yearColumn,semColumn,branchColumn);

        treeView.setLayoutY(200);
        treeView.setLayoutX(30);
        Cursor cursor = collection.find();
        for (Document document : cursor) {
            // process the document
//collection.remove(document);
            students.add(new Student(document.get("name").toString(),document.get("rollNo").toString(),document.get("year").toString(),document.get("sem").toString(),document.get("branch").toString()));
            System.out.print("RETRIVED" + "\n");

        }


        // observe any change to a NitriteCollection
        collection.register(new ChangeListener() {
            @Override
            public void onChange(ChangeInfo changeInfo) {
                System.out.println("Action - " + changeInfo.getChangeType());
                System.out.println("List of affected ids:");
                for (ChangedItem item : changeInfo.getChangedItems()) {
                    System.out.println("Id - " + item.getChangeType());
                    System.out.println("Id - " + item.getChangeTimestamp());
                    System.out.println("Id - " + item.getDocument());
                    if(item.getChangeType().toString().equals("INSERT")){
                        students.add(new Student(item.getDocument().get("name").toString(),item.getDocument().get("rollNo").toString(),item.getDocument().get("year").toString(),item.getDocument().get("sem").toString(),item.getDocument().get("branch").toString()));

                    }
                    if(item.getChangeType().toString().equals("REMOVE")){
                        System.out.println("student removed");
                    }
                }
            }
        });


// main view

        anchorPane.getChildren().addAll(topBar,header,header2,treeView,leftDrawer);
        primaryStage.setResizable(false);
        primaryStage.show();




    }
    public void removeStudent(String rollNo){

        Cursor cursor = collection.find();
        for (Document document : cursor) {
            // process the document
//
            if(document.get("rollNo").toString().matches(rollNo)) {
                collection.remove(document);
                System.out.print(document.get("rollNo").toString() + "\n");
            }


        }


    }

    public void updateStudent(ObservableList<Student> students){


    };
    public static void main(String[] args) {
        launch(args);
    }


}
