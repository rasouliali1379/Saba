package com.rayanandishehnasr.hmi.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rayanandishehnasr.hmi.DataHolder.CitiesDataHolder;
import com.rayanandishehnasr.hmi.Fragments.AboutDeveloperFragment;
import com.rayanandishehnasr.hmi.Fragments.ChartsFragment;
import com.rayanandishehnasr.hmi.Fragments.ErrorFragment;
import com.rayanandishehnasr.hmi.Fragments.AboutUsFragment;
import com.rayanandishehnasr.hmi.Fragments.ContactUsFragment;
import com.rayanandishehnasr.hmi.Fragments.GuideFragment;
import com.rayanandishehnasr.hmi.Fragments.HomeFragment;
import com.rayanandishehnasr.hmi.Fragments.MoreFragment;
import com.rayanandishehnasr.hmi.Fragments.NewsFragment;
import com.rayanandishehnasr.hmi.Fragments.SplashFragment;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.ViewModel.Factory.RetrofitViewModelFactory;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_activity_home_btn)ImageView homeBtn;
    @BindView(R.id.main_activity_charts_btn)ImageView chartsBtn;
    //    @BindView(R.id.main_activity_news_btn)ImageView newsBtn;
    @BindView(R.id.main_activity_more_btn)ImageView moreBtn;
    @BindView(R.id.main_activity_bottom_nav)CardView bottomNav;
    @BindView(R.id.main_activity_toolbar)CardView toolbar;
    @BindView(R.id.main_activity_toolbar_title)TextView toolbarTitle;

    RetrofitViewModel retrofitViewModel;

    int currentFragment;
    public static int selectedCityId = 1;
    public static boolean launched = false;
    boolean isHidden = false;


    Fragment homeFragment, newsFragment,
            moreFragment,
            splashFragment, aboutUsFragment,
            contactUsFragment, errorFragment,
            guideFragment, aboutDeveloperFragment, chartsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewModels();
        initObservers();
        initFragments();
        bottomNavSetup();
        if (!isHidden){
            hideToolbarFast();
            hideBottomNavFast();
        }
    }

    private void initFragments() {
        homeFragment= new HomeFragment(this, retrofitViewModel);
        newsFragment = new NewsFragment(this, retrofitViewModel);
        moreFragment = new MoreFragment(retrofitViewModel);
        splashFragment = new SplashFragment(this, retrofitViewModel);
        aboutUsFragment = new AboutUsFragment(this, retrofitViewModel);
        contactUsFragment = new ContactUsFragment(this, retrofitViewModel);
        errorFragment = new ErrorFragment(this, retrofitViewModel);
        guideFragment = new GuideFragment(this, retrofitViewModel);
        aboutDeveloperFragment = new AboutDeveloperFragment(this, retrofitViewModel);
        chartsFragment = new ChartsFragment(this, retrofitViewModel, getApplication());
    }

    private void initObservers() {
        retrofitViewModel.getSelectedFragment().observe(this, this::switchFragment);
    }

    private void initViewModels() {
        retrofitViewModel = new ViewModelProvider(this, new RetrofitViewModelFactory(getApplication())).get(RetrofitViewModel.class);
    }

    private void bottomNavSetup() {
        homeBtn.setOnClickListener(v -> switchFragment(Consts.HOME_PAGE));
//        newsBtn.setOnClickListener(v -> switchFragment(Consts.NEWS_PAGE));
        moreBtn.setOnClickListener(v -> switchFragment(Consts.MORE_PAGE));
        chartsBtn.setOnClickListener(v -> switchFragment(Consts.CHARTS_PAGE));
        switchFragment(Consts.SPLASH_PAGE);
    }

    private void switchFragment(int which){
        Fragment fragment = null;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (currentFragment != which){
            switch (which){
                case Consts.HOME_PAGE:
                    fragment = homeFragment;
                    homeBtn.setImageResource(R.drawable.ic_home_orange);
                    moreBtn.setImageResource(R.drawable.ic_more_gray);
                    chartsBtn.setImageResource(R.drawable.ic_chart_gray);
//                    newsBtn.setImageResource(R.drawable.ic_news_gray);
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_form_right);
                    toolbarTitle.setText(getResources().getString(R.string.app_name) + " - " + getSelectedItem(selectedCityId).getStrStateName());
                    if (isHidden){
                        apearBottomNav();
                        apearToolbar();
                    }
                    break;
//                case Consts.NEWS_PAGE:
//                    fragment = newsFragment;
//                    homeBtn.setImageResource(R.drawable.ic_home_gray);
//                    moreBtn.setImageResource(R.drawable.ic_more_gray);
////                    newsBtn.setImageResource(R.drawable.ic_news_orange);
//                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_form_right);
//                    toolbarTitle.setText("خبر خوان");
//                    if (isHidden){
//                        apearBottomNav();
//                        apearToolbar();
//                    }
//                    break;
                case Consts.MORE_PAGE:
                    fragment = moreFragment;
                    homeBtn.setImageResource(R.drawable.ic_home_gray);
                    moreBtn.setImageResource(R.drawable.ic_more_orange);
                    chartsBtn.setImageResource(R.drawable.ic_chart_gray);
//                    newsBtn.setImageResource(R.drawable.ic_news_gray);
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_form_right);
                    toolbarTitle.setText("موارد دیگر");
                    if (isHidden){
                        apearBottomNav();
                        apearToolbar();
                    }
                    break;
                case Consts.ABOUT_US_PAGE:
                    fragment = aboutUsFragment;
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_form_right);
                    if (!isHidden){
                        hideBottomNav();
                        hideToolbar();
                    }
                    break;
                case Consts.CONTACT_US_PAGE:
                    fragment = contactUsFragment;
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_form_right);
                    if (!isHidden){
                        hideBottomNav();
                        hideToolbar();
                    }
                    break;
                case Consts.SPLASH_PAGE:
                    fragment = splashFragment;
                    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    if (!isHidden){
//                        hideToolbarFast();
//                        hideBottomNavFast();
//                    }
                    break;

                case Consts.ERROR_PAGE:
                    fragment = errorFragment;
                    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    if (!isHidden){
                        hideBottomNav();
                        hideToolbar();
                    }
                    break;

                case Consts.GUIDE_PAGE:
                    fragment = guideFragment;
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_form_right);
                    if (!isHidden){
                        hideBottomNav();
                        hideToolbar();
                    }
                    break;
                case Consts.ABOUT_DEV_PAGE:
                    fragment = aboutDeveloperFragment;
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_form_right);
                    if (!isHidden){
                        hideBottomNav();
                        hideToolbar();
                    }
                    break;
                case Consts.CHARTS_PAGE:
                    fragment = chartsFragment;
                    toolbarTitle.setText("نمودارها");
                    homeBtn.setImageResource(R.drawable.ic_home_gray);
                    moreBtn.setImageResource(R.drawable.ic_more_gray);
                    chartsBtn.setImageResource(R.drawable.ic_chart_orange);
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_form_right);
                    if (isHidden){
                        apearBottomNav();
                        apearToolbar();
                    }
                    break;
            }

            currentFragment = which;
            transaction.replace(R.id.main_activity_root_layout, fragment);
            transaction.commit();
        }
    }

    private void hideBottomNav() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        int cal_height = (int) height * 11 / 100;

        bottomNav.animate().translationY(cal_height).alpha(0.0f).setDuration(200);

        isHidden = true;
    }

    private void apearBottomNav(){
        bottomNav.animate().translationY(0).alpha(1.0f).setDuration(200);
        isHidden = false;
    }

    private void hideToolbar() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        int cal_height = (int) height * 12 / 100;

        toolbar.animate().translationY(-cal_height).alpha(0.0f).setDuration(200);
    }

    private void apearToolbar(){
        toolbar.animate().translationY(0).alpha(1.0f).setDuration(200);
    }

    private void hideToolbarFast() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        int cal_height = (int) height * 12 / 100;
        toolbar.animate().translationY(-cal_height).alpha(0.0f).setDuration(0);
    }

    private void hideBottomNavFast() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        int cal_height = (int) height * 11 / 100;

        bottomNav.animate().translationY(cal_height).alpha(0.0f).setDuration(0);
        isHidden = true;
    }

    @Override
    public void onBackPressed() {
        switch (currentFragment){

            case Consts.HOME_PAGE:
            case Consts.SPLASH_PAGE:
            case Consts.ERROR_PAGE:
                selectedCityId = 1;
                exitApp();
                break;

            case Consts.NEWS_PAGE:
            case Consts.MORE_PAGE:
            case Consts.CHARTS_PAGE:
                retrofitViewModel.setSelectedFragment(Consts.HOME_PAGE);
                break;

            case Consts.RELATED_LINKS_PAGE:
            case Consts.GUIDE_PAGE:
            case Consts.CONTACT_US_PAGE:
            case Consts.ABOUT_US_PAGE:
                case Consts.ABOUT_DEV_PAGE:
                retrofitViewModel.setSelectedFragment(Consts.MORE_PAGE);
                break;

        }
    }

    private void exitApp(){
        new AlertDialog.Builder(this)
                .setMessage("آیا می خواهید از برنامه خارج شوید؟")
                .setPositiveButton("بله", (dialog, which) -> finish())
                .setNegativeButton("خیر", (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private CitiesDataHolder getSelectedItem(int id) {
        List<CitiesDataHolder> cities = retrofitViewModel.getCities().getValue();
        if (cities != null) {
            for (int i = 0 ; i < cities.size(); i++){
                if (id == cities.get(i).getTiState()){
                    return cities.get(i);
                }
            }
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        launched = false;
    }


}