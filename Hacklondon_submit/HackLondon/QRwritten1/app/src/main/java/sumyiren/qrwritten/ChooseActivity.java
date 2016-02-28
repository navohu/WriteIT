package sumyiren.qrwritten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends Activity {

    private Button goupload;
    private Button gocheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        goupload = (Button) findViewById(R.id.goupload);
        gocheck = (Button) findViewById(R.id.gocheck);


        goupload.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Launch main activity
                Intent intent = new Intent(ChooseActivity.this,
                        MainActivity.class);


                startActivity(intent);
                finish();
            }
        });


        gocheck.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Launch main activity
                Intent intent = new Intent(ChooseActivity.this,
                        gocheck.class);


                startActivity(intent);
                finish();
            }


        });
    }
}





