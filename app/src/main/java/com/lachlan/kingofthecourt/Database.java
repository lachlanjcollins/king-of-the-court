package com.lachlan.kingofthecourt;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

// @TODO: Check how to implement this class...

public class Database {
    private FirebaseFirestore db;
    private DocumentSnapshot doc;

    public Database() {
        db = FirebaseFirestore.getInstance();
    }

    public CollectionReference getCollection(String path) {
        return db.collection(path);
    }

    public DocumentSnapshot getDocument(String collectionPath, String documentPath) {
        db.collection(collectionPath).document(documentPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    doc = task.getResult();
                }
            }
        });
        return doc;
    }
}
