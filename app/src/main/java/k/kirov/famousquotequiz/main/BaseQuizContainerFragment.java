package k.kirov.famousquotequiz.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import k.kirov.famousquotequiz.R;

/**
 * @author Kamen Kirov (kamen_kirov@mail.bg).
 */

public class BaseQuizContainerFragment extends Fragment {

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(R.id.container_framelayout, fragment);
        transaction.commit();
        getChildFragmentManager().executePendingTransactions();
    }

}
