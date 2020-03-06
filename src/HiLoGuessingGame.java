import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

public class HiLoGuessingGame extends Application {
    private Text resultText, numberLabel;
    private TextField numberInputField;
    private Button submitButton, clearButton;
    private Random random;
    private Integer numberToGuess;
    private final Integer MIN_DEFAULT = 1;
    private final Integer MAX_DEFAULT = 100;


    private ArrayList<Integer> guessList;

    public void start(Stage primaryStage) {

        /*Instantiate Random object and set numberToGuess variable to int between 1 and 100
        */
        random = new Random();
        numberToGuess = random.nextInt((MAX_DEFAULT - MIN_DEFAULT) + 1) + MIN_DEFAULT;

        guessList = new ArrayList<>(); //Store all guesses and count of guesses in an ArrayList

        GridPane pane = new GridPane(); //Utilizing GridPane as top level layout
        pane.setStyle("-fx-background-color:floralwhite");

        /*Style added to resultText*/
        resultText = new Text("Guess: ");
        resultText.setFont(Font.font(28));
        resultText.setFill(Color.DODGERBLUE);
        pane.getChildren().add(resultText);
        pane.setVgap(10);
        pane.setAlignment(Pos.TOP_CENTER);

        /*Style added to label*/
        numberLabel = new Text("Choose a number between 1 and 100: ");
        numberLabel.setFont(Font.font(18));
        numberLabel.setFill(Color.DODGERBLUE);
        pane.getChildren().add(numberLabel);

        numberInputField = new TextField();
        TilePane numberPane = new TilePane(numberLabel,numberInputField);
        pane.add(numberPane,0,3);

        /*Adding an image to the scene - delete if code doesn't immediately compile!*/
        Image img = new Image("/java_logo.png");
        ImageView imageView = new ImageView(img);
        imageView.setX(50);
        imageView.setY(25);
        imageView.setFitHeight(455);
        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);
        TilePane imagePane = new TilePane(imageView);
        pane.add(imagePane,0,10);


        submitButton = new Button("Submit guess");
        clearButton = new Button("Clear");
        clearButton.setOnAction(this::handleClearButton);
        TilePane buttonPane = new TilePane(submitButton,clearButton);
        pane.add(buttonPane,0,5);

        numberInputField.setOnAction(this::processNumberField);
        submitButton.setOnAction(this::processNumberField);


        Scene scene = new Scene(pane, 1500, 650);


        primaryStage.setTitle("Hi-Lo Guessing Game GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void processNumberField(ActionEvent event) {

        try {
            guessingGameAlgorithm(numberToGuess, MIN_DEFAULT, MAX_DEFAULT);
        } catch(NumberNotInRangeException nnire){
                Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
                exceptionAlert.setTitle("NumberNotInRangeException");
                exceptionAlert.setHeaderText("Number Not In Range Exception:");
                exceptionAlert.setContentText("Number outside range! Please enter an integer between 1 and 100 (inclusive)!");
                exceptionAlert.show();
            }
        catch(NumberFormatException nfe) {
            Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
            exceptionAlert.setTitle("NumberFormatException");
            exceptionAlert.setHeaderText("Number Format Exception:");
            exceptionAlert.setContentText("Please enter an integer between 1 and 100 (inclusive)!");
            exceptionAlert.show();
        }finally {
            numberInputField.clear();
        }

    }

    public void handleClearButton(ActionEvent event) {
        numberInputField.clear();
        resultText.setText("Guess cleared! Please continue...");
    }


    public void guessingGameAlgorithm(Integer correctNumber, int min, int max) throws NumberNotInRangeException {
        String output;
        Integer userGuess = Integer.parseInt(numberInputField.getText());

            if(userGuess <0 || userGuess > 100) {
                throw new NumberNotInRangeException("Number outside range!");
            }
            if(userGuess == numberToGuess) {
                guessList.add(userGuess);
                output = "You guessed : " + userGuess + ". You guessed correctly - nice job! Total guesses taken = " + guessList.size();
                String playAgain = " Do you want to play again? If so, enter a new number between 1-100, inclusive. ";
                resultText.setText(output + " |  Actual number: " + correctNumber + "\n" +playAgain);
                guessList.clear();
                numberToGuess = random.nextInt((max - min) + 1) + min;
            }
            else if(userGuess < correctNumber) {
                guessList.add(userGuess);
                output = "Your guess of " + userGuess + " was too low. Guess again! ";
                resultText.setText(output + " | Number of guesses " + guessList.size());

            }else  {
                guessList.add(userGuess);
                output = "Your guess of " + userGuess + " was too high. Guess again!";
                resultText.setText(output + " |  Number of guesses " + guessList.size());
            }
        }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
