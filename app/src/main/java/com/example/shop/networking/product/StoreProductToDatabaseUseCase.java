package com.example.shop.networking.product;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shop.R;
import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

public class StoreProductToDatabaseUseCase extends BaseObservableViewMvc<StoreProductToDatabaseUseCase.Listener> {

    public interface Listener {
        void notifyViewAboutProductStoredSuccessFully(DocumentReference result);

        void notifyViewAboutProductStoredFailedWithException(Exception e);

        void notifyViewAboutEditStoredProductSuccessFully(Void result);
        void notifyViewAboutEditStoredProductFailed(Exception e);


        void notifyViewAboutStoreProductImageProcessResult(double progress);

        void notifyViewAboutProductDeleteSuccessfully(Task<Void> task);
    }


    public void StoreProductToDatabase(Map<String, Object> product){
        FirebaseFirestore.getInstance().collection("products").add(product)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        notifyViewAboutStoreProductToDatabaseSuccess(task.getResult());
                        Log.d("Tag", "Successfully Add product to Firebase");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        notifyViewAboutStoreProductToDatabaseFailed(e);
                    }
                });
    }

    private void notifyViewAboutStoreProductToDatabaseFailed(Exception e) {
        for (Listener listener:getListeners()){
            listener.notifyViewAboutProductStoredFailedWithException(e);
        }
    }

    private void notifyViewAboutStoreProductToDatabaseSuccess(DocumentReference result) {
        for (Listener listener:getListeners()){
            listener.notifyViewAboutProductStoredSuccessFully(result);
        }
    }

    public void EditStoreProductFromDatabase(String productId,Map<String, Object> product){
        FirebaseFirestore.getInstance().collection("products").document(productId).update(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        notifyViewAboutEditStoredProductFromDatabaseSuccess(task);

                        Log.d("Tag", "Successfully Add product to Firebase");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                notifyViewAboutEditStoredProductFromDatabaseFailed(e);
            }
        });


    }

    private void notifyViewAboutEditStoredProductFromDatabaseFailed(Exception e) {
        for (Listener listener:getListeners()){
            listener.notifyViewAboutEditStoredProductFailed(e);
        }
    }

    private void notifyViewAboutEditStoredProductFromDatabaseSuccess(Task<Void> result)
    {
        for (Listener listener:getListeners()){
            listener.notifyViewAboutEditStoredProductSuccessFully(result.getResult());
        }
    }


    public void StoreProductImageAndNotify(byte[] readFile,String filename){

            FirebaseStorage.getInstance().getReference("Images/").
                    child(filename).putBytes(readFile)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.v("-\\Image.", "Product-Image Added To Firestore.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            notifyVIewAboutStoreProductImageProcess(progress);

                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            UploadTask.TaskSnapshot result = task.getResult();
                            StorageReference storage1 = result.getStorage();
                            Uri uri1 = result.getUploadSessionUri();
                        }
                    });
        }

    private void notifyVIewAboutStoreProductImageProcess(double progress) {
        for(Listener listener :getListeners()){
            listener.notifyViewAboutStoreProductImageProcessResult(progress);
        }
    }

    public void DeleteProductFromDatabase(String productId){
        FirebaseFirestore.getInstance().collection("products").document(productId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                notifyViewAboutProductDeleteSuccessFully(task);
            }
        });
    }

    private void notifyViewAboutProductDeleteSuccessFully(Task<Void> task) {
        for(Listener listener:getListeners()){
            listener.notifyViewAboutProductDeleteSuccessfully(task);
        }

    }
}

