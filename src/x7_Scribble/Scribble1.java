package x7_Scribble;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Scribble1 extends Application {

private BorderPane root;
private Group malGruppe;
private VBox vbox;
private ColorPicker farbauswahl;
private Slider strichbreiteauswahl;

private Rectangle panel;


private double x,y,lastX,lastY;	


	@Override
	public void start(Stage primaryStage) {
		root = new BorderPane();
		malGruppe = new Group();
		vbox = new VBox(50);
		farbauswahl = new ColorPicker();
		strichbreiteauswahl = new Slider(1,50,10);
		Button ruecksetzKnopf = new Button("Rücksetzen");

		// Fenster erzeugen und Eigenschaften festlegen
		Scene scene = new Scene (root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Scribble");
		primaryStage.setResizable(false);
		
		// Eigenschaften für Slider und Farbauswahl festlegen 
		strichbreiteauswahl.setShowTickMarks(true);		//Marker anzeigen
		strichbreiteauswahl.setShowTickLabels(true);	//Labels anzeigen
		farbauswahl.setValue(Color.BLACK);				//Strichfarbe festlegen
		
		
		
		// Vbox erstellen
		vbox.getChildren().addAll(farbauswahl,strichbreiteauswahl,ruecksetzKnopf);
		vbox.prefWidth(50);		// bevorzugte Breite
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(50);
	    vbox.setStyle("-fx-border-width: 1;" + "-fx-border-color: black");
	    
		

		// Zeichenfläche erstellen
		panel = new Rectangle(600,400,Color.WHITESMOKE);
		malGruppe.getChildren().addAll(panel);
		malGruppe.setAutoSizeChildren(false);
		

		
		//alles auf der BorderPane platzieren
		root.setCenter(malGruppe);	//Panel in der Mitte platzieren
		root.setLeft(vbox); 		//Vbox links platzieren
		
		
		
		panel.setOnMousePressed(new LineStarter());
		panel.setOnMouseDragged(new DragPainter());
		panel.setOnMouseReleased(new LineEnder());	
		
		ruecksetzKnopf.setOnAction(new Loeschen());
		
		primaryStage.show();

	}
	
	
	

	
	private class Loeschen implements EventHandler<ActionEvent>{
		public void handle (ActionEvent event) {
			malGruppe.getChildren().clear();
			malGruppe.getChildren().add(panel);
			
		}
	}
	
	
	private class LineStarter implements EventHandler<MouseEvent>{
		public void handle(MouseEvent event) {
				x=event.getX(); 
				y=event.getY();			
		}
	}
	
	public class DragPainter implements EventHandler<MouseEvent> {
			
		public void handle(MouseEvent event) {
			
			// zeichnen außerhalb der Zeichenfläche verhindern
			double Xmax = panel.getWidth();
			double Ymax = panel.getHeight();
			if (event.getX() <= strichbreiteauswahl.getValue() || 
				event.getY() <= strichbreiteauswahl.getValue() ||
				event.getX() >= (panel.getWidth() - strichbreiteauswahl.getValue() ) || 
				event.getY() >= (panel.getHeight() - strichbreiteauswahl.getValue())) {
				return;
				
			}
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
			//Linie 
			//malGruppe.getChildren().add(new Circle(lastX,lastY,3,farbauswahl.getValue()));
		}
	}
	
	public static void main (String[] args) {
		launch(args);
	}
	

}
