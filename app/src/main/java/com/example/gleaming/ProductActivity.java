package com.example.gleaming;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.gleaming.adapters.PrendaAdapter;
import com.example.gleaming.model.Prenda;
import com.example.gleaming.model.ProductSingleResponse;

import com.example.gleaming.model.Tags;
import com.example.gleaming.network.GleamingService;
import com.example.gleaming.network.RetrofitBuilder;
import com.example.gleaming.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import me.gujun.android.taggroup.TagGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ProductActivity extends AppCompatActivity {

    //private ImageView product_img;
    private LinearLayout description;
    private TagGroup llTaggies;
    private ImageView product_img;
    private RecyclerView rvRecomendados;
    private String TAG = "ProductActivity";
    private AppCompatActivity appCompatActivity;
    private ImageView myBlurImage;
    private Button btnDevolver;
    private PrendaAdapter prendaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent =  getIntent();

        ImageView product_img = findViewById(R.id.product_img);
        product_img = findViewById(R.id.product_img);
        description = findViewById(R.id.description);
        myBlurImage = findViewById(R.id.product_image);


        RecyclerView rvRecomendados = findViewById(R.id.rv_recomended);
        prendaAdapter = new PrendaAdapter(this);
        rvRecomendados.setAdapter(prendaAdapter);

    if(intent != null){
            int id = intent.getIntExtra(getString(R.string.prenda_id), 0);
            String img = intent.getStringExtra(getString(R.string.product_img));

             Log.i(TAG, "onCreate: "+ img);
             Log.i(TAG, "onCreate: " + id);

            if(id > 0){

                description = findViewById(R.id.description);
                llTaggies = findViewById(R.id.tag_group);
                final Toolbar toolbar = findViewById(R.id.toolbar);

                toolbar.setTitle("");

                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                getPrenda(id);

                Glide.with(this)
                        .load("http://gleaming.nickgonzalezs.com/storage/images/" + img)
                        .apply(
                                new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        )
                        .transform(new FitCenter())
                        .into((ImageView) findViewById(R.id.product_img));

                Glide.with(this)
                        .load("http://gleaming.nickgonzalezs.com/storage/images/" + img)
                        .apply(bitmapTransform(new BlurTransformation(25)))
                        .into((ImageView) findViewById(R.id.product_image));

           }
        }
    }

    private void getPrendaMen(int id){
        TokenManager tokenManager = new TokenManager(this);
    }

    private void getPrenda(int id) {


        TokenManager tokenManager = new TokenManager(this);
        // Se crea el servicio, para hacer los calls
        // Se usa createServiceWithAuth, porque este metodo agrega el token al request
        GleamingService service = RetrofitBuilder.createServiceWithAuth(GleamingService.class, tokenManager);
        Call<ProductSingleResponse> productResponseCall = service.getPrenda(id);

        productResponseCall.enqueue(new Callback<ProductSingleResponse>() {
            @Override
            public void onResponse(Call<ProductSingleResponse> call, Response<ProductSingleResponse> response) {
                if(response.isSuccessful()){

                    ProductSingleResponse singleProductResponse = response.body();

                    Prenda prenda = singleProductResponse.getPrenda();
                    List<Tags> tags = singleProductResponse.getTags();

                    WebView webview = (WebView) findViewById(R.id.webView1);
                    webview.setWebViewClient(new WebViewClient());
                    webview.getSettings().setJavaScriptEnabled(true);
                    webview.loadUrl("http://maps.google.com/maps/" + "@" + prenda.getLatitude() + "," + prenda.getLongitude() + ",");

                    ArrayList<Prenda> prendas = singleProductResponse.getPrendas();
                    prendaAdapter.addPrendas(prendas);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    TextView txtDescription = new TextView(ProductActivity.this);
                    txtDescription.setLayoutParams(params);
                    txtDescription.setText(prenda.getDescription());
                    txtDescription.setTextColor(getResources().getColor(R.color.colorLightGray));
                    txtDescription.setTextSize(16);

                    description.addView(txtDescription);

                    List<String> tagString = new ArrayList<>();

                    for (Tags tag : tags) {
                        tagString.add(tag.getName());
                    }

                    llTaggies.setTags(tagString);
    }
                else{
                    Log.i(TAG, "Error: " + response.errorBody());
                }
            }



            @Override
            public void onFailure(Call<ProductSingleResponse> call, Throwable t) {
                Log.i(TAG, "Error: " + call.request().url());
                throw new RuntimeException(t);
            }
        });

    }//finalgetPrenda

   /* @Override
    public void onClick(View v) {

        Log.d(TAG, "onClick:" + v.getId());
        switch (v.getId()){
            case R.id.btn_product_back:
                Intent intent = new Intent(this, ProductActivity.class);
                break;

        }
    }*/

} //final appcom

