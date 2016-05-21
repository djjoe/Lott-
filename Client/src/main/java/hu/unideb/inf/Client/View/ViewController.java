package hu.unideb.inf.Client.View;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ViewController extends Application {

	private static Stage primaryStage;
	
	public ViewController() {}

	public void show() {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewController.setPrimaryStage(primaryStage);
		loadLogin();
		primaryStage.show();
	}
	
	private static void loadLogin() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(ViewController.class.getResource("LoginScene.fxml"));
			TabPane rootPane = (TabPane) fxmlLoader.load();
			getPrimaryStage().setScene(new Scene(rootPane));
		} catch (IOException e) {
			System.err.println("Bibi van!");
			e.printStackTrace();
		}
	}
	
	public void loadMenu() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(ViewController.class.getResource("Menu.fxml"));
			TabPane rootPane = (TabPane) fxmlLoader.load();
			getPrimaryStage().setScene(new Scene(rootPane));
		} catch (IOException e) {
			System.err.println("Bibi van!");
			e.printStackTrace();
		}
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		ViewController.primaryStage = primaryStage;
	}
	
	public void errorAlert(String head, String text, Alert.AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setHeaderText(head);
		alert.setContentText(text);
		alert.showAndWait();
	}
	
	public void registerError() {
		errorAlert("Hiba", "Az adott felhasználónév már használatban van", Alert.AlertType.ERROR);
	}
	
	public void registerSuccess() {
		errorAlert("Sikeres regisztráció.", "Most már beléphet.", Alert.AlertType.CONFIRMATION);
	}
}
