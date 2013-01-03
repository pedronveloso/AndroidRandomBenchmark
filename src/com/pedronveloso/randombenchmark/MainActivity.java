package com.pedronveloso.randombenchmark;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.GregorianCalendar;

public class MainActivity extends Activity implements View.OnClickListener {

    private boolean firstRun = true;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button) findViewById(R.id.btn_start);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                new RainbowsInTheBackground().execute();
                //disable the button so the user does not launch more threads
                Button btn = (Button) findViewById(R.id.btn_start);
                btn.setEnabled(false);
                break;
        }
    }


    private class RainbowsInTheBackground extends AsyncTask<Long, Long, Long> {

        protected Long doInBackground(Long... params) {
            SecureRandom secureRandom = new SecureRandom();
            GregorianCalendar before = new GregorianCalendar();
            double tempResult; //just a placeholder to avoid some Dalvik VM optimizations
            for (int i = 0; i < 1000000; i++) {
                tempResult = secureRandom.nextDouble();
            }
            GregorianCalendar after = new GregorianCalendar();
            return after.getTimeInMillis() - before.getTimeInMillis();
        }

        protected void onPostExecute(Long result) {
            TextView tvResult = (TextView) findViewById(R.id.tv_text);
            if (firstRun) {
                firstRun = false;
                //clear text
                tvResult.setText("");
            }
            //append to last
            tvResult.setText(tvResult.getText() + "\nTook this much milliseconds: " + result);
            //re-enable button now
            Button btn = (Button) findViewById(R.id.btn_start);
            btn.setEnabled(true);
        }
    }

}
