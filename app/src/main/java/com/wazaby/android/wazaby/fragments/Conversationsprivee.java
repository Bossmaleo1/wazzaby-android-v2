package com.wazaby.android.wazaby.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wazaby.android.wazaby.R;
import com.wazaby.android.wazaby.adapter.ConversationsAdapter;
import com.wazaby.android.wazaby.appviews.MessageConstitution;
import com.wazaby.android.wazaby.appviews.UserOnline;
import com.wazaby.android.wazaby.model.Const;
import com.wazaby.android.wazaby.model.Database.SessionManager;
import com.wazaby.android.wazaby.model.dao.DatabaseHandler;
import com.wazaby.android.wazaby.model.data.Categorie_prob;
import com.wazaby.android.wazaby.model.data.ConversationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bossmaleo on 02/11/17.
 */

public class Conversationsprivee extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ConversationsAdapter allUsersAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<ConversationItem> data = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;
    private JSONObject object;
    private Snackbar snackbar;
    private Resources res;
    private Context context;
    private DatabaseHandler database;
    private SessionManager session;

    /*public static List<ConversationItem> getData() {
        List<ConversationItem> data = new ArrayList<>();
        ConversationItem FriendItem1 = new ConversationItem("Lorem ipsum dolor sit amet Dolore ex deserunt aute fugiat aute nulla ea sunt aliqua nisi cupidatat eu. Duis nulla tempor do aute et eiusmod velit exercitation nostrud quis","Rivel Babindamana",R.drawable.ic_lens_black_18dp,R.drawable.rivel,R.color.greencolor);
        ConversationItem FriendItem2 = new ConversationItem("Lorem ipsum dolor sit amet Dolore ex deserunt aute fugiat aute nulla ea sunt aliqua nisi cupidatat eu. Duis nulla tempor do aute et eiusmod velit exercitation nostrud quis","Clen Dorel",R.drawable.ic_lens_black_18dp,R.drawable.kobe,R.color.primarygraydark);
        ConversationItem FriendItem3 = new ConversationItem("Lorem ipsum dolor sit amet Dolore ex deserunt aute fugiat aute nulla ea sunt aliqua nisi cupidatat eu. Duis nulla tempor do aute et eiusmod velit exercitation nostrud quis","Personne Anonyme",R.drawable.ic_help_black_18dp,R.drawable.regisanonyme,android.R.color.black);
        data.add(FriendItem1);
        data.add(FriendItem2);
        data.add(FriendItem3);

        return data;
    }*/

    /*public void Liste()
    {
        ConversationItem FriendItem1 = new ConversationItem("Lorem ipsum dolor sit amet Dolore ex deserunt aute fugiat aute nulla ea sunt aliqua nisi cupidatat eu. Duis nulla tempor do aute et eiusmod velit exercitation nostrud quis","Rivel Babindamana",R.drawable.ic_lens_black_18dp,R.drawable.rivel,R.color.greencolor);
        ConversationItem FriendItem2 = new ConversationItem("Lorem ipsum dolor sit amet Dolore ex deserunt aute fugiat aute nulla ea sunt aliqua nisi cupidatat eu. Duis nulla tempor do aute et eiusmod velit exercitation nostrud quis","Clen Dorel",R.drawable.ic_lens_black_18dp,R.drawable.kobe,R.color.primarygraydark);
        ConversationItem FriendItem3 = new ConversationItem("Lorem ipsum dolor sit amet Dolore ex deserunt aute fugiat aute nulla ea sunt aliqua nisi cupidatat eu. Duis nulla tempor do aute et eiusmod velit exercitation nostrud quis","Personne Anonyme",R.drawable.ic_help_black_18dp,R.drawable.regisanonyme,android.R.color.black);
        data.add(FriendItem1);
        data.add(FriendItem2);
        data.add(FriendItem3);
    }*/

    public Conversationsprivee() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View bossmaleo =  inflater.inflate(R.layout.conversationsprivee, container, false);
        fab = (FloatingActionButton)bossmaleo.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UserOnline.class);
                startActivity(intent);
            }
        });
        context = getActivity();
        res = getResources();
        database = new DatabaseHandler(getActivity());
        session = new SessionManager(getActivity());
        progressBar = (ProgressBar)bossmaleo.findViewById(R.id.progressbar);
        coordinatorLayout = (CoordinatorLayout)bossmaleo.findViewById(R.id.coordinatorLayout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) bossmaleo.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView)bossmaleo.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allUsersAdapter = new ConversationsAdapter(getActivity(),data);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(allUsersAdapter);
        ConnexionProblematique();
        recyclerView.addOnItemTouchListener(new Conversationsprivee.RecyclerTouchListener(getActivity(), recyclerView, new Conversationsprivee.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(),MessageConstitution.class);
                intent.putExtra("name",data.get(position).getFriendLibelle());
                intent.putExtra("imageview",data.get(position).getImageID());
                intent.putExtra("ID",data.get(position).getID());
                intent.putExtra("KEYPUSH",data.get(position).getKEYPUSH());
                intent.putExtra("PHOTO",data.get(position).getPHOTO());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return bossmaleo;
    }

    @Override
    public void onRefresh() {

        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                allUsersAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Conversationsprivee.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Conversationsprivee.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }

    private void ConnexionProblematique()
    {
        progressBar.setVisibility(View.VISIBLE);
        //TestProgressBar();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_MESSAGE_PRIVEE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray reponse = null;

                        // Toast.makeText(getActivity()," "+response,Toast.LENGTH_LONG).show();
                        try {
                            reponse = new JSONArray(response);
                            for(int i = 0;i<reponse.length();i++)
                            {
                                object = reponse.getJSONObject(i);

                                data.add(new ConversationItem(context,object.getInt("Id"), object.getString("Libelle"),object.getString("Prenom")+" "+object.getString("Nom"),R.drawable.ic_lens_black_18dp,object.getString("Photo"),R.color.greencolor,object.getString("KEYPUSH"),object.getString("PHOTO")));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.GONE);
                        allUsersAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(error instanceof ServerError)
                        {
                            snackbar = Snackbar
                                    .make(coordinatorLayout, res.getString(R.string.error_volley_servererror), Snackbar.LENGTH_LONG)
                                    .setAction(res.getString(R.string.try_again), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ConnexionProblematique();
                                        }
                                    });

                            snackbar.show();
                            progressBar.setVisibility(View.GONE);
                        }else if(error instanceof NetworkError)
                        {
                            snackbar = Snackbar
                                    .make(coordinatorLayout, res.getString(R.string.error_volley_servererror), Snackbar.LENGTH_LONG)
                                    .setAction(res.getString(R.string.try_again), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ConnexionProblematique();
                                        }
                                    });

                            snackbar.show();
                            progressBar.setVisibility(View.GONE);
                        }else if(error instanceof AuthFailureError)
                        {
                            snackbar = Snackbar
                                    .make(coordinatorLayout, res.getString(R.string.error_volley_servererror), Snackbar.LENGTH_LONG)
                                    .setAction(res.getString(R.string.try_again), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ConnexionProblematique();
                                        }
                                    });

                            snackbar.show();
                            progressBar.setVisibility(View.GONE);
                        }else if(error instanceof ParseError)
                        {
                            snackbar = Snackbar
                                    .make(coordinatorLayout, res.getString(R.string.error_volley_servererror), Snackbar.LENGTH_LONG)
                                    .setAction(res.getString(R.string.try_again), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ConnexionProblematique();
                                        }
                                    });

                            snackbar.show();
                            progressBar.setVisibility(View.GONE);
                        }else if(error instanceof NoConnectionError)
                        {
                            snackbar = Snackbar
                                    .make(coordinatorLayout, res.getString(R.string.error_volley_noconnectionerror), Snackbar.LENGTH_LONG)
                                    .setAction(res.getString(R.string.try_again), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ConnexionProblematique();
                                        }
                                    });

                            snackbar.show();
                            progressBar.setVisibility(View.GONE);
                        }else if(error instanceof TimeoutError)
                        {
                            snackbar = Snackbar
                                    .make(coordinatorLayout, res.getString(R.string.error_volley_timeouterror), Snackbar.LENGTH_LONG)
                                    .setAction(res.getString(R.string.try_again), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ConnexionProblematique();
                                        }
                                    });
                            snackbar.show();
                            progressBar.setVisibility(View.GONE);
                        }else
                        {
                            snackbar = Snackbar
                                    .make(coordinatorLayout, res.getString(R.string.error_volley_error), Snackbar.LENGTH_LONG)
                                    .setAction(res.getString(R.string.try_again), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ConnexionProblematique();
                                        }
                                    });

                            snackbar.show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("ID",String.valueOf(database.getUSER(Integer.valueOf(session.getUserDetail().get(SessionManager.Key_ID))).getID()));
                params.put("IDProb",String.valueOf(database.getUSER(Integer.valueOf(session.getUserDetail().get(SessionManager.Key_ID))).getIDPROB()));
                params.put("web","0");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
