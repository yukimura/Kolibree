package app.kolibree.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import app.kolibree.R;
import app.kolibree.utils.DateUtils;

/**
 * Created by Lapinou on 03/07/2015.
 */
public class DialogDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Date date = null;
        try {
            date = DateUtils.CUSTOM_DATE_FORMAT.parse(day + "/" + month + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strDate = DateUtils.PROGRAM_DATE_FORMAT.format(date);
        ((Button) getActivity().findViewById(R.id.bt_datePicker)).setText(strDate);
    }

}