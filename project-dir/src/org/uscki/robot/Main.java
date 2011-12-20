package org.uscki.robot;

import org.uscki.robot.mennov1.MennoV1;

import android.app.Activity;
import android.os.Bundle;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MennoV1 menno = new MennoV1();
        menno.setup();
    }
}