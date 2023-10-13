package com.example.personalcosts;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utilidad {
    static CollectionReference getCollectionReferenceForCategory(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("categorias")
                .document(currentUser.getUid()).collection("my_category");
    }
}
