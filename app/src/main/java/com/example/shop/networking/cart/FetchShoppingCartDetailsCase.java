package com.example.shop.networking.cart;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shop.common.BaseObservable;
import com.example.shop.screen.model.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FetchShoppingCartDetailsCase extends BaseObservable<FetchShoppingCartDetailsCase.Listener> {
    private FirebaseAuth user = FirebaseAuth.getInstance();

    //notifier
    public interface Listener {
        void onCartFetch(List<Cart> cart);

        void onProductRemoveFromCart();

        void onProductQtyIncreaseSuccess();

        void onProductQtyIncreaseFailed(Exception e);


        void onProductQtyDecreaseSuccess();

        void onProductQtyDecreaseFailed(Exception e);
    }


    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void FetchCartAndNotify(final Activity activity){
        //ref document shopping-cart

        db.collection("shopping-cart")
                .document(user.getUid()).collection("products")
        .addSnapshotListener(activity, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                //Prevent Errors
                if(e != null){
                    Toast.makeText(activity,"Edit Firebase Database Rules.",Toast.LENGTH_LONG).show();
                }
                //populating cart
                final List<Cart> models = new ArrayList<>();
                if(queryDocumentSnapshots != null){
                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        Cart model = documentSnapshot.toObject(Cart.class);
                        String ProductId = documentSnapshot.getId();
                        String ProductName = model.getProductName();
                        String ProductDes = model.getProductDes();
                        String ProductImage = model.getProductImage1();
                        int ProductPrice = ((int) model.getProductPrice());
                        int ProductQty = model.getProductQty();

                        //Populating Shopping cart
                        models.add(new Cart(ProductId,ProductName,ProductPrice,ProductDes,ProductImage,ProductQty));

                    }
                    notifySuccess(models);

                }
            }
        });

    }

    public void RemoveProductFromCart(final Cart cart, final Context mContext){
        db.collection("shopping-cart").document(user.getUid()).
                collection("products")
                .document(cart.getProductId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                notifyProductRemove();
            }
        });
    }

    public void IncreaseProductQtyInCart(Cart cart, Map<String,Object> productInCart){
        db.collection("shopping-cart").document(user.getUid()).collection("products")
                .document(cart.getProductId()).update(productInCart)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        notifySuccessIncreaseProductQty();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                notifyFailureIncreaseProductQty(e);

            }
        });

    }

    private void notifyFailureIncreaseProductQty(Exception e) {
        for(FetchShoppingCartDetailsCase.Listener listener :getListeners()){
            listener.onProductQtyIncreaseFailed(e);
        }
    }

    private void notifySuccessIncreaseProductQty() {
        for(FetchShoppingCartDetailsCase.Listener listener :getListeners()){
            listener.onProductQtyIncreaseSuccess();
        }
    }

    public void DecreaseProductQtyInCart(Cart cart, Map<String ,Object> productInCart){
        db.collection("shopping-cart").document(user.getUid()).collection("products")
                .document(cart.getProductId()).update(productInCart).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                notifySuccessDecreaseProductQty();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                notifyFailureInDecreaseProductQty(e);
            }
        });
    }

    private void notifyFailureInDecreaseProductQty(Exception e) {
        for(FetchShoppingCartDetailsCase.Listener listener :getListeners()){
            listener.onProductQtyDecreaseFailed(e);
        }
    }

    private void notifySuccessDecreaseProductQty() {
        for(FetchShoppingCartDetailsCase.Listener listener:getListeners()){
            listener.onProductQtyDecreaseSuccess();
        }
    }

    private void notifyProductRemove() {
        for(FetchShoppingCartDetailsCase.Listener listener:getListeners()){
            listener.onProductRemoveFromCart();
        }
    }

    private void notifySuccess(List<Cart> cart) {
        //for listener
        for(FetchShoppingCartDetailsCase.Listener listener:getListeners()){
            listener.onCartFetch(cart);
        }
    }
}
