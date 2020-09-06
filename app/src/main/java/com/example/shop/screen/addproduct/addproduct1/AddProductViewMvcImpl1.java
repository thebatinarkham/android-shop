package com.example.shop.screen.addproduct.addproduct1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.preference.PreferenceManager;

import com.example.shop.R;
import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.example.shop.screen.productlist.ProductListActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProductViewMvcImpl1 extends BaseObservableViewMvc<AddProductMvc1.Listener> implements
AddProductMvc1{
    private String ProductName, ProductCategory, ProductLocation, ProductTag;
    private int ProductPrice;
    private Button ProductSaveBtn, ProductDelete;
    private TextInputEditText ProductDesEditText;
    private TextInputLayout ProductDesLayout;
    private ChipGroup chipGroup;

    private ProgressBar progressBar;
    private String filename;
    private SharedPreferences pref;
    ArrayList<Uri> uris = new ArrayList<>();
    ArrayList<String> ProductImages = new ArrayList<>();

    private String productId;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AddProductViewMvcImpl1(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.activity_add_product1,parent,false));
        ProductName = getActivity().getIntent().getExtras().getString("productName");

        ProductPrice = Integer.parseInt(getActivity().getIntent().getExtras().getString("productPrice"));
        ProductCategory = getActivity().getIntent().getExtras().getString("productCategory");
        ProductLocation = getActivity().getIntent().getExtras().getString("productLocation");
        uris.add(Uri.parse(getActivity().getIntent().getExtras().getString("productImage1")));
        uris.add(Uri.parse(getActivity().getIntent().getExtras().getString("productImage2")));
        uris.add(Uri.parse(getActivity().getIntent().getExtras().getString("productImage3")));
        ProductSaveBtn = findViewById(R.id.productSaveBtn);
        ProductDesEditText = findViewById(R.id.productDescription);
        chipGroup = findViewById(R.id.productTag);

        ProductDesLayout = findViewById(R.id.productDescriptionLayout);
        progressBar = findViewById(R.id.progressBar);

        ProductDelete = findViewById(R.id.productDelete);

        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        productId = pref.getString("ProductId", "");
        ProductDesEditText.setText(pref.getString("ProductDesc", " "));
        ProductTag = pref.getString("ProductTag", " ");





        final String[] tagsName = getActivity().getResources().getStringArray(R.array.product_tagName);
        for(int tags = 0; tags  < tagsName.length; tags ++ ){
            final int finalTags = tags;

            Chip ProductChip = new Chip(getContext(),null,R.style.Widget_MaterialComponents_Chip_Entry);

            ProductChip.setText(tagsName[tags]);
            ProductChip.setCloseIconEnabled(true);
            ProductChip.setCheckable(true);

            chipGroup.addView(ProductChip);
            chipGroup.setSingleSelection(true);
            chipGroup.setSelectionRequired(true);

            if(ProductTag.contains(tagsName[tags]) && !ProductTag.isEmpty()){
                ProductChip.setChecked(true);
            }

            ProductChip.setChipIcon(getActivity().getDrawable(R.drawable.ic_check_circle_black_24dp));
            ProductChip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chipGroup.removeView(view);
                }
            });


            ProductChip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductTag = tagsName[finalTags];
                    Toast.makeText(getActivity(), tagsName[finalTags] , Toast.LENGTH_LONG).show();
                }
            });


        }


        ProductSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductSaveBtn.setEnabled(false);
                ProductSaveBtn.setText("Wait");

                if(validate()){
                    for (int i = 0; i < uris.size(); i++) {
                        File file = new File(uris.get(i).getPath().replace(":", ""));
                        try {
                            uploadFile(uris.get(i), file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    final Map<String, Object> products = new HashMap<>();
                    products.put("productName", ProductName);
                    products.put("productPrice", ProductPrice);
                    products.put("productCategory", ProductCategory);
                    products.put("productLocation", ProductLocation);
                    products.put("productDes", ProductDesEditText.getText().toString());
                    products.put("productTag", ProductTag);
                    products.put("productImage1", ProductImages.get(0));
                    products.put("productImage2", ProductImages.get(1));
                    products.put("productImage3", ProductImages.get(2));


                    if(productId.isEmpty()){
                        for(Listener listener:getListeners()){
                            listener.FetchProductAndStoreToDatabase(products);
                        }
                    }else{
                        for(Listener listener:getListeners()){
                            listener.EditProductAndStoreToDatabase(productId,products);
                        }
                    }


                }
            }
        });

        if (!productId.trim().isEmpty()) {
            ProductDelete.setVisibility(View.VISIBLE);

            ProductDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(Listener listener:getListeners()){
                        listener.DeleteProductFromDatabase(productId);
                    }

                }
            });

        }

    }

    private void uploadFile(Uri uri, File file) throws IOException {
        if (uris != null) {
            productId = pref.getString("ProductId", "");


            if (productId.isEmpty()) {
                filename = file.getName();

            } else {
                filename = uri.getLastPathSegment().replace("Images/", "").contains
                        (FirebaseAuth.getInstance().getUid()) ?
                        uri.getLastPathSegment().replace("Images/", "") :
                        file.getName();
            }

            ProductImages.add(filename);

            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
            byte[] readFile = readBytes(inputStream);

            for(Listener listener:getListeners()){
                listener.StoreProductImageToDatabase(readFile,filename);
            }
        }
    }

    private byte[] readBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1){
            byteBuffer.write(buffer,0,buffer.length);
        }
        return byteBuffer.toByteArray();
    }

    private Boolean validate() {
        if (ProductDesLayout.getEditText().getText().toString().trim().isEmpty()) {
            ProductDesLayout.setError("Field Cannot Be Empty.");
            return false;
        } else {
            ProductDesLayout.setError(null);
            ProductDesLayout.setErrorEnabled(false);
        }
        return true;
    }

    @Override
    public void notifyProductStoreToDatabase() {
        Toast.makeText(getContext(),"Product Successfully stored to Database",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), ProductListActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void notifyProductEditStoreSuccessFull() {
        Toast.makeText(getContext(),"Product Successfully Edited to Database",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), ProductListActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void notifyViewAboutProductImageStoreProcess(double process) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress((int) process  / 3);
    }

    @Override
    public void notifyViewAboutProductDeleteFromDatabase() {

    }
}
