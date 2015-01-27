package x7_Scribble;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Scribble1 extends Application {

private BorderPane root;
private Group malGruppe;
private VBox vbox;
private TextField textfeld;
private ColorPicker farbauswahl;
private Slider strichbreiteauswahl;

	@Override
	public void start(Stage primaryStage) {
		root = new BorderPane();
		malGruppe = new Group();
		vbox = new VBox();
		textfeld = new TextField();
		farbauswahl = new ColorPicker();
		strichbreiteauswahl = new Slider(1,50,10);
		Button okKnopf = new Button("OK");
		
		
		Scene scene = new Scene (root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Scribble");
		
		// Eigenschaften für Slider und Farbauswahl festlegen 
		strichbreiteauswahl.setShowTickMarks(true);		//Marker anzeigen
		strichbreiteauswahl.setShowTickLabels(true);	//Labels anzeigen
		farbauswahl.setValue(Color.BLACK);				//Strichfarbe festlegen
		
		
		// Vbox erstellen
		vbox.getChildren().addAll(farbauswahl,strichbreiteauswahl,textfeld,okKnopf);
		vbox.prefWidth(50);		// bevorzugte Breite
		

		// Zeichenfläche erstellen
		Rectangle panel = new Rectangle(600,400,Color.WHITESMOKE);
		malGruppe.getChildren().addAll(panel);

		
		//alles auf der BorderPane platzieren
		root.setCenter(malGruppe);	//Panel in der Mitte platzieren
		root.setLeft(vbox); 		//Vbox links platzieren
		
		
		
		panel.setOnMousePressed(new LineStarter());
		panel.setOnMouseDragged(new DragPainter());
		panel.setOnMouseReleased(new LineEnder());	
		
		primaryStage.show();

	}
	
	
	
	
	private double x,y,lastX,lastY;	
	
	private class LineStarter implements EventHandler<MouseEvent>{
		public void handle(MouseEvent event) {
			x=event.getX(); 
			y=event.getY();
			malGruppe.getChildren().add(new Circle(x,y,3,Color.RED));
		}
	}
	
	public class DragPainter implements EventHandler<MouseEvent> {
			
		public void handle(MouseEvent event) {
			lastX=x;
			lastY=y;
			x=event.getX();
			y=event.getY();
			Line line = new Line(x,y,lastX,lastY);
			
			// Strichbreite auslesen
			line.setStrokeWidth(strichbreiteauswahl.getValue());
			
			// Farbauswahl auslesen
			line.setStroke(farbauswahl.getValue());
			
			// Linie erzeugen
			malGruppe.getChildren().add(line);
		}		

	}
	
	private class LineEnder implements EventHandler<MouseEvent>{
		public void handle(MouseEvent event) {
			malGruppe.getChildren().add(new Circle(lastX,lastY,3,Color.GREEN));
		}
	}
	
	public static void main (String[] args) {
		launch(args);
	}
	

}
