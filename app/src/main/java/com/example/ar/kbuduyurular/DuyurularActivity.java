package com.example.ar.kbuduyurular;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class DuyurularActivity extends AppCompatActivity implements View.OnClickListener {

	TextView txtTitle, txtEklyn, txtZmn, txtOkSy;
	FloatingActionButton fab;
	WebView webViewDuyuru;
	String site;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duyurular);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
				DuyurularActivity.super.onBackPressed();
			}
		});

		txtTitle = findViewById(R.id.textView_Title);
		txtEklyn = findViewById(R.id.textView_Eklyn);
		txtOkSy = findViewById(R.id.textView_OkSy);
		txtZmn = findViewById(R.id.textView_Zmn);
		webViewDuyuru = findViewById(R.id.webView_Duyuru);

		txtTitle.setText(getIntent().getStringExtra("TITLE"));
		txtEklyn.setText(getIntent().getStringExtra("EKLEYEN"));
		txtOkSy.setText(getIntent().getStringExtra("OKUYAN"));
		txtZmn.setText(getIntent().getStringExtra("ZAMAN"));
		site = "<html>\n" + "<head>\n" + "\t\n" + "\n" + "</head>\n" + "\n" + "<body>" + getIntent().getStringExtra("DUYURU_W") + "</body>\n" + "</html>";

		webViewDuyuru.loadData(site, "text/html", "UTF-8");
	}

	@Override
	public void onClick(View v) {

	}
}
