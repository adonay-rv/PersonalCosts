package com.example.personalcosts;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UtilidadCompra {
    static CollectionReference getCollectionReferenceForCompra(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notas1")
                .document(currentUser.getUid()).collection("my_notes1");
    }
}
