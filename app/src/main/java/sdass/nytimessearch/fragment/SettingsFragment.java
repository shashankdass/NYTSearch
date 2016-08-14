package sdass.nytimessearch.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;

import sdass.nytimessearch.R;
import sdass.nytimessearch.databinding.FragmentSettingsBinding;

/**
 * Created by sdass on 8/12/16.
 */
public class SettingsFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    private FragmentSettingsBinding binding;
    private ImageView imageView;
    private Spinner spinner;
    private TextView showSelectedDate;
    private HashMap<String, Boolean> checkedGenres = new HashMap();
    final Calendar c = Calendar.getInstance();
    String sortBy;
    private onSettingsDoneListener listener;
    public interface onSettingsDoneListener {
        // This can be any number of events to be sent to the activity
        public void onSettingsDone(Calendar c, String sortBy, HashMap genres);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.fragment_settings, parent,false);
        imageView = binding.datePicker;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        spinner = binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_order_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        setupCheckBoxes();

        return binding.getRoot();
    }

    CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton view, boolean checked) {
            // compoundButton is the checkbox
            // boolean is whether or not checkbox is checked
            // Check which checkbox was clicked
            switch(view.getId()) {
                case R.id.artsCheckBox:
                    if (checked) {
                        checkedGenres.put("arts", true);
                    } else {
                        checkedGenres.put("arts", false);
                    }
                    break;
                case R.id.sportsCheckBox:
                    if (checked) {
                        checkedGenres.put("sports", true);
                    } else {
                        checkedGenres.put("sports", false);
                    }
                    break;
                case R.id.fashionCheckBox:
                    if (checked) {
                        checkedGenres.put("fashion", true);
                    } else {
                        checkedGenres.put("fashion", false);
                    }
                    break;
            }
        }
    };

    private void setupCheckBoxes() {
            CheckBox artsCheck = binding.artsCheckBox;
            CheckBox sportsCheck = binding.sportsCheckBox;
            CheckBox fashionCheck = binding.fashionCheckBox;
            artsCheck.setOnCheckedChangeListener(checkListener);
            sportsCheck.setOnCheckedChangeListener(checkListener);
            fashionCheck.setOnCheckedChangeListener(checkListener);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onSettingsDoneListener) {
            listener = (onSettingsDoneListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement SettingsFragment.onSettingsDoneListener");
        }
    }

    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing

        super.onResume();
    }

    private void showDatePicker() {
        FragmentManager fm = getFragmentManager();
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        // SETS the target fragment for use later when sending results
        datePickerFragment.setTargetFragment(SettingsFragment.this, 300);
        datePickerFragment.show(fm, "date picker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        showSelectedDate = binding.tvDate;
        showSelectedDate.setText(""+dayOfMonth+"\\"+(monthOfYear+1) +"\\"+year);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sortBy = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onGenreSelected(View view){
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        HashMap checkedValues = new HashMap();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.artsCheckBox:
                if (checked)
                    checkedValues.put("arts", true);
                else
                    checkedValues.put("arts", false);
                break;
            case R.id.fashionCheckBox:
                if (checked)
                    checkedValues.put("fashion", true);
                else
                    checkedValues.put("fashion", false);
                break;
            case R.id.sportsCheckBox:
                if (checked)
                    checkedValues.put("sports", true);
                else
                    checkedValues.put("sports", false);
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener.onSettingsDone(c,sortBy,checkedGenres);
    }
}
