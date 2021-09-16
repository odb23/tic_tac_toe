import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private char whoseTurn = 'X';
    private Cell[][] cells = new Cell[3][3];
    private Label statusLbl = new Label("X's turn to play");
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j< 3; j++) {
                pane.add(cells[i][j] = new Cell(), j, i);
            }
        }
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane); borderPane.setBottom(statusLbl);

        Scene scene = new Scene(borderPane, 350,400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe");
        primaryStage.show();
    }

    public boolean isFull() {
        for(int i = 0; i < 3; i++) {
            for (int j= 0; j < 3; j++) {
                if (cells[i][j].getToken() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isWon(char token) {
        // check rows
        for (int i = 0; i < 3; i++) {
            if (cells[i][0].getToken() == token &&
                    cells[i][1].getToken() == token &&
                    cells[i][2].getToken() == token) return  true;
        }

        //check columns
        for (int i = 0; i < 3; i++) {
            if (cells[0][i].getToken() == token &&
                    cells[1][i].getToken() == token &&
                    cells[2][i].getToken() == token) return  true;
        }

        if (cells[0][0].getToken() == token &&
                cells[1][1].getToken() == token &&
                cells[2][2].getToken() == token) return true;


        return cells[0][2].getToken() == token &&
                cells[1][1].getToken() == token &&
                cells[2][0].getToken() == token;
    }

    public class Cell extends Pane {
        private char token = ' ';

        public Cell() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000,2000);
            this.setOnMouseClicked( event -> handleMouseClick());
        }

        public char getToken() {
            return token;
        }

        public void setToken(char c) {
            this.token = c;

            if(token == 'X') {
                Line line1 = new Line(10,10, this.getWidth() - 10, this.getHeight() - 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));
                Line line2 = new Line(10, this.getHeight()-10, this.getWidth()-10,10);
                line2.startYProperty().bind(this.heightProperty().subtract(10));
                line2.endXProperty().bind(this.widthProperty().subtract(10));

                getChildren().addAll(line1,line2);
            }

            if (token =='O') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2,
                        this.getWidth()/2 - 10, this.getHeight() / 2 - 10);
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.WHITE);

                getChildren().add(ellipse);
            }
        }

        private void handleMouseClick() {
            if (token == ' ' &&  whoseTurn != ' ') {
                setToken(whoseTurn);
            }
            if (isWon(whoseTurn)) {
                statusLbl.setText(whoseTurn + " won! The game is Over.");
                whoseTurn = ' ';
            }else if (isFull()) {
                statusLbl.setText("Draw! The game is over");
            }else {
                whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
                statusLbl.setText(whoseTurn + "'s turn.");
            }
        }
    }

}
