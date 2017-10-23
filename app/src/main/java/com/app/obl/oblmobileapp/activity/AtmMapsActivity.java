package com.app.obl.oblmobileapp.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.adaptor.AtmMapPagerAdapter;
import com.app.obl.oblmobileapp.helper.AtmLocation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AtmMapsActivity extends AppCompatActivity {

    private static final String TOOLBAR_TITLE = "ATM Location";
    private static final String TAB_NAME_LIST = "LIST";
    private static final String TAB_NAME_MAP = "MAP";
    
    private static final String XML_FILE_NAME = "atm_info_list.xml";
    private static final String XML_KEY_ATM_INIT = "atm";
    private static final String XML_KEY_ATM_NAME = "atmname";
    private static final String XML_KEY_ATM_ADDRESS = "atmaddress";
    private static final String XML_KEY_ATM_THANA = "atmthana";
    private static final String XML_KEY_ATM_DISTRICT = "atmdistrict";
    private static final String XML_KEY_ATM_DIVISION = "atmdivision";
    private static final String XML_KEY_ATM_LATITIUDE = "atmlattitude";
    private static final String XML_KEY_ATM_LONGITUDE = "atmlongitude";
    
    private TabLayout tabLayout;
    private Toolbar mToolbar;
    public ArrayList<AtmLocation> atm_location_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_location);

        SetTitleBar();
        SetTab();
    }

    public ArrayList<AtmLocation> GetAtmLocation() {
        return GetInfoFromXML();
    }


    private ArrayList<AtmLocation> GetInfoFromXML()
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
        return atm_location_list;
    }

    private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {

        int eventType = parser.getEventType();
        AtmLocation atmObject = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    atm_location_list = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase(XML_KEY_ATM_INIT)){
                        atmObject = new AtmLocation();
                    }
                    else if (atmObject != null){
                        if (name.equalsIgnoreCase(XML_KEY_ATM_NAME)){
                            atmObject.atm_name = parser.nextText();
                        } else if (name.equalsIgnoreCase(XML_KEY_ATM_ADDRESS)){
                            atmObject.atm_address = parser.nextText();
                        } else if (name.equalsIgnoreCase(XML_KEY_ATM_THANA)){
                            atmObject.atm_thana= parser.nextText();
                        }else if (name.equalsIgnoreCase(XML_KEY_ATM_DISTRICT)){
                            atmObject.atm_district= parser.nextText();
                        }else if (name.equalsIgnoreCase(XML_KEY_ATM_DIVISION)){
                            atmObject.atm_division= parser.nextText();
                        }
                        else if (name.equalsIgnoreCase(XML_KEY_ATM_LONGITUDE)){
                            atmObject.atm_longitude= parser.nextText();
                        }
                        else if (name.equalsIgnoreCase(XML_KEY_ATM_LATITIUDE)){
                            atmObject.atm_latitude= parser.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase(XML_KEY_ATM_INIT) && atmObject != null){
                        atm_location_list.add(atmObject);
                    }
            }
            eventType = parser.next();
        }


    }
    private void SetTab()
    {
        tabLayout = (TabLayout) findViewById(R.id.tab_atm_loc_layout);
        tabLayout.addTab(tabLayout.newTab().setText(TAB_NAME_LIST));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_NAME_MAP));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_list_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_branch_location_map);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.atm_loc_pager);
        final AtmMapPagerAdapter adapter = new AtmMapPagerAdapter
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
