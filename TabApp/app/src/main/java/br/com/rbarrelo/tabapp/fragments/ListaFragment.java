package br.com.rbarrelo.tabapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import br.com.rbarrelo.tabapp.R;
import de.greenrobot.event.EventBus;

/**
 * Created by rafaelbarrelo on 11/28/15.
 */
public abstract class ListaFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected TextView vazio;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vazio = (TextView) view.findViewById(R.id.aviso_vazio);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.color_primary_blue,
                                                R.color.color_accent_pink,
                                                R.color.color_primary_blue_dark);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupRecyclerView();
            }
        });
    }

    protected void refreshFinished(){
        swipeRefreshLayout.setRefreshing(false);
    }

    protected abstract void setupRecyclerView();

    protected void listaVazia(){
        vazio.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    protected void listaPreenchida(){
        vazio.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }
}