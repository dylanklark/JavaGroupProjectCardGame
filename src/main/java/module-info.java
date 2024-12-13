module com.example.groupprojectcardgame {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.example.groupprojectcardgame to javafx.fxml;
    exports com.example.groupprojectcardgame;
}