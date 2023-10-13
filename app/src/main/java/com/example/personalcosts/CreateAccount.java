package com.example.personalcosts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class CreateAccount extends AppCompatActivity {

    EditText emailEditText, userNameEditText, passwordEditText, confirmPasswordEditText;
    Button   createAcccountBtn;
    ProgressBar progress_Bar;
    TextView loginBtnView;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        //variables que permite unir lo grafico con lo logico
        emailEditText = findViewById(R.id.txtEmail);
        userNameEditText = findViewById(R.id.txtUserName);
        passwordEditText = findViewById(R.id.txtPassword);
        confirmPasswordEditText = findViewById(R.id.txtConfirmPassword);
        loginBtnView = findViewById(R.id.txtInicarbtn);
        createAcccountBtn = findViewById(R.id.btnCreateAccount);
        progress_Bar = findViewById(R.id.progressBar);

        //crear nuevo evento que permite el funcionalidad de crear cuenta
        createAcccountBtn.setOnClickListener(view -> createAccount());
        loginBtnView.setOnClickListener(v-> startActivity(new Intent(CreateAccount.this, LoginActivity.class)));
    }

    void createAccount() {
        //variables que almacenaran el contenido que el usuario ingrese desde la vista
        String email = emailEditText.getText().toString();
        String username = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        //validar si los campos estan vacios
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(username) ||TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(CreateAccount.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();;
        }
        else {
            boolean isvalidated = validateData(email, password, confirmPassword, username);
            if (!isvalidated) {
                return;
            }
            createAccountInFirebase(email, password, username);
        }
    }
    void createAccountInFirebase(String email, String password,  String username) {
        //esta función se encarga de crear una cuenta de usuario en Firebase utilizando el correo electrónico y la contraseña proporcionados por el usuario.
        changeInProgress(true);//mando a llamar la funcion creada en la aprte de abajo que es para que cargar el progressBar

        //Inicializa y obtiene una instancia de FirebaseAuth, que es esencial para gestionar la autenticación de usuarios en una aplicación
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        //Con el metodo createUserWithEmailAndPassword crear una cuenta de usuario en Firebase utilizando la dirección de correo electrónico (email) y la contraseña(password) proporcionadas como parámetros
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);

                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        // Configura el nombre del usuario en el perfil de Firebase Authentication
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();

                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CreateAccount.this, "Cuenta creada Exitosamente, Verificar Email", Toast.LENGTH_SHORT).show();
                                    user.sendEmailVerification(); // Envía el correo de verificación
                                    firebaseAuth.signOut(); // Cierra la sesión
                                    finish(); // Finaliza la actividad
                                } else {
                                    Toast.makeText(CreateAccount.this, "Error al guardar el nombre del usuario", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(CreateAccount.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void changeInProgress(boolean inprogress) { //Esta línea define la función, que toma un parámetro booleano llamado inprogress parámetro indica si la aplicaciónestá en un estado de progreso o no

        if(inprogress){//verifica si inprogress es true
            //Si lo es, significa que la aplicación está en un estado de progreso,por lo que se realizan las siguientes acciones

            progress_Bar.setVisibility(View.VISIBLE);//Esto hace que la barra de progreso sea visible
            createAcccountBtn.setVisibility(View.GONE);//Esto oculta el botón
        }else{//Si inprogress es false
            progress_Bar.setVisibility(View.GONE);//Esto oculta la barra de progreso
            createAcccountBtn.setVisibility(View.VISIBLE);// Esto hace que el botón sea visible
        }
    }
    boolean validateData(String email, String password, String confirmPassword, String username) {
        // La función validateData probablemente contiene lógica para verificar si los datos son válidos según ciertos criterios, como si el correo electrónico tiene un formato válido o si la contraseña y su confirmación coinciden.
        //validacion de los dattos de cada input del usuario
        //verifica si la dirección de correo electrónico (email) proporcionada por elusuario tiene un formato válido
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //Si la dirección de correo electrónico no coincide con el patrón, semuestra un mensaje de error en el campo de entrada de correo electrónico
            emailEditText.setError("El correo es invalido");

            //La función devuelve false para indicar que los datos no son válidos.
            return false;
        }
        if (password.length() < 6) {//Verifica si la longitud de la contraseña (password) es menor que 6 caracteres
            //Si la contraseña es demasiado corta, se muestra un mensaje de error en el campo de entrada de contraseña
            passwordEditText.setError("El tamaño de la contraseña es invalido");

            // la función devuelve false.
            return false;
        }
        if (!password.equals(confirmPassword)) {// se verifica si la contraseña (password) y su confirmación (confirmPassword) son iguales.
            //Si las contraseñas no coinciden, se muestra un mensaje de error en el campo de entrada de confirmación de contraseña
            confirmPasswordEditText.setError("las contraseñas no son iguales");

            //La función devuelve false
            return false;
        }
        //Si ninguno de los tres casos anteriores se cumple, significa que todos los datos han pasado las validaciones y son considerados válidos
        //La función devuelve true para indicar que los datos son válidos.
        return true;
    }
}