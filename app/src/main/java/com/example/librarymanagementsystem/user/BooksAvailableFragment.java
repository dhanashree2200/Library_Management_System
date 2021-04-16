package com.example.librarymanagementsystem.user;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.librarymanagementsystem.DatabaseHandler;
import com.example.librarymanagementsystem.DbModel;
import com.example.librarymanagementsystem.R;
import com.example.librarymanagementsystem.admin.ModelClass;
import com.example.librarymanagementsystem.admin.ViewBookAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BooksAvailableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BooksAvailableFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rView;
    ArrayList<ModelClass> book;
    DatabaseHandler db;
    ViewBookAdapter viewBookAdapter;
    List<DbModel> list;

    public BooksAvailableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BooksAvailableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BooksAvailableFragment newInstance(String param1, String param2) {
        BooksAvailableFragment fragment = new BooksAvailableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_books_available, container, false);
        setHasOptionsMenu(true);

        db=new DatabaseHandler(getActivity());
        book=new ArrayList<>();

        rView=view.findViewById(R.id.rview);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewBookAdapter=new ViewBookAdapter(getActivity(),book);
        rView.setAdapter(viewBookAdapter);
        list=db.getAllBooks();
        for (DbModel books:list) {
            if(books.getStatus()==0) {
                ModelClass model = new ModelClass(books.getBook_id(), books.getBook_title(), books.getBook_auth(), books.getBook_pub());
                book.add(model);
            }
        }
        if(book.size()==0){
            TextView tv=new TextView(getActivity());
            tv.setText("No book issued yet");
            tv.setTextSize(20);
        }
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search,menu);
        MenuItem item=menu.findItem(R.id.searchI);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                viewBookAdapter.getFilter().filter(newText.toString());
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}