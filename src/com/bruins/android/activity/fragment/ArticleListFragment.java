package com.bruins.android.activity.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.MenuItem;
import com.bruins.android.R;
import com.bruins.android.activity.activity.MainActivity;
import com.bruins.android.activity.adapter.ArticleListAdapter;
import com.bruins.android.activity.db.DbAdapter;
import com.bruins.android.activity.rss.domain.Article;
import com.bruins.android.activity.rss.parser.RssHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ArticleListFragment extends SherlockListFragment implements PullToRefreshAttacher.OnRefreshListener{

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
//    private static final String BLOG_URL = "http://blog.nerdability.com/feeds/posts/default";
    private static final String BLOG_URL = "http://bgwfans.com/feed/";
    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;
    private RssService rssService;
    private MenuItem refresh;
    private PullToRefreshAttacher mPullToRefreshAttacher;
    Context mContext;

    public interface Callbacks {
        public void onItemSelected(String id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    public ArticleListFragment() {
//    	setHasOptionsMenu(true);	//this enables us to set actionbar from fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshList();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
        mContext = getActivity().getBaseContext();
        mPullToRefreshAttacher = ((MainActivity) getSherlockActivity()).getPullToRefreshAttacher();
        mPullToRefreshAttacher.addRefreshableView(getListView(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onRefreshStarted(View view) {
        refreshList();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mPullToRefreshAttacher.setRefreshComplete();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallbacks.onItemSelected(String.valueOf(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }
        mActivatedPosition = position;
    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        refresh = menu.findItem(R.id.actionbar_refresh);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.refreshmenu, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.actionbar_refresh) {
//        	refreshList();
//        	return true;
//        }
//        return false;
//    }

    private void refreshList(){
    	rssService = new RssService(this);
        rssService.execute(getResources().getString(R.string.feed_url));
    }

    @Override
    public void onStop(){
        super.onStop();
        rssService.cancel(true);
    }

    @Override
    public void onPause(){
        super.onPause();
        rssService.cancel(true);
    }

    public class RssService extends AsyncTask<String, Void, List<Article>> {

        private ProgressDialog progress;
        private Context context;
        private ArticleListFragment articleListFrag;

        public RssService(ArticleListFragment articleListFragment) {
            context = articleListFragment.getActivity();
            articleListFrag = articleListFragment;
            progress = new ProgressDialog(context);
            progress.setMessage("Loading...");
        }


        protected void onPreExecute() {
            Log.e("ASYNC", "PRE EXECUTE");
//		progress.show();
        }


        protected  void onPostExecute(final List<Article>  articles) {
            Log.e("ASYNC", "POST EXECUTE");
            articleListFrag.getSherlockActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (Article a : articles){
                        Log.d("DB", "Searching DB for GUID: " + a.getGuid());
                        DbAdapter dba = new DbAdapter(articleListFrag.getSherlockActivity());
                        dba.openToRead();
                        Article fetchedArticle = dba.getBlogListing(a.getGuid());
                        dba.close();
                        if (fetchedArticle == null){
                            Log.d("DB", "Found entry for first time: " + a.getTitle());
                            dba = new DbAdapter(articleListFrag.getSherlockActivity());
                            dba.openToWrite();
                            dba.insertBlogListing(a.getGuid());
                            dba.close();
                        }else{
                            a.setDbId(fetchedArticle.getDbId());
                            a.setOffline(fetchedArticle.isOffline());
                            a.setRead(fetchedArticle.isRead());
                        }
                    }
                    ArticleListAdapter adapter = new ArticleListAdapter(articleListFrag.getSherlockActivity(), articles);
                    articleListFrag.setListAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            });
            progress.dismiss();
            mPullToRefreshAttacher.setRefreshComplete();
        }


        @Override
        protected List<Article> doInBackground(String... urls) {
            String feed = urls[0];

            URL url = null;
            try {

                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();

                url = new URL(feed);
                RssHandler rh = new RssHandler();

                xr.setContentHandler(rh);
                xr.parse(new InputSource(url.openStream()));


                Log.e("ASYNC", "PARSING FINISHED");
                return rh.getArticleList();

            } catch (IOException e) {
                Log.e("RSS Handler IO", e.getMessage() + " >> " + e.toString());
            } catch (SAXException e) {
                Log.e("RSS Handler SAX", e.toString());
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                Log.e("RSS Handler Parser Config", e.toString());
            }
            return null;
        }
    }
}
