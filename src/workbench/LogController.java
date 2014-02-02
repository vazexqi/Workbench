package workbench;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class LogController {
	
	public interface LogMessage {
		String getTitle();
		String getUrl();
		String getMessage();
	}
	
	private Main application;
	
	private AnchorPane root;
	private HBox editorOperationsBar;
	private Button clearButton;
	private TextArea textArea;
	
	public LogController(Main application) {
		this.application = application;
		createGraph();
		application.metadataConnection().addListener((o, oldValue, newValue) -> handleMetadataConnectionChanged());
	}
	
	public Node getRoot() {
		return root;
	}
	
	public void log(LogMessage message) {
		
		if (message.getTitle() != null) {
			textArea.appendText(message.getTitle() + "\n");
		}
		textArea.appendText(message.getUrl() + "\n");
		textArea.appendText(prettyFormat(message.getMessage()));
		textArea.appendText("\n\n\n\n\n");
		
		clearButton.setDisable(false);
	}
	
	private void createGraph() {
		
		root = new AnchorPane();
		
		editorOperationsBar = new HBox();
		editorOperationsBar.setAlignment(Pos.BASELINE_CENTER);
		AnchorPane.setTopAnchor(editorOperationsBar, 6.0);
		AnchorPane.setLeftAnchor(editorOperationsBar, 0.0);
		AnchorPane.setRightAnchor(editorOperationsBar, 0.0);
		root.getChildren().add(editorOperationsBar);
		
		clearButton = new Button("Clear");
		clearButton.setDisable(true);
		clearButton.setOnAction(e -> handleClearButtonClicked(e));
		editorOperationsBar.getChildren().add(clearButton);
		
		textArea = new TextArea();
		AnchorPane.setTopAnchor(textArea, 40.0);
		AnchorPane.setBottomAnchor(textArea, 0.0);
		AnchorPane.setLeftAnchor(textArea, 0.0);
		AnchorPane.setRightAnchor(textArea, 0.0);
		root.getChildren().add(textArea);
	}
	
	private void handleMetadataConnectionChanged() {
		
		if (application.metadataConnection().get() != null) {
			
		}
		else {
			
		}
	}
	
	private void handleClearButtonClicked(ActionEvent e) {
		
		textArea.clear();
		clearButton.setDisable(true);
	}
	
	private String prettyFormat(String input) {
	    return prettyFormat(input, 2);
	}
	
	private String prettyFormat(String input, int indent) {
	    try {
	        Source xmlInput = new StreamSource(new StringReader(input));
	        StringWriter stringWriter = new StringWriter();
	        StreamResult xmlOutput = new StreamResult(stringWriter);
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", indent);
	        Transformer transformer = transformerFactory.newTransformer(); 
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.transform(xmlInput, xmlOutput);
	        return xmlOutput.getWriter().toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e); // simple exception handling, please review it
	    }
	}
}
