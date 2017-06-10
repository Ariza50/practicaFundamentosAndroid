package es.arizsystems.restaurante.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import es.arizsystems.restaurante.R;
import es.arizsystems.restaurante.model.Mesa;
import es.arizsystems.restaurante.model.Plato;
import es.arizsystems.restaurante.model.Restaurante;

/**
 * Created by Ariz on 3/6/17.
 */

public class TableListFragment extends Fragment {
    private final static String ARG_TABLES = "ARG_TABLES";
    protected LinkedList<Mesa> mMesas;
    protected onTableSelListener mOnTableSelListener;

    public static TableListFragment newInstance(LinkedList<Mesa> mesas) {

        TableListFragment fragment = new TableListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TABLES, mesas);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mMesas = (LinkedList<Mesa>) getArguments().getSerializable(ARG_TABLES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //  Insertamos la vista del fragment
        View root = inflater.inflate(R.layout.fragment_table_list, container, false);

        //  Cogemos el listView donde vamos a introducir la lista
        ListView lista = (ListView) root.findViewById(R.id.table_list);

        //  Creamos el objeto que va a gestionar la introducción de datos en la lista
        ArrayAdapter<Mesa> adapter = new ArrayAdapter<Mesa>(getActivity(), android.R.layout.simple_list_item_1, mMesas);

        //  Se lo pasamos a la lista que lo representará en pantalla
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Avisamos de que se ha pulsado
                if (mOnTableSelListener != null) {
                    Mesa tableSelected = mMesas.get(position);
                    //  Lanzo la función al activity que esté escuchando, si lo hay...
                    mOnTableSelListener.onTableSelected(tableSelected, position);
                }
            }
        });

        // Hacemos algo con el floating action button
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "Aquí iría el añadir una mesa", Snackbar.LENGTH_LONG).show();
            }
        });

        actualizarCarta();

        return root;
    }

    private void actualizarCarta() {

        final LinkedList<Plato> carta = Restaurante.getInstance().getPlatos();



        //  La carta está vacia, lo cual quiere decir que tenemos que descargarla de un servicio
        if(carta.size() == 0) {
            AsyncTask<Restaurante, Integer, LinkedList<Plato>> donwloadCarta = new AsyncTask<Restaurante, Integer, LinkedList<Plato>>() {

                @Override
                protected LinkedList<Plato> doInBackground(Restaurante... params) {
                    publishProgress(50);
                    return downloadForecast(params[0]);
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                }

                @Override
                protected void onPostExecute(LinkedList<Plato> platos) {
                    super.onPostExecute(platos);

                    if (platos != null) {
                        // No ha habido errores descargando la información del tiempo, actualizo la interfaz
                        Restaurante.getInstance().setPlatos(platos);

                        actualizarCarta();
                    }
                    else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("No se ha podido descargar la carta");
                        alertDialog.setPositiveButton("Reintentar?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                actualizarCarta();
                            }
                        });

                        alertDialog.show();
                    }
                }

            };

            donwloadCarta.execute(Restaurante.getInstance());

        }

    }

    private LinkedList<Plato> downloadForecast(Restaurante rest) {
        URL url = null;
        InputStream input = null;

        try {

            url = new URL(String.format("http://www.mocky.io/v2/593856ef110000a21d6bb9cf", rest.toString()));
            HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
            connexion.connect();
            byte data[] = new byte[1024];
            int downloadedBytes;
            input = connexion.getInputStream();
            StringBuilder sb = new StringBuilder();
            while ((downloadedBytes = input.read(data)) != -1) {
                sb.append(new String(data, 0, downloadedBytes));
            }

            JSONObject jsonRoot = new JSONObject(sb.toString());
            JSONArray list = jsonRoot.getJSONArray("platos");

            LinkedList<Plato> carta = new LinkedList<>();

            for (int i = 0; i < list.length(); i++) {
                JSONObject pl = list.getJSONObject(i);
                Integer id =  pl.getInt("id");
                String nombre = pl.getString("nombre");
                Integer photo = pl.getInt("photo");
                float pvp = (float) pl.getDouble("precio");

                switch (photo){
                    case 1:
                        photo = R.drawable.restaurante;
                        break;
                    case 2:
                        photo = R.drawable.restaurante;
                        break;
                    case 3:
                        photo = R.drawable.restaurante;
                        break;
                    case 4:
                        photo = R.drawable.restaurante;
                        break;
                }

                Plato plato = new Plato(nombre, id, photo, pvp);
                carta.add(plato);

            }

            return carta;

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public interface onTableSelListener {
        void onTableSelected(Mesa mesa, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof onTableSelListener) {
            mOnTableSelListener = (onTableSelListener) getActivity();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getActivity() instanceof onTableSelListener) {
            mOnTableSelListener = (onTableSelListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnTableSelListener = null;
    }
}