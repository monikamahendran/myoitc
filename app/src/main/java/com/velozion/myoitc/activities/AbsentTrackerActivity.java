package com.velozion.myoitc.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.velozion.myoitc.BaseActivity;
import com.velozion.myoitc.R;
import com.velozion.myoitc.Utils;
import com.velozion.myoitc.bean.AbsentTrackerBean;
import com.velozion.myoitc.bean.EmployeeListBean;
import com.velozion.myoitc.interfaces.Listeners;
import com.velozion.myoitc.network.StringMethodRequest;
import com.velozion.myoitc.utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AbsentTrackerActivity extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, Listeners.POSTMethodListener {

    List<Calendar> selected_dates = new ArrayList<>();

    SimpleDateFormat simpleDateFormat;
    CalendarView calendarView;

    ArrayList<AbsentTrackerBean> absentTrackerBeans;

    EmployeeListBean employeeListBean;

    TextView emp_name, emp_des, month_tv, workingdays_tv, workinghrs_tv, leavs_tv;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarRequired(true);
        setToolbarTitle("Absence Tracker");
        setHomeMenuRequired(false);
        setContentView(R.layout.activity_absent_tracker);
        absentTrackerBeans = new ArrayList<>();

        simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
        calendarView = findViewById(R.id.calendarContainer);
        emp_name = findViewById(R.id.eat_name);
        emp_des = findViewById(R.id.eat_des);
        month_tv = findViewById(R.id.eat_month);
        workingdays_tv = findViewById(R.id.eat_workingdays);
        workinghrs_tv = findViewById(R.id.eat_hrs_count);
        leavs_tv = findViewById(R.id.eat_leaves_count);


        employeeListBean = getIntent().getExtras().getParcelable("data");

        employeeListBean.setEmp_id("1");

        emp_name.setText("" + employeeListBean.getEmp_name());
        emp_des.setText(" - " + employeeListBean.getEmp_designation());

        getAbsentTrackerList();

        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {

                handleDetails();

            }
        });

        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                handleDetails();
            }
        });

    }

    private void handleDetails() {

        int month = calendarView.getCurrentPageDate().get(Calendar.MONTH);

        Log.d("ResponseMonth", "" + (month + 1));

        for (int i = 0; i < absentTrackerBeans.size(); i++) {

            String monthname = Utils.convertNumToMonth((month + 1));

            if (absentTrackerBeans.get(i).getMonth().equalsIgnoreCase(monthname)) {
                month_tv.setText(" (" + monthname + ")");
                workingdays_tv.setText("" + absentTrackerBeans.get(i).getWorkingDays());
                workinghrs_tv.setText("" + absentTrackerBeans.get(i).getWorkingHrs());
                leavs_tv.setText("" + absentTrackerBeans.get(i).getLeaves());

                break;

            } else {

                month_tv.setText(" (" + monthname + ")");
                workingdays_tv.setText("0");
                workinghrs_tv.setText("0");
                leavs_tv.setText("0");
            }

        }

    }


    private void getAbsentTrackerList() {
        try {
            Map<String, String> mParams = new HashMap<>();

            StringMethodRequest postMethodRequest = new StringMethodRequest(this, Request.Method.GET, Utils.AbsentTrackerAPI, mParams, true, this, this, this, 1);
            MyApplication.getInstance().addToRequestQueue(postMethodRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }


    private void setCalendarView(List<EventDay> events, String date, String color) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendarView.setHeaderColor(R.color.grey);
            calendarView.setHeaderLabelColor(R.color.white);

            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(getDay(date)));
            calendar.set(Calendar.MONTH, getMonthNum(date));
            calendar.set(Calendar.YEAR, Integer.parseInt(getyear(date)));

            events.add(new EventDay(calendar, R.drawable.ic_leave, Color.parseColor(color)));

            selected_dates.add(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("ResponseCalenderError", e.getMessage());
        }
        calendarView.setEvents(events);
        calendarView.setHighlightedDays(selected_dates);
    }


    @Override
    public void onPostCompleted(JSONObject newJsObj, int msgId) throws JSONException {
        if (msgId == 1) {
            if (newJsObj.getBoolean("success")) {
                List<EventDay> events = new ArrayList<>();

                JSONArray outJson = newJsObj.optJSONArray("data");

                Log.d("ResponseS", newJsObj.toString());

                if (outJson.length() > 0) {

                    JSONObject outerObject = outJson.optJSONObject(0);

                    JSONArray jsonArray = outerObject.optJSONArray("details");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.optJSONObject(i);

                        Log.d("ResponseEmp", object.optString("id") + " ," + employeeListBean.getEmp_id());

                        if (object.optString("id").equalsIgnoreCase(employeeListBean.getEmp_id())) {

                            for (int j = 0; j < object.optJSONArray("months").length(); j++) {

                                JSONObject monthJSONObj = object.optJSONArray("months").optJSONObject(j);

                                for (int z = 0; z < monthJSONObj.optJSONArray("holidays").length(); z++) {
                                    JSONObject holidayJSONObj = monthJSONObj.optJSONArray("holidays").optJSONObject(z);

                                    String date = holidayJSONObj.optString("date");
                                    String color_code = holidayJSONObj.optString("reasonColorScheme");
                                    setCalendarView(events, date, color_code);

                                }


                                absentTrackerBeans.add(new AbsentTrackerBean(object.optString("name"),
                                        object.optString("designation"),
                                        monthJSONObj.optString("month"),
                                        monthJSONObj.optString("workingDays"),
                                        monthJSONObj.optString("workingHrs"),
                                        String.valueOf(monthJSONObj.optJSONArray("holidays").length())));


                            }


                            Log.d("ResponseEvents", "Holidasy : " + events.size() + " , Months :" + absentTrackerBeans.size());


                            handleDetails();

                            break;
                        }
                    }
                    if (events.size() == 0) {
                        Toast.makeText(this, "No Absent Data Found", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, events.size() + " Absent Data Found", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        }
    }

    public static int getMonthNum(String date) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date d = dateFormat.parse(date);

        SimpleDateFormat monthformat = new SimpleDateFormat("MM", Locale.US);
        int month = Integer.parseInt(monthformat.format(d.getTime()));

        month--;


        return month;
    }


    public static String getDay(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date d = dateFormat.parse(date);

        SimpleDateFormat dayformat = new SimpleDateFormat("dd", Locale.US);


        return dayformat.format(d.getTime());


    }

    public static String getyear(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date d = dateFormat.parse(date);

        SimpleDateFormat yearformat = new SimpleDateFormat("yyyy", Locale.US);


        return yearformat.format(d.getTime());


    }
}
