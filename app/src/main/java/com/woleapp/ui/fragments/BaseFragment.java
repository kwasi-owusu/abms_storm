package com.woleapp.ui.fragments;

import android.util.DisplayMetrics;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.woleapp.R;


/**
 * Created by Vijay on 30/11/15.
 */
public class BaseFragment extends Fragment {
    public void replaceFragmentWithoutBack(int containerViewId, Fragment fragment, String fragmentTag) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .commit();
    }

    public void replaceFragmentWithBack(int containerViewId,
                                        Fragment fragment,
                                        String fragmentTag,
                                        String backStackStateName) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, android.R.anim.fade_out)
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();


    }

    public void replaceFragmentWithBack(int containerViewId,
                                        Fragment fragment,
                                        String fragmentTag) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, android.R.anim.fade_out)
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(fragment.getClass().getName())
                .commit();


    }

    public void addFragmentWithoutRemove(int containerViewId, Fragment fragment, String fragmentName) {
        String tag = fragment.getClass().getSimpleName();//getTag();
        Log.e("FragmentTag", tag + "---");
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right,
                        R.anim.right_to_left, R.anim.left_to_right)
                .add(containerViewId, fragment, fragmentName)
                .addToBackStack(tag)
                .commit();
    }

    public void replaceFragmentAddBackStack(int containerViewId, Fragment fragment, String fragmentName) {
        String tag = fragment.getClass().getSimpleName();//getTag();
        Log.e("FragmentTag", tag + "---");
        getActivity().getSupportFragmentManager().beginTransaction()
                //.setCustomAnimations(R.anim.right_to_left, android.R.anim.fade_out)
                .setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right,
                        R.anim.right_to_left, R.anim.left_to_right)
                // remove fragment from fragment manager
                //fragmentTransaction.remove(getActivity().getSupportFragmentManager().findFragmentByTag(tag));
                // add fragment in fragment manager
                .replace(containerViewId, fragment, fragmentName)
                // add to back stack
                .addToBackStack(tag)
                .commit();
    }

    public void replacefragmentWithAddStack(int containerViewId, Fragment fragment, String fragmentName) {
        String tag = (String) fragment.getTag();
        getActivity().getSupportFragmentManager().beginTransaction()
                // remove fragment from fragment manager
                //fragmentTransaction.remove(getActivity().getSupportFragmentManager().findFragmentByTag(tag));
                // add fragment in fragment manager
                .setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right,
                        R.anim.right_to_left, R.anim.left_to_right)
                .replace(containerViewId, fragment)//fragmentName
                // add to back stack
                .addToBackStack(null)
                .commit();
    }

    public void addFragmentWithRemove(int containerViewId, Fragment fragment, String fragmentName) {

        String tag = fragmentName;//(String) fragment.getTag();
        Log.e("fragmentToRemove", tag + "--");
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        Log.e("BackStackCount: ", supportFragmentManager.getBackStackEntryCount() + "--");
        Fragment frag = supportFragmentManager.findFragmentByTag(tag);
        if (frag != null) {
            Log.e("fragmentName", frag.getClass().getSimpleName() + "--");
        } else {
            Log.e("fragmentName", "Null--");
        }
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right,
                        R.anim.right_to_left, R.anim.left_to_right)
                // remove fragment from fragment manager
                .remove(supportFragmentManager.findFragmentByTag(tag))
                // add fragment in fragment manager
                .add(containerViewId, fragment)
                // add to back stack
                .addToBackStack(null)//tag
                .commit();
        Log.e("BackStackCount: ", supportFragmentManager.getBackStackEntryCount() + "--");
    }

    public void addFragmentWithoutRemoveTToB(int containerViewId, Fragment fragment, String fragmentName) {
        String tag = fragment.getClass().getSimpleName();//getTag();
        Log.e("FragmentTag", tag + "---");
        getActivity().getSupportFragmentManager().beginTransaction()
                //.setCustomAnimations(R.anim.right_to_left, android.R.anim.fade_out)
                .setCustomAnimations(R.anim.bounce, android.R.anim.fade_out,
                        R.anim.bounce, android.R.anim.fade_out)
                // remove fragment from fragment manager
                //fragmentTransaction.remove(getActivity().getSupportFragmentManager().findFragmentByTag(tag));
                // add fragment in fragment manager


                .add(containerViewId, fragment, fragmentName)
                // add to back stack
                .addToBackStack(tag)
                .commit();
    }

    public void addFragmentWithoutRemoveTToB2(int containerViewId, Fragment fragment) {
        String tag = fragment.getClass().getSimpleName();//getTag();
        Log.e("FragmentTag", tag + "---");
        getActivity().getSupportFragmentManager().beginTransaction()
                //.setCustomAnimations(R.anim.right_to_left, android.R.anim.fade_out)
                .setCustomAnimations(R.anim.bounce, android.R.anim.fade_out,
                        R.anim.bounce, android.R.anim.fade_out)
                // remove fragment from fragment manager
                //fragmentTransaction.remove(getActivity().getSupportFragmentManager().findFragmentByTag(tag));
                // add fragment in fragment manager

                .add(containerViewId, fragment, tag)
                // add to back stack
                .addToBackStack(tag)//null
                .commit();
    }
    /*setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                        R.anim.right_to_left, R.anim.left_to_right)*/

  /*  Dialog dialogOK = null, dialogValidation = null;

    public void dialogOK(Context context, String message) {
        if (dialogOK != null && dialogOK.isShowing())
            dialogOK.dismiss();
        dialogOK = new DialogOK(context, message);
        dialogOK.show();
    }

    public void dialogValidation(Context context, String title, String subTitle, ArrayList<String> messages) {
        if (dialogValidation != null && dialogValidation.isShowing())
            dialogValidation.dismiss();
        dialogValidation = new DialogValidation(context, title, subTitle, messages);
        dialogValidation.show();
    }
*/
    /**
     * Method for get screen height and width
     */
    private int width, height;

    private int[] getDeviceHeightWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        int[] i = new int[2];
        i[0] = height;
        i[1] = width;
        return i;
    }

    public void showFragment(Fragment targetFragment, String className) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right);//android.R.anim.fade_out
        ft.replace(R.id.container_main, targetFragment, className);
        ft.commitAllowingStateLoss();
    }

    public void showFragmentLtoR(Fragment targetFragment, String className) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left);//android.R.anim.fade_out
        ft.replace(R.id.container_main, targetFragment, className);
        ft.commitAllowingStateLoss();
    }
}
