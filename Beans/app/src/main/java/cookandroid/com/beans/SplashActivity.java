package cookandroid.com.beans;

        import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by JoonRyeol on 2016-11-28.
 */
public class SplashActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler hd = new Handler();
        hd.postDelayed(new Runnable(){ @Override public void run(){finish();} }, 1500); // 1.5초 후에 hd Handler 실행
    }
}
