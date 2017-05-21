package cookandroid.com.beans;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by JoonRyeol on 2016-11-27.
 */
public class WebViewActivity extends FragmentActivity{
    WebView web;
    EditText edtUrl;
    ImageButton btnGo,btnBack,btnFoward,btnFfive,btnexit,btnLoadstop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        web=(WebView)findViewById(R.id.web1);
        edtUrl=(EditText)findViewById(R.id.edt1);
        btnGo=(ImageButton)findViewById(R.id.btnGo);
        btnBack=(ImageButton)findViewById(R.id.btnBack);
        btnFoward=(ImageButton)findViewById(R.id.btnFoword);
        btnFfive=(ImageButton)findViewById(R.id.btnF5);
        btnLoadstop=(ImageButton)findViewById(R.id.loadStop);
        btnexit=(ImageButton)findViewById(R.id.mainReturn);

        Intent intent = getIntent();
        String init_url=intent.getExtras().getString("urlParameter");
        edtUrl.setText(init_url);
        web.loadUrl(init_url);

        web.setWebViewClient(new WebViewClient());
        WebSettings set = web.getSettings();//웹세팅 클래스를 이용하여 줌 버튼 컨트롤을 화면에 표시
        set.setBuiltInZoomControls(true);//디스플레이
        set.setJavaScriptEnabled(true);

        btnGo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            web.loadUrl(edtUrl.getText().toString()); // 입력한 URL 을 로딩함...
        }
    });
    btnBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            web.goBack(); // 뷰의 이전 화면으로 이동..
        }
    });
    btnFoward.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            web.goForward();
        }
    });
    btnFfive.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            web.reload();
        }
    });
    btnLoadstop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            web.stopLoading();
        }
    });
    btnexit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
}
}
