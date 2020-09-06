package com.example.shop.screen.addproduct.addproduct;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.example.shop.R;
import com.example.shop.screen.addproduct.addproduct1.AddProductActivity1;
import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.example.shop.screen.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class AddProductViewMvcImpl extends BaseObservableViewMvc<AddProductMvc.Listener> implements
             AddProductMvc{


    private TextInputLayout textInputLayout,ProductNameLayout,ProductPriceLayout,ProductCategoryLayout,ProductLocationLayout;
    private TextInputEditText ProductName, ProductPrice, ProductLocation, ProductDetails;
    private ImageView ProductImage1, ProductImage2, ProductImage3;
    private ProgressDialog progress;
    private StorageReference storage;
    private Uri imageUri1,imageUri2,imageUri3;
    private EditText ProductCategory;
    private Button NavigateToProductPage;

    private static final int GALLERY_REQUEST = 1;

    private AutoCompleteTextView DropdownText;
    private FragmentManager mFragmentManger;
    ArrayList<Uri> FetchProductImage = new ArrayList<>();
    ArrayList<Uri> CompressedImageUri = new ArrayList<>();
    private File file;
    private File CompressedImage;
    private Product product;



    public AddProductViewMvcImpl(LayoutInflater mLayoutInflater, ViewGroup parent, ViewMvcFactory viewMvcFactory,
                                 final FragmentManager fragmentManager)  {

        setRootView(mLayoutInflater.inflate(R.layout.activity_add_product,parent,false));
        this.mFragmentManger = fragmentManager;
        NavigateToProductPage = findViewById(R.id.navToProductPage);
        ProductName = findViewById(R.id.productName);
        ProductPrice = findViewById(R.id.productPrice);
        ProductCategory = findViewById(R.id.dropdown_Text);
        ProductLocation = findViewById(R.id.productLocation);
        ProductImage1 = findViewById(R.id.imageHeader);
        ProductImage2 = findViewById(R.id.sideImage1);
        ProductImage3 = findViewById(R.id.sideImage2);
        ProductNameLayout = findViewById(R.id.productNameLayout);
        ProductPriceLayout = findViewById(R.id.productPriceLayout);
        ProductCategoryLayout = findViewById(R.id.productCategoryLayout);
        ProductLocationLayout = findViewById(R.id.productLocationLayout);

        textInputLayout = findViewById(R.id.productCategoryLayout);
        DropdownText = findViewById(R.id.dropdown_Text);
        String[] items = new String[]{
                "Furniture",
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.dropdown_item,
                items
        );
        DropdownText.setAdapter(adapter);
        //remove preference before create new
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref.edit().remove("ProductDesc").commit();
        pref.edit().remove("ProductId").commit();
        pref.edit().remove("ProductTag").commit();


        ProductImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BSImagePicker multiSelectionPicker = new BSImagePicker.Builder("com.example.shop.fileprovider")
                        .isMultiSelect()
                        .setMinimumMultiSelectCount(3)
                        .setMaximumMultiSelectCount(3)
                        .setOverSelectTextColor(R.color.colorAccent)
                        .disableOverSelectionMessage()
                        .setMultiSelectDoneTextColor(R.color.colorPrimary)
                        .build();

                multiSelectionPicker.show(fragmentManager, "picker");

            }
        });

        NavigateToProductPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    Intent intent = new Intent(getContext(), AddProductActivity1.class);
                    intent.putExtra("productName", ProductName.getText().toString());
                    intent.putExtra("productPrice", ProductPrice.getText().toString());
                    intent.putExtra("productCategory", ProductCategory.getText().toString());
                    intent.putExtra("productLocation", ProductLocation.getText().toString());
                    intent.putExtra("productImage1",FetchProductImage.get(0).toString());
                    intent.putExtra("productImage2",FetchProductImage.get(1).toString());
                    intent.putExtra("productImage3",FetchProductImage.get(2).toString());
                    getContext().startActivity(intent);
                }
            }
        });
    }



    private Boolean validate() {
        if(ProductNameLayout.getEditText().getText().toString().trim().isEmpty()){
            ProductNameLayout.setError("Field Cannot be Empty");
            return false;
        }else{
            ProductNameLayout.setError(null);
            ProductNameLayout.setErrorEnabled(false);
        }
        if(ProductPriceLayout.getEditText().getText().toString().trim().isEmpty()){
            ProductPriceLayout.setError("Field Cannot be Empty");
            return false;
        }else{
            ProductPriceLayout.setError(null);
            ProductPriceLayout.setErrorEnabled(false);
        }
        if(ProductLocationLayout.getEditText().getText().toString().trim().isEmpty()){
            ProductLocationLayout.setError("Field Cannot Be Empty");
            return false;
        }else{
            ProductLocationLayout.setError(null);
            ProductLocationLayout.setErrorEnabled(false);
        }
        if(ProductCategoryLayout.getEditText().getText().toString().trim().isEmpty()){
            ProductCategoryLayout.setError("Field Cannot be Empty");
            return false;
        }else{
            ProductCategory.setError(null);
            ProductCategoryLayout.setErrorEnabled(false);
        }
        if(FetchProductImage.isEmpty()){
            Toast.makeText(getContext(),"Select Image By Click Icon.",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;


    }

    @Override
    public void FetchProductForEditResult(Product product) {
        //product result for edit
        if(product != null){
            final ArrayList<String> ProductImages = new ArrayList<>();
            ProductImages.add(product.getProductImage1());
            ProductImages.add(product.getProductImage2());
            ProductImages.add(product.getProductImage3());


            for(int i = 0 ; i < ProductImages.size(); i++){
                StorageReference imageRef = FirebaseStorage.getInstance().getReference()
                        .child("Images").child(ProductImages.get(i));
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FetchProductImage.add(uri);
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(FetchProductImage.size() >= 3){
                            Glide.with(getContext()).load(FetchProductImage.get(0)).into(ProductImage1);
                            Glide.with(getContext()).load(FetchProductImage.get(1)).into(ProductImage2);
                            Glide.with(getContext()).load(FetchProductImage.get(2)).into(ProductImage3);
                            ProductImage3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }
                    }
                });
            }
            ProductName.setText(product.getProductName());
            ProductPrice.setText(String.valueOf(product.getProductPrice()));
            ProductLocation.setText(product.getProductLocation());
            ProductCategory.setText(product.getProductCategory());

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("ProductId", product.getProductId());
            editor.putString("ProductDesc", product.getProductDes());
            editor.putString("ProductTag", product.getProductTag());
            editor.commit();

            ProductImage3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BSImagePicker multiSelectionPicker = new BSImagePicker.Builder("com.example.shop.fileprovider")
                            .isMultiSelect()
                            .setMinimumMultiSelectCount(3)
                            .setMaximumMultiSelectCount(3)
                            .setOverSelectTextColor(R.color.colorAccent)
                            .disableOverSelectionMessage()
                            .setMultiSelectDoneTextColor(R.color.colorPrimary)
                            .build();

                    multiSelectionPicker.show(mFragmentManger, "picker");

                }
            });
        }


    }


    @Override
    public void bindProductsImages(ArrayList<Uri> ProductImageUri) {
        //from activity multi-image listener not support in child view
        this.FetchProductImage= ProductImageUri;
        ProductImage1.setImageURI(ProductImageUri.get(0));
        ProductImage2.setImageURI(ProductImageUri.get(1));
        ProductImage3.setImageURI(ProductImageUri.get(2));
        ProductImage3.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }


}
