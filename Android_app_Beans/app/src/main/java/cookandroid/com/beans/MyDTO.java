package cookandroid.com.beans;

import android.widget.EditText;
import android.widget.ImageButton;
/**
 * Created by JoonRyeol on 2016-11-26.
 */
public class MyDTO {
    ImageButton google,naver,daum,youtube,playstore,naverdic,map;
    EditText et;
    String googleURL,naverURL,daumURL,youtubeURL,playstoreURL,naverdicURL,mapURL;
    String submitURL;

    public MyDTO(){
        googleURL="http://www.google.co.kr/search?q=";
        naverURL="http://search.naver.com/search.naver?&query=";
        daumURL="http://m.search.daum.net/search?&q=";
        youtubeURL="http://www.youtube.com/results?search_query=";
        playstoreURL ="http://play.google.com/store/search?q=";
        naverdicURL="http://dic.naver.com/search.nhn?dicQuery=";
        mapURL="http://www.google.co.kr/maps/place/";
        submitURL="NULL";
    }
    public void setSubmitURL(String submitURL){
        this.submitURL=submitURL;
    }
    public String getSubmitURL(){
        return this.submitURL;
    }
}
