package cookandroid.com.beans;


import android.view.View;

/**
 * Created by JoonRyeol on 2016-11-27.
 */
public class ImgClick implements View.OnClickListener{
    MyDTO dto;
    public ImgClick(MyDTO dto) {
        this.dto = dto;
    }

    public int case_check(String submitURL) {
        if (submitURL.equals(dto.googleURL)) { return 1;}
        else if(submitURL.equals(dto.daumURL)){ return 2; }
        else if(submitURL.equals(dto.naverURL)){ return 3; }
        else if(submitURL.equals(dto.naverdicURL)){ return 4; }
        else if(submitURL.equals(dto.youtubeURL)){ return 5; }
        else if(submitURL.equals(dto.mapURL)){ return 6; }
        else if(submitURL.equals(dto.playstoreURL)){ return 7; }
        else { return 0; }
    }
    public void initImage(String submitURL){
       int token = case_check(submitURL);
            switch(token) {
                case 1: dto.google.setImageResource(R.drawable.google_small); break;
                case 2: dto.daum.setImageResource(R.drawable.daum_small); break;
                case 3: dto.naver.setImageResource(R.drawable.naver_small); break;
                case 4: dto.naverdic.setImageResource(R.drawable.naver_dic_small); break;
                case 5: dto.youtube.setImageResource(R.drawable.youtube_small); break;
                case 6: dto.map.setImageResource(R.drawable.map_small); break;
                case 7: dto.playstore.setImageResource(R.drawable.playstore_small); break;
            }
    }
    @Override
    public void onClick(View v){
        initImage(dto.submitURL);
        switch(v.getId()){
            case R.id.gogleBtn :{
                dto.google.setImageResource(R.drawable.google_check);
                dto.setSubmitURL(dto.googleURL);
            }return;
            case R.id.daumBtn :{
                dto.daum.setImageResource(R.drawable.daum_check);
                dto.setSubmitURL(dto.daumURL);
             }return;
            case R.id.naverBtn :{
                dto.naver.setImageResource(R.drawable.naver_check);
                dto.setSubmitURL(dto.naverURL);
            }return;
            case R.id.naverdicBtn :{
                dto.naverdic.setImageResource(R.drawable.naver_dic_check);
                dto.setSubmitURL(dto.naverdicURL);
            }return;
            case R.id.youtubeBtn :{
                dto.youtube.setImageResource(R.drawable.youtube_check);
                dto.setSubmitURL(dto.youtubeURL);
            }return;
            case R.id.mapBtn :{
                dto.map.setImageResource(R.drawable.map_check);
                dto.setSubmitURL(dto.mapURL);
            }return;
            case R.id.playstoreBtn :{
                dto.playstore.setImageResource(R.drawable.playstore_check);
                dto.setSubmitURL(dto.playstoreURL);
            }return;
        }
    }
}
