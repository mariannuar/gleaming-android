package com.example.gleaming;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.gleaming.adapters.PrendaAdapter;
import com.example.gleaming.network.GleamingService;
import com.example.gleaming.model.Prenda;
import com.example.gleaming.model.PrendaResponse;
import com.example.gleaming.network.RetrofitBuilder;
import com.example.gleaming.utils.TokenManager;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendsFragment extends Fragment {


    private String TAG = "TrendsFragment";

    // Variable para almacenar el Contexto, como no se esta en un activity,
    // hay que recibirla por el metodo onAttach
    private AppCompatActivity activity;

    // Variable para saber si se pueden cargar mas prendas
    boolean canLoad = true;

    // El adapter que se creo de manera global
    private PrendaAdapter prendaAdapter;
    private Prenda prenda;

    // TODO: Este metodo no se usa, pero es necesario
    // Para crear nuevos fragments, usar newInstance()
    public TrendsFragment() {

    }

    // Para crear nuevas instancias se usa este metodo
    public static AllPrendasFragment newInstance() {
        AllPrendasFragment fragment = new AllPrendasFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_all_prendas, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {

            // Para usar findViewById, se tiene que usar la variable view, que viene por parametro

            // Se obtiene el RecyclerView del layout(R.layout.fragment_women)
            RecyclerView rvPrendas = view.findViewById(R.id.rv_prendas);

            // Se crea el layoutManager
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

            //Se agrega el layout al RecyclerView
            rvPrendas.setLayoutManager(staggeredGridLayoutManager);

            // Se inicializa el adapter
            prendaAdapter = new PrendaAdapter(activity);

            // Se agrega el adapter al RecyclerView
            rvPrendas.setAdapter(prendaAdapter);


            // Se cargan las prendas

            loadPrendas();

        }
    }

    public void loadPrendas(){

        // No se puede cargar
        canLoad = false;

        // Se crea el manejador del token
        TokenManager tokenManager = new TokenManager(activity);

        // Se crea el servicio, para hacer los calls
        // Se usa createServiceWithAuth, porque este metodo agrega el token al request
        GleamingService service = RetrofitBuilder.createServiceWithAuth(GleamingService.class, tokenManager);

        // Se crea el call, para llamar el metodo necesario
        Call<PrendaResponse> prendaResponseCall = service.prendasAll();

        // Se ejecuta el call
        prendaResponseCall.enqueue(new Callback<PrendaResponse>() {
            @Override
            public void onResponse(Call<PrendaResponse> call, Response<PrendaResponse> response) {

                if(response.isSuccessful()){

                    PrendaResponse prendaResponse = response.body();

                    ArrayList<Prenda> prendas = prendaResponse.getResults();

                    prendaAdapter.addPrendas(prendas);
                    canLoad = true;

                } else{
                    Log.i(TAG, response.message());
                    canLoad = true;
                }
            }

            @Override
            public void onFailure(Call<PrendaResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
