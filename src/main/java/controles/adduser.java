package controles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.user;
import services.userservice;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class adduser {

    @FXML
    private Button ajouter;


    @FXML
    private DatePicker birth;

    @FXML
    private TextField email;


    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField nom;

    @FXML
    private TextField numtel;

    @FXML
    private TextField password;

    @FXML
    private TextField prenom;

    @FXML
    private TextField profileImage;

    @FXML
    private TextField roles;

    @FXML
    private TextField specialite;

    @FXML
    private ImageView userImageView;


    public void initialize() {

        gender.setItems(FXCollections.observableArrayList("Homme", "Femme"));



    }


    @FXML
    void browseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            userImageView.setImage(image);

            profileImage.setText(file.toURI().toString());
        }
    }

    private final userservice  us =new userservice();

    @FXML
    void adduser(ActionEvent event) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            user newUser = new user();
            newUser.setEmail(email.getText());
            try {
                String rolesJson = objectMapper.writeValueAsString(new String[]{"ROLE_ADMIN"});
                newUser.setRoles(rolesJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            newUser.setPassword(password.getText());
            newUser.setNom(nom.getText());
            newUser.setPrenom(prenom.getText());
            newUser.setNumtel(Integer.parseInt(numtel.getText()));
            LocalDate selectedDate = birth.getValue();
            if (selectedDate != null) {
                LocalDateTime birthDateTime = selectedDate.atStartOfDay();
                newUser.setBirth(LocalDate.from(birthDateTime));
            }

            String imagePath = profileImage.getText();
            newUser.setProfileImage(imagePath);
           // newUser.setSpecialite(specialite.getText());
            newUser.setGender(gender.getValue());
            us.create(newUser);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("User added successfully!");
            alert.showAndWait();

            clearFields();
        } catch (SQLException e) {
            showErrorAlert("Error adding user: " + e.getMessage());
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid phone number! Please enter a valid integer.");
        }
    }
    private void clearFields() {
        nom.clear();
        prenom.clear();
        email.clear();
        numtel.clear();
        password.clear();
     //   gender.clear();
        birth.setValue(null);
        profileImage.clear();
        roles.clear();
        specialite.clear();
    }






    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void annuler(ActionEvent event) {
        try {
            // Charger le fichier FXML d'adduser
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/back.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de adduser
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
