package workbench;
	
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.MetadataConnection;

public class Main extends Application {
	
	private static final String TITLE = "Workbench";
	private static final String CSS_FILE = "application.css";
	
	private LoginController loginController;
	private DescribeAndListController describeAndListController;
	private PropertiesController propertiesController;
	private EditorController editorController;
	
	private ObjectProperty<EnterpriseConnection> enterpriseConnectionProperty = new SimpleObjectProperty<>();
	private ObjectProperty<MetadataConnection> metadataConnectionProperty = new SimpleObjectProperty<>();
	
	private DoubleProperty apiVersionProperty = new SimpleDoubleProperty();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {		
		try {
			stage.setTitle(TITLE);
			stage.setScene(createScene());
			stage.show();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {	
		if (loginController != null) {
			loginController.logout();
		}
	}
	
	public DescribeAndListController getDescribeAndListController() {
		return describeAndListController;
	}
	
	public PropertiesController getPropertiesController() {
		return propertiesController;
	}
	
	public EditorController getEditorController() {
		return editorController;
	}
	
	public ObjectProperty<EnterpriseConnection> enterpriseConnection() {
		return enterpriseConnectionProperty;
	}
	
	public ObjectProperty<MetadataConnection> metadataConnection() {
		return metadataConnectionProperty;
	}
	
	public DoubleProperty apiVersion() {
		return apiVersionProperty;
	}
	
	private Scene createScene() {
		
		AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root, 1200, 800, Color.WHITE);
		scene.getStylesheets().add(getClass().getResource(CSS_FILE).toExternalForm());
		
		// BEGIN LOGIN PANE
		LoginController loginController = new LoginController(this);
		Node loginGraphRoot = loginController.getRoot();
		AnchorPane.setTopAnchor(loginGraphRoot, 10.0);
		AnchorPane.setLeftAnchor(loginGraphRoot, 10.0);
		AnchorPane.setRightAnchor(loginGraphRoot, 10.0);
		root.getChildren().add(loginGraphRoot);
		
		// BEGIN MAIN SPLIT PANE
		SplitPane mainSplitPane = new SplitPane();
		mainSplitPane.setDividerPosition(0, 0.35);
		AnchorPane.setTopAnchor(mainSplitPane, 50.0);
		AnchorPane.setBottomAnchor(mainSplitPane, 10.0);
		AnchorPane.setLeftAnchor(mainSplitPane, 10.0);
		AnchorPane.setRightAnchor(mainSplitPane, 10.0);
		root.getChildren().add(mainSplitPane);
		AnchorPane mainLeftPane = new AnchorPane();
		AnchorPane mainRightPane = new AnchorPane();
		mainSplitPane.getItems().addAll(mainLeftPane, mainRightPane);
		
		// BEGIN LEFT SPLIT PANE
		SplitPane leftSplitPane = new SplitPane();
		leftSplitPane.setOrientation(Orientation.VERTICAL);
		AnchorPane.setTopAnchor(leftSplitPane, 0.0);
		AnchorPane.setBottomAnchor(leftSplitPane, 0.0);
		AnchorPane.setLeftAnchor(leftSplitPane, 0.0);
		AnchorPane.setRightAnchor(leftSplitPane, 0.0);
		mainLeftPane.getChildren().add(leftSplitPane);
		
		// BEGIN DESCRIBE AND LIST PANE
		describeAndListController = new DescribeAndListController(this);
		Node describeAndListGraphRoot = describeAndListController.getRoot();
		AnchorPane.setTopAnchor(describeAndListGraphRoot, 0.0);
		AnchorPane.setBottomAnchor(describeAndListGraphRoot, 0.0);
		AnchorPane.setLeftAnchor(describeAndListGraphRoot, 0.0);
		AnchorPane.setRightAnchor(describeAndListGraphRoot, 0.0);
		leftSplitPane.getItems().add(describeAndListGraphRoot);
		
		// BEGIN PROPERIES PANE
		propertiesController = new PropertiesController(this);
		Node propertiesGraphRoot = propertiesController.getRoot();
		AnchorPane.setTopAnchor(propertiesGraphRoot, 0.0);
		AnchorPane.setBottomAnchor(propertiesGraphRoot, 0.0);
		AnchorPane.setLeftAnchor(propertiesGraphRoot, 0.0);
		AnchorPane.setRightAnchor(propertiesGraphRoot, 0.0);
		leftSplitPane.getItems().add(propertiesGraphRoot);
		
		// BEGIN EDITOR PANE
		editorController = new EditorController(this);
		Node editorGraphRoot = editorController.getRoot();
		AnchorPane.setTopAnchor(editorGraphRoot, 0.0);
		AnchorPane.setBottomAnchor(editorGraphRoot, 0.0);
		AnchorPane.setLeftAnchor(editorGraphRoot, 0.0);
		AnchorPane.setRightAnchor(editorGraphRoot, 0.0);
		mainRightPane.getChildren().add(editorGraphRoot);
		
		return scene;
	}
}
