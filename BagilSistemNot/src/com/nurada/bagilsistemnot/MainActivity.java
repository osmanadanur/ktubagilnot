package com.nurada.bagilsistemnot;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;
import com.google.ads.AdRequest.ErrorCode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,AdListener{

	EditText vize_not,final_not,sinif_ort,standart_spm,ogr_ort,t_not,harf_not;
	EditText not_ort_yaz1,not_ort_yaz2,not_ort_yaz3,not_ort_yaz4,not_ort_yaz5,not_ort_yaz6;
	String vize_not_s,final_not_s,sinif_ort_s,standart_spm_s;
	Button hesapla_buton,temizle_buton,info_buton;
	double dc,cc,cb,bb,ba,aa;
	double vizenot,finalnot,sinifort,stndrtspm;
	private InterstitialAd interstitial;
	AdView adview;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		vize_not = (EditText) findViewById(R.id.vize);
		final_not = (EditText) findViewById(R.id.final_not);
		sinif_ort = (EditText) findViewById(R.id.sinif_ort);
		standart_spm = (EditText) findViewById(R.id.standrt_spm);
		
		not_ort_yaz1 = (EditText) findViewById(R.id.not_ort_yaz1);
		not_ort_yaz2 = (EditText) findViewById(R.id.not_ort_yaz2);
		not_ort_yaz3 = (EditText) findViewById(R.id.not_ort_yaz3);
		not_ort_yaz4 = (EditText) findViewById(R.id.not_ort_yaz4);
		not_ort_yaz5 = (EditText) findViewById(R.id.not_ort_yaz5);
		not_ort_yaz6 = (EditText) findViewById(R.id.not_ort_yaz6);
		
		hesapla_buton = (Button)findViewById(R.id.hesapla_buton);
		hesapla_buton.setOnClickListener(this);
			
		hesapla_buton.setBackgroundResource(R.drawable.abc);
			
		temizle_buton = (Button)findViewById(R.id.temizle_buton);
		temizle_buton.setOnClickListener(this);		
		
		temizle_buton.setBackgroundResource(R.drawable.abcd);
		
		info_buton=(Button) findViewById(R.id.infoButton);
		info_buton.setOnClickListener(this);
		
		interstitial = new InterstitialAd(this,"ca-app-pub-1407011822702155/3123912027");
		AdRequest adRequest = new AdRequest();                             
		interstitial.loadAd(adRequest);
		interstitial.setAdListener(this);
		
		LinearLayout layout=(LinearLayout)findViewById(R.id.reklamAlani);
        adview = new AdView(this, AdSize.SMART_BANNER,
                "ca-app-pub-1407011822702155/3123912027");
        layout.addView(adview);
        adview.loadAd(new AdRequest());
	}

	@Override
	public void onClick(View v) {
		ImageView img= (ImageView) findViewById(R.id.harfnotu);
		temizle(img);
		if(v.getId() == R.id.hesapla_buton ){
		vize_not_s = vize_not.getText().toString();
		final_not_s = final_not.getText().toString();
		sinif_ort_s = sinif_ort.getText().toString();
		standart_spm_s = standart_spm.getText().toString();				 			

			if ((!vize_not_s.equals("")) && (!sinif_ort_s.equals(""))) {
				
				vizenot = Float.parseFloat(vize_not.getText().toString());
				if (!final_not_s.equals("")) finalnot = Float.parseFloat(final_not.getText().toString());
				sinifort = Float.parseFloat(sinif_ort.getText().toString());
				

				double ogrort;
				double znot;
				double tnot;
				
				ogrort=(vizenot+finalnot)/2;
				
				if (vizenot < 100 && finalnot < 100 && sinifort < 80 ) {
					if (!standart_spm_s.equals("")) {
						stndrtspm = Float.parseFloat(standart_spm.getText().toString());
						znot=(ogrort-sinifort)/stndrtspm;
						tnot=(10*znot)+50;
						tnot=Math.round(tnot);
						seksenAlti(sinifort, stndrtspm, vizenot, finalnot, tnot,final_not_s,img);
					}
					else Toast.makeText(getApplicationContext(),
							"Lütfen standart sapma bilgisini girin!",
							Toast.LENGTH_SHORT).show();
					
				} else if (sinifort >= 80 && sinifort<=100) {
					if (final_not_s.equals("")) {
						ogrort=0;
					}
					seksenUstu(ogrort,vizenot,img);
					
				}
				else {
					Toast.makeText(getApplicationContext(),
							"Lütfen girdiðiniz bilgileri kontrol edin!",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"Lütfen Gerekli Alanlarý Doldurun!", Toast.LENGTH_SHORT)
						.show();

			}

		}
		
		if(v.getId() == R.id.temizle_buton){
			vize_not.setText(String.valueOf(""));
			final_not.setText(String.valueOf(""));
			sinif_ort.setText(String.valueOf(""));
			standart_spm.setText(String.valueOf(""));
			temizle(img);
			
		}
		if(v.getId() == R.id.infoButton){
			Intent info=new Intent(this,Hakkinda.class);
			startActivity(info);
			
		}

		
	}
	public void temizle(ImageView img){
		img.setImageResource(R.drawable.bos);
		not_ort_yaz1.setText(String.valueOf(""));
		not_ort_yaz2.setText(String.valueOf(""));
		not_ort_yaz3.setText(String.valueOf(""));
		not_ort_yaz4.setText(String.valueOf(""));
		not_ort_yaz5.setText(String.valueOf(""));
		not_ort_yaz6.setText(String.valueOf(""));
	}
		public void seksenAlti(double sinifort,double stndrtspm,double vizenot,double finalnot,double tnot,String final_not_s,ImageView img){
			
			double a=36,b;
			double d=40.99;
			if (sinifort<=42.5) {
				b=0;
			}
			else if (sinifort>42.5 && sinifort<=47.5) {
				b=2;
			}
			else if (sinifort>47.5 && sinifort<=52.5) {
				b=4;
			}
			else if (sinifort>52.5 && sinifort<=57.5) {
				b=6;
			}
			else if (sinifort>57.5 && sinifort<=62.5) {
				b=8;
			}
			else if (sinifort>62.5 && sinifort<=70) {
				b=10;
			}
			else {
				b=12;
			}
			
			a=a-b;
			d=d-b;	
			
			dc=Math.floor((((((a-40)/10)*stndrtspm)+sinifort)*2)-vizenot); //dc 
			if (dc<45) {dc=45;}
			cc=Math.floor((((((a-35)/10)*stndrtspm)+sinifort)*2)-vizenot); //cc 
			if (cc<45) {cc=45;}
			cb=Math.floor((((((a-30)/10)*stndrtspm)+sinifort)*2)-vizenot); //cb 
			if (cb<45) {cb=45;}
			bb=Math.floor((((((a-25)/10)*stndrtspm)+sinifort)*2)-vizenot); //bb 
			if (bb<45) {bb=45;}
			ba=Math.floor((((((a-20)/10)*stndrtspm)+sinifort)*2)-vizenot); //ba 
			if (ba<45) {ba=45;}
			aa=Math.floor((((((a-15)/10)*stndrtspm)+sinifort)*2)-vizenot); //aa
			if (aa<45) {aa=45;}

			if (dc<100) {not_ort_yaz1.setText(String.valueOf(dc));}
			else not_ort_yaz1.setText(String.valueOf("X"));		
			if(dc==cc){not_ort_yaz1.setText(String.valueOf("X"));}
			
			if (cc<100) {not_ort_yaz2.setText(String.valueOf(cc));}		
			else not_ort_yaz2.setText(String.valueOf("X"));
			if(cc==cb){not_ort_yaz2.setText(String.valueOf("X"));}
			
			if (cb<100) {not_ort_yaz3.setText(String.valueOf(cb));}
			else not_ort_yaz3.setText(String.valueOf("X"));
			if(cb==bb){not_ort_yaz3.setText(String.valueOf("X"));}
			
			if (bb<100) {not_ort_yaz4.setText(String.valueOf(bb));}
			else not_ort_yaz4.setText(String.valueOf("X"));
			if(bb==ba){not_ort_yaz4.setText(String.valueOf("X"));}
			
			if (ba<100) {not_ort_yaz5.setText(String.valueOf(ba));}
			else not_ort_yaz5.setText(String.valueOf("X"));
			if(ba==aa){not_ort_yaz5.setText(String.valueOf("X"));}
			
			if (aa<100) {not_ort_yaz6.setText(String.valueOf(aa));}
			else not_ort_yaz6.setText(String.valueOf("X"));
			
			if (!final_not_s.equals("")) {
			if(finalnot>=45 ){	
				if (tnot<a) {
			        img.setImageResource(R.drawable.ff);
				}
				else if (tnot>=a && tnot<=d) {	
					img.setImageResource(R.drawable.fd);
				}
				else if (tnot>=(a+5) && tnot<=(d+5)) {;
					img.setImageResource(R.drawable.dd);
				}
				else if (tnot>=(a+10) && tnot<=(d+10)) {	
					img.setImageResource(R.drawable.dc);
				}
				else if (tnot>=(a+15) && tnot<=(d+15)) {
					img.setImageResource(R.drawable.cc);
				}
				else if (tnot>=(a+20) && tnot<=(d+20)) {
					img.setImageResource(R.drawable.cb);
				}
				else if (tnot>=(a+25) && tnot<=(d+25)) {
					img.setImageResource(R.drawable.bb);
				}
				else if (tnot>=(a+30) && tnot<=(d+30)) {
					img.setImageResource(R.drawable.ba);
				}
				else {
					img.setImageResource(R.drawable.aa);
				}
				}
			else {
				img.setImageResource(R.drawable.ff);
			}
			}
			else img.setImageResource(R.drawable.bos);
			
		}
	public void seksenUstu(double ogrort,double vizenot,ImageView img){
		if (ogrort!=0) {		
		
		if (ogrort>=90) {
			img.setImageResource(R.drawable.aa);
		}
		else if (ogrort>=80 && ogrort<90) {
			img.setImageResource(R.drawable.ba);
		}
		else if (ogrort>=75 && ogrort<80) {
			img.setImageResource(R.drawable.bb);
		}
		else if (ogrort>=70 && ogrort<75) {
			img.setImageResource(R.drawable.cb);
		}
		else if (ogrort>=60 && ogrort<70) {
			img.setImageResource(R.drawable.cc);
		}
		else if (ogrort>=50 && ogrort<60) {
			img.setImageResource(R.drawable.dc);
		}
		else if (ogrort>=40 && ogrort<50) {
			img.setImageResource(R.drawable.dd);
		}
		else if (ogrort>=30 && ogrort<40) {
			img.setImageResource(R.drawable.fd);
		}
		else if (ogrort<30) {
			img.setImageResource(R.drawable.ff);
		}
		}
		dc=2*50-vizenot;
		if (dc<45) {dc=45;}
		cc=2*60-vizenot;
		if (cc<45) {cc=45;}
		cb=2*70-vizenot;
		if (cb<45) {cb=45;}
		bb=2*75-vizenot;
		if (bb<45) {bb=45;}
		ba=2*80-vizenot;
		if (ba<45) {ba=45;}
		aa=2*90-vizenot;
		if (aa<45) {aa=45;}
		
		if (dc<=100) {not_ort_yaz1.setText(String.valueOf(dc));}
		else not_ort_yaz1.setText(String.valueOf("X"));		
		if(dc==cc){not_ort_yaz1.setText(String.valueOf("X"));}
		
		if (cc<100) {not_ort_yaz2.setText(String.valueOf(cc));}		
		else not_ort_yaz2.setText(String.valueOf("X"));
		if(cc==cb){not_ort_yaz2.setText(String.valueOf("X"));}
		
		if (cb<100) {not_ort_yaz3.setText(String.valueOf(cb));}
		else not_ort_yaz3.setText(String.valueOf("X"));
		if(cb==bb){not_ort_yaz3.setText(String.valueOf("X"));}
		
		if (bb<100) {not_ort_yaz4.setText(String.valueOf(bb));}
		else not_ort_yaz4.setText(String.valueOf("X"));
		if(bb==ba){not_ort_yaz4.setText(String.valueOf("X"));}
		
		if (ba<100) {not_ort_yaz5.setText(String.valueOf(ba));}
		else not_ort_yaz5.setText(String.valueOf("X"));
		if(ba==aa){not_ort_yaz5.setText(String.valueOf("X"));}
		
		if (aa<100 ) {not_ort_yaz6.setText(String.valueOf(aa));}
		else not_ort_yaz6.setText(String.valueOf("X"));
	}
	@Override
    public boolean onTouchEvent(MotionEvent event) {

        hideSoftKeyboard(this);

        return false;
    }

    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveAd(Ad arg0) {
		// TODO Auto-generated method stub
		if (arg0 == interstitial) {
	        interstitial.show();
	       }
	}
}
