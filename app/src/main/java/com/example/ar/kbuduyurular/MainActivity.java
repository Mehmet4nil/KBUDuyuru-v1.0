package com.example.ar.kbuduyurular;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

	Button btnGit;
	String muhFak = "http://muh.karabuk.edu.tr/",muhFakList = "http://muh.karabuk.edu.tr/index.php?page=announcements";

	Spinner spinnerfakulte;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnGit = findViewById(R.id.btn_Git);
		spinnerfakulte = findViewById(R.id.spinner_Fakulte);

		intent = new Intent(MainActivity.this, DuyurulisteActivity.class);

		btnGit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intent);
			}
		});

		spinnerfakulte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
					case 0:
						break;
					case 1:
						btnGit.setTextColor(Color.BLUE);
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					case 5:
						break;
					case 6:
						break;
					case 7:
						break;
					case 8:
						// Fakulte duyurularinin listelenecegi activity ye muhendislik fak linkleri yollaniyor
						intent.putExtra("MUHFAK", muhFak);
						intent.putExtra("MUHFAKLIST",muhFakList);
						intent.putExtra("MUH","Mühendislik Fakültesi");
						startActivity(intent);
						break;
					case 9:
						break;
					case 10:
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}
}
