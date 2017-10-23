package com.app.obl.oblmobileapp.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.adaptor.BranchMapPagerAdapter;
import com.app.obl.oblmobileapp.helper.BranchLocation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BranchMapsActivity extends AppCompatActivity {

    private static final String TOOLBAR_TITLE = "Branch Location";
    private static final String TAB_NAME_LIST = "LIST";
    private static final String TAB_NAME_MAP = "MAP";

    private static final String XML_FILE_NAME = "branch_info_list.xml";
    private static final String XML_KEY_BRANCH_INIT = "branch";
    private static final String XML_KEY_BRANCH_NAME = "branchname";
    private static final String XML_KEY_BRANCH_ADDRESS = "branchaddress";
    private static final String XML_KEY_BRANCH_PHONE = "branchphone";
    private static final String XML_KEY_BRANCH_MOBILE = "branchmobile";
    private static final String XML_KEY_BRANCH_FAX = "branchfax";
    private static final String XML_KEY_BRANCH_EMAIL = "branchemail";
    private static final String XML_KEY_BRANCH_LATITIUDE = "branchlattitude";
    private static final String XML_KEY_BRANCH_LONGITUDE = "branchlongitude";

    private TabLayout tabLayout;
    private Toolbar mToolbar;

    public ArrayList<BranchLocation> br_location_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_location);

        SetTitleBar();
        SetTab();
    }

    public ArrayList<BranchLocation> GetBranchLocation() {
        return GetInfoFromXML();
    }


    private ArrayList<BranchLocation> GetInfoFromXML()
    {
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            AssetManager assetManager = getApplicationContext().getAssets();
            InputStream in_s = assetManager.open(XML_FILE_NAME);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            parseXML(parser);


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return br_location_list;
    }

    private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {

        int eventType = parser.getEventType();
        BranchLocation branchObject = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    br_location_list = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase(XML_KEY_BRANCH_INIT)){
                        branchObject = new BranchLocation();
                    }
                    else if (branchObject != null){
                        if (name.equalsIgnoreCase(XML_KEY_BRANCH_NAME)){
                            branchObject.branch_name = parser.nextText();
                        } else if (name.equalsIgnoreCase(XML_KEY_BRANCH_ADDRESS)){
                            branchObject.branch_address = parser.nextText();
                        } else if (name.equalsIgnoreCase(XML_KEY_BRANCH_PHONE)){
                            branchObject.branch_phone= parser.nextText();
                        }else if (name.equalsIgnoreCase(XML_KEY_BRANCH_MOBILE)){
                            branchObject.branch_mobile= parser.nextText();
                        }else if (name.equalsIgnoreCase(XML_KEY_BRANCH_FAX)){
                            branchObject.branch_fax= parser.nextText();
                        }else if (name.equalsIgnoreCase(XML_KEY_BRANCH_EMAIL)){
                            branchObject.branch_email= parser.nextText();
                        }
                        else if (name.equalsIgnoreCase(XML_KEY_BRANCH_LONGITUDE)){
                            branchObject.branch_longitude= parser.nextText();
                        }
                        else if (name.equalsIgnoreCase(XML_KEY_BRANCH_LATITIUDE)){
                            branchObject.branch_latitude= parser.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase(XML_KEY_BRANCH_INIT) && branchObject != null){
                        br_location_list.add(branchObject);
                    }
            }
            eventType = parser.next();
        }


    }
    private void SetTab()
    {
        tabLayout = (TabLayout) findViewById(R.id.tab_br_loc_layout);
        tabLayout.addTab(tabLayout.newTab().setText(TAB_NAME_LIST));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_NAME_MAP));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_list_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_branch_location_map);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.br_loc_pager);
        final BranchMapPagerAdapter adapter = new BranchMapPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    private void SetTitleBar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(TOOLBAR_TITLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setIcon(R.drawable.ic_toolbar_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
