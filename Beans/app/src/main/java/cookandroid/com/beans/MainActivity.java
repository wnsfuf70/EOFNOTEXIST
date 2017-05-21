package cookandroid.com.beans;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MyDTO dto = new MyDTO();

    HorizontalScrollView scroll;
    Button submitBtn;
    LinearLayout baseLayout,subLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this,SplashActivity.class));
        /* 각 컴포넌트에 대한 findding */
        submitBtn=(Button)findViewById(R.id.submitBtn);
        baseLayout=(LinearLayout)findViewById(R.id.baseLayout);
        subLayout=(LinearLayout)findViewById(R.id.subLayout);
        scroll=(HorizontalScrollView)findViewById(R.id.Scrollview1);
        dto.et=(EditText)findViewById(R.id.editText1);
        dto.google=(ImageButton)findViewById(R.id.gogleBtn);
        dto.naver=(ImageButton)findViewById(R.id.naverBtn);
        dto.naverdic=(ImageButton)findViewById(R.id.naverdicBtn);
        dto.daum=(ImageButton)findViewById(R.id.daumBtn);
        dto.youtube=(ImageButton)findViewById(R.id.youtubeBtn);
        dto.playstore=(ImageButton)findViewById(R.id.playstoreBtn);
        dto.map=(ImageButton)findViewById(R.id.mapBtn);

        /*사이트 클릭*/
        dto.google.setOnClickListener(new ImgClick(dto));
        dto.naver.setOnClickListener(new ImgClick(dto));
        dto.naverdic.setOnClickListener(new ImgClick(dto));
        dto.daum.setOnClickListener(new ImgClick(dto));
        dto.youtube.setOnClickListener(new ImgClick(dto));
        dto.playstore.setOnClickListener(new ImgClick(dto));
        dto.map.setOnClickListener(new ImgClick(dto));

        /* 검색 버튼 클릭*/
        submitBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(dto.getSubmitURL().equals("NULL")){
                Toast.makeText(MainActivity.this,"포털사이트를 선택하세요",Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                    String Url=dto.getSubmitURL().concat(dto.et.getText().toString());
                    //Intent search = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
                    //startActivity(search);
                    Intent token = new Intent(MainActivity.this,WebViewActivity.class);
                    token.putExtra("urlParameter",Url);
                    startActivity(token);
                    return;
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater myInflater = getMenuInflater();
        myInflater.inflate(R.menu.menu1, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.itemOriginal:
                baseLayout.setBackgroundResource(R.drawable.background);
                scroll.setBackground(getDrawable(R.drawable.scroll_img));
                return true;
            case R.id.itemDark:
                baseLayout.setBackgroundResource(R.drawable.background2);
                scroll.setBackground(getDrawable(R.drawable.scroll_img2));
                return true;
            case R.id.itemBright:
                baseLayout.setBackgroundResource(R.drawable.background3);
                scroll.setBackground(getDrawable(R.drawable.scroll_img));
                return true;
            case R.id.send_mail :
                Intent token = new Intent(MainActivity.this,MailClientActivity.class);
                startActivity(token);
                return true;
        }
        return false;
    }
}
