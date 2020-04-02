package com.example.ar.kbuduyurular;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DuyurulisteActivity extends AppCompatActivity implements View.OnClickListener{

	ProgressDialog progressDialog;
	ConstraintLayout title_layout;
	TextView txtTitle;
	Button btnDGetir;
	ProgressBar pbar;
	ListView duyuruListe;
	ArrayList<String> duyuruInfo = new ArrayList<>();
	ArrayList<String> duyuruText = new ArrayList<>();
	ArrayList<String> duyuruLinkList = new ArrayList<>();
	ArrayList<String> duyuruTitleList = new ArrayList<>();

	Elements duyuru;
	String tarihList, tarihSystm;
	int i2 = 1,nextLi = 2, tarihLGun, tarihLAy, tarihSGun, tarihSAy;

	ArrayAdapter<String> listAdaptor;

	private static String Url = "";
	String UrlList = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duyuruliste);

		btnDGetir = findViewById(R.id.btn_DGetir);
		txtTitle = findViewById(R.id.textView_Title);
		pbar = findViewById(R.id.progressBar);
		duyuruListe = findViewById(R.id.Listview_Duyuru);

		pbar.setVisibility(View.GONE);

		listAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, duyuruTitleList);
		duyuruListe.setAdapter(listAdaptor);

		btnDGetir.setOnClickListener(this);

		Url = getIntent().getStringExtra("MUHFAK"); // listelenmesi istenen fakultenin linkleri aliniyor
		UrlList = getIntent().getStringExtra("MUHFAKLIST");
		txtTitle.setText(getIntent().getStringExtra("MUH")+" Duyuruları");

		duyuruListe.setOnItemClickListener(new AdapterView.OnItemClickListener() { //listView de tıklanan pozizyondaki duyurunun acilmasi
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Url = "http://muh.karabuk.edu.tr/" + duyuruLinkList.get(position);
				btnDGetir.setTextColor(Color.RED);
				new DuyuruGetir().execute();
			}
		});

		new DuyuruListeGetir().execute();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.btn_DGetir) {
			btnDGetir.setTextColor(Color.BLUE);
			UrlList = UrlList + "&frame=" + nextLi; // bir sonraki duyuru listesinin linkini olusturur
			nextLi++;
			new DuyuruListeGetir().execute();
		}


	}


	public class DuyuruListeGetir extends AsyncTask<Void, Void, Void> { //listView e ilgili sitedeki duyuru basliklarinin cekilmesi ve listelenmesi


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pbar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				org.jsoup.nodes.Document doc = Jsoup.connect(UrlList).get();
				Elements ele = doc.select("div#main");
				Elements table = ele.select("table");
				//Elements rows = table.select("tr");
				Elements cols = table.select("tr");
				Elements dSayi = table.select("tr.duyurutd"); // Duyuru listesindeki duyuru sayısını çeker
				Elements a = cols.select("a[href]");// Listede gösterilen duyuru linklerini alır
				Elements aTitle = cols.select("a");// Listede gösterilen duyuru başlıklarını alır
				Elements p = cols.select("p");// Listedeki duyuru tarihlerini alır


				for (int i = 0; i < dSayi.size(); i++) {

					tarihList = p.get(i2).text();
					tarihLGun = Integer.parseInt(tarihList.substring(15, 17));
					tarihLAy = Integer.parseInt(tarihList.substring(18, 20));

					Date date2 = Calendar.getInstance().getTime(); // Sistem tarihini formatli olarak alir
					SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM");
					tarihSystm = dateformat.format(date2);
					tarihSGun = Integer.parseInt(tarihSystm.substring(0, 2));
					tarihSAy = Integer.parseInt(tarihSystm.substring(3, 5));


					if (tarihSAy == tarihLAy) {
						if (tarihSGun - 7 <= tarihLGun) {
							duyuruTitleList.add("[YENİ]" + aTitle.get(i).text());
						}
					} else {
						duyuruTitleList.add(aTitle.get(i).text());
					}

					i2 = i2 + 3;
				}
				for (Element link : a) {
					duyuruLinkList.add(link.attr("href"));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			//txtEklyn2.setText(duyuruInfo.get(0));
			//txtZmn2.setText(duyuruInfo.get(1));
			//txtOkSy2.setText(duyuruInfo.get(2));
			//txtDuyuru2.setText(duyuruText.get(4));
			listAdaptor.notifyDataSetChanged(); // listeyi yeniliyor
			pbar.setVisibility(View.GONE);

			//Document d = Jsoup.connect("http://muh.karabuk.edu.tr/index.php?page=announcement&no=1781").post();
			//d.select("tr.duyuru_private");

		}
	}

	public class DuyuruGetir extends AsyncTask<Void, Void, Void> { //listView de secilen linkdeki duyuru iceriginin cekilmesi ve yazdirilmasi


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pbar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				org.jsoup.nodes.Document doc = Jsoup.connect(Url).get();
				Elements ele = doc.select("div#main");
				Elements table = ele.select("table");
				duyuru = doc.select("tr.duyuru_private");//WebView de gosterilecek icerik aliniyor
				//Elements rows = table.select("tr");
				Elements cols = table.select("td");
				Elements p = cols.select("p");

				duyuruText = new ArrayList<>();
				duyuruInfo = new ArrayList<>();
				for (int j = 0; j < cols.size(); j++) {
					duyuruText.add(cols.get(j).text());
				}
				for (int i = 0; i < p.size(); i++) {
					duyuruInfo.add(p.get(i).text());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			Intent intent = new Intent(DuyurulisteActivity.this, DuyurularActivity.class);
			intent.putExtra("TITLE", duyuruText.get(0));
			intent.putExtra("EKLEYEN", duyuruInfo.get(0));
			intent.putExtra("ZAMAN", duyuruInfo.get(1));
			intent.putExtra("OKUYAN", duyuruInfo.get(2));
			intent.putExtra("DUYURU_W", duyuru.toString());
			startActivity(intent);
			pbar.setVisibility(View.GONE);

		}
	}
}
