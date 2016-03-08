package org.dieschnittstelle.mobile.android.activities;

import org.dieschnittstelle.mobile.android.activities.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class Anna extends AbstractLifecycleLogger {

	/**
	 * constants
	 */
	private static final int REQUEST_CODE_CALL_BEATE = 0;

	/**
	 * logging
	 */
	protected static String logger = "Anna";
	
	/**
	 * the text view that displays the inputs
	 */
	private TextView conversation;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.conversation);

		// Die Ansicht, in der der Austausch von Nachrichten zwischen Anna und
		// Beate dargestellt wird
		conversation = (TextView) findViewById(R.id.conversation_transcript);

		// Das Eingabefeld fuer eine neue Nachricht
		EditText newMessage = (EditText) findViewById(R.id.new_message);

		newMessage.setImeOptions(EditorInfo.IME_ACTION_DONE);

		// Falls eine neue Nachricht eingegeben wird und mittels "done" beendet
		// wird, wird die Activity Beate aufgerufen
		newMessage.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView view, int arg1, KeyEvent arg2) {
				Log.d(logger, "got editor action " + arg1 + " on textview "
						+ view + ". KeyEvent is: " + arg2);
				if (arg1 == EditorInfo.IME_ACTION_DONE || KeyEvent.KEYCODE_ENTER == arg2.getKeyCode()) {
					Log.d(logger, "done was pressed!");

					// Zugriff auf den eingegebenen Text
					String message = view.getText().toString();

					// Ausgabe des eingegebenen Texts
					conversation.append("\nmyself> " + message);
					
					// Aufruf der Beate Activity mit dem eingegebenen Text 
					callBeateWithMessage(message);

					view.setText("");

					return false;
				}
				return false;
			}

		});

	}
	

	/**
	 * uebung:
	 * 
	 * 1) Grundlegende Funktion von Aktivitaetsaufrufen <br>
	 * <br>
	 * a) Aufruf mit Aktionsbezeichner <br>
	 * b) Aufruf mit Klasse <br>
	 * c) Auskommentieren der Intent-Filter und erneuter Aufruf <br>
	 * d) Aufruf mit Aktionsbezeichner und erneuter Aufruf <br>
	 * <br>
	 * <br>
	 * 2) Aufruf mit Rueckgabewert
	 * 
	 */
	private void callBeateWithMessage(String message) {
		/*
		 * Aufruf von Beate
		 */
		Intent callBeateIntent;
		// Erzeugung des Intents mit Aktionsbezeichner
		callBeateIntent = new Intent(Beate.ACTION_NAME);

		// Erzeugung des Intents mit Beate Klasse
		// callBeateIntent = new Intent(Anna.this, Beate.class);

		// Hinzufuegung der Message als Argument fuer den Aufruf von Beate
		callBeateIntent.putExtra(Beate.MESSAGE_ARGUMENT_NAME, message);

		// Absenden des Intents ohne erwarteten Rueckgabewert
		// startActivity(callBeateIntent);

		// Absenden des Intents mit erwartetem Rueckgabewert
		startActivityForResult(callBeateIntent, REQUEST_CODE_CALL_BEATE);

	}

	/**
	 * 
	 * Callback bei Beendigung einer aufgerufenen Activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.i(logger, "onActivityResult(): " + requestCode + "/" + resultCode
				+ "/" + data);

		// ueberpruefe den requestCode
		switch (requestCode) {
		case REQUEST_CODE_CALL_BEATE:
			Log.i(logger,
					"onActivityResult(): read out return value from Beate...");
			switch (resultCode) {
			case Beate.RESULT_CODE_DONE:
				// lies den Wert des "Rueckgabefelds" aus!
				String msg = data.getExtras().getString(
						Beate.MESSAGE_RETURN_VALUE_NAME);
				Log.i(logger, "onActivityResult(): msg is: " + msg);
				conversation.append("\nbeate> " + msg);
				break;
			default:
				Log.e(logger, "onActivityResult(): unknown resultCode "
						+ resultCode + " for Beate requestCode " + requestCode);
			}
			break;
		default:
			Log.e(logger, "onActivityResult(): unknown requestCode: "
					+ requestCode);
		}

	}

}
