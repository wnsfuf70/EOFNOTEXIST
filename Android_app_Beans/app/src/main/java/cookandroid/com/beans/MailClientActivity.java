package cookandroid.com.beans;
/**
 * Created by JoonRyeol on 2016-12-06.
 */
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class MailClientActivity extends AppCompatActivity{

    private GMailSender m;
    EditText et_content;
    EditText et_title;
    Button btn_send,btn_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailclient);
        if (android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        btn_send = (Button) findViewById(R.id.btn_send);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        et_content = (EditText) findViewById(R.id.et_content);
        et_title = (EditText) findViewById(R.id.et_title);

        btn_send.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                GMailSender sender = new GMailSender("wnsfuf70@gmail.com", "ayfbucimailpiqyy"); // SUBSTITUTE
                try{
                    sender.sendMail(et_title.getText().toString(), // subject.getText().toString(),
                            et_content.getText().toString(), // body.getText().toString(),
                            "wnsfuf70@gmail.com", // from.getText().toString(),
                            "wnsfuf70@gmail.com" // to.getText().toString()
                    );
                    Toast.makeText(getApplication(), "전송되었습니다.", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(getApplication(), "전송 실패.", Toast.LENGTH_LONG).show();
                }
                finally{
                    finish();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view) {  finish(); }
        });

    }

}
