package com.example.edouardroussillon.toolbar;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        Toolbar mToolbar;
        PopupMenu myPopup;
        CardView mCardView;
        ImageView mImage;
        TextView mTitle;
        TextView mSubTitle;
        LinearLayout mLayoutText;

        float defaultRadiu;
        float defaultEvelation;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            mToolbar = (Toolbar)rootView.findViewById(R.id.toolbar);

            mToolbar.setLogo(R.mipmap.ic_launcher);
            mToolbar.setTitle(getResources().getText(R.string.title));
            mToolbar.setSubtitle(getResources().getText(R.string.subtitle));

            mToolbar.inflateMenu(R.menu.menu_main);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Toast.makeText(getActivity(), "Toolbar pressed: " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            mToolbar.setNavigationIcon(R.mipmap.ic_action_hardware_keyboard_backspace);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Navigation pressed", Toast.LENGTH_SHORT).show();
                }
            });


            myPopup = new PopupMenu(getActivity(), rootView.findViewById(R.id.button));
            getActivity().getMenuInflater().inflate(R.menu.menu_main2, myPopup.getMenu());
            myPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Toast.makeText(getActivity(), "PopupMenu pressed: " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myPopup.show();
                }
            });

            mCardView = (CardView)rootView.findViewById(R.id.cardview);
            defaultRadiu = mCardView.getRadius();
            defaultEvelation = mCardView.getCardElevation();

            SwitchCompat switchCompat = (SwitchCompat)rootView.findViewById(R.id.switchCompat);
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        mCardView.setRadius(20.0f);
                        mCardView.setCardElevation(0);
                        mCardView.setCardBackgroundColor(getResources().getColor(R.color.third));

                        mImage.setImageResource(R.drawable.image2);
                        setColorText();
                    } else {
                        mCardView.setRadius(defaultRadiu);
                        mCardView.setCardElevation(defaultEvelation);
                        mCardView.setCardBackgroundColor(getResources().getColor(android.R.color.white));

                        mImage.setImageResource(R.drawable.image);
                        setColorText();
                    }

                }
            });

            mTitle = (TextView)rootView.findViewById(R.id.title);
            mSubTitle = (TextView)rootView.findViewById(R.id.subtitle);
            mImage = (ImageView)rootView.findViewById(R.id.image);
            mLayoutText = (LinearLayout)rootView.findViewById(R.id.layoutText);

            setColorText();

            return rootView;
        }

        public void setColorText() {
            Palette.generateAsync(((BitmapDrawable)mImage.getDrawable()).getBitmap(), 10, new Palette.PaletteAsyncListener() {
                public void onGenerated(Palette palette) {
                    mTitle.setTextColor(palette.getMutedColor(getResources().getColor(android.R.color.white)));
                    mSubTitle.setTextColor(palette.getVibrantColor(getResources().getColor(android.R.color.white)));
                    mLayoutText.setBackgroundColor(palette.getDarkMutedColor(getResources().getColor(android.R.color.background_light)));
                    mLayoutText.getBackground().setAlpha(135);
                }
            });
        }
    }
}
