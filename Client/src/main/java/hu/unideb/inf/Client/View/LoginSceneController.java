package hu.unideb.inf.Client.View;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import hu.unideb.inf.Client.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class LoginSceneController {

	@FXML
	TextField userName;
	@FXML
	PasswordField password;

	@FXML
	private void login() {
		if (userName.getText().isEmpty() || password.getText().isEmpty()) {
			Main.viewController.errorAlert("Hiányzó adatok", "A felhasználónevet és jelszót muszáj kitölteni", Alert.AlertType.ERROR);
		} else
			Main.clientService.login(userName.getText(), getMD5(password.getText()));
	}

	@FXML
	private TextField userNameR;
	@FXML
	private PasswordField passwordR;
	@FXML
	private PasswordField passwordAgainR;
	@FXML
	private TextField nameR;
	@FXML
	private TextField emailR;
	@FXML
	private Spinner<Integer> ageR;

	@FXML
	private void register() {
		if (userNameR.getText().isEmpty() || passwordR.getText().isEmpty()) {
			Main.viewController.errorAlert("Hiányzó adatok", "A felhasználónevet és a jelszót muszáj kitölteni.", Alert.AlertType.ERROR);
		} else if (!passwordR.getText().equals(passwordAgainR.getText())) {
			Main.viewController.errorAlert("Hibás jelszó", "A jelszavak nem egyeznek.", Alert.AlertType.ERROR);
		} else
			Main.clientService.register(userNameR.getText(), getMD5(passwordR.getText()), nameR.getText(), emailR.getText(),
					ageR.getValue());

	}

	private String getMD5(String string) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(string.getBytes("UTF-8"));
			return DatatypeConverter.printHexBinary(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
