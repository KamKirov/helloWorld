package k.kirov.famousquotequiz.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import k.kirov.famousquotequiz.R;

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    boolean binary;
    Editor editorMode;
    SharedPreferences prefMode;

    public interface ISettings {
        void onSettings(boolean action);
    }

    ISettings callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (ISettings) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Switch mode = (Switch) view.findViewById(R.id.switch_mode);

        prefMode = getActivity().getSharedPreferences("quiz_mode", Context.MODE_PRIVATE);
        editorMode = prefMode.edit();

        binary = prefMode.getBoolean("binary", false);

        mode.setChecked(binary);

        mode.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Save the chosen mode
        editorMode.putBoolean("binary", isChecked);
        editorMode.commit();

        if (callback != null)
            callback.onSettings(isChecked);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
