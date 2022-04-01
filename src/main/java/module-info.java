module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports SortingAlgorithms;
    opens SortingAlgorithms to javafx.fxml;
    exports ToolsQA;
    opens ToolsQA to javafx.fxml;
}