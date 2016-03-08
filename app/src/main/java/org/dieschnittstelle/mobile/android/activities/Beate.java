package org.dieschnittstelle.mobile.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class Beate extends AbstractLifecycleLogger {
	
	/**
	 * logging
	 */
	protected static final String logger = "Beate";

	/**
	 * der Aktionsbezeichner
	 */
	public static final String ACTION_NAME = "org.dieschnittstelle.SHOW_INPUT";

	/**
	 * die als Argument uebergebene Nachricht
	 */
	public static final String MESSAGE_ARGUMENT_NAME = "message";

	/**
	 * die als Rueckgabewert zu uebergebende Nachricht
	 */
	public static final String MESSAGE_RETURN_VALUE_NAME = "returnMessage";

	/**
	 * der Resultatscode
	 */
	public static final int RESULT_CODE_DONE = 1;

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
		// wird, wird an die Activity Anna zurueck gegeben
		newMessage.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView view, int arg1, KeyEvent arg2) {
				Log.d(logger, "got editor action " + arg1 + " on textview "
						+ view + ". KeyEvent is: " + arg2);
				if (arg1 == EditorInfo.IME_ACTION_DONE || KeyEvent.KEYCODE_ENTER == arg2.getKeyCode()) {
					Log.d(logger, "done was pressed!");

					// Zugriff auf den eingegebenen Text
					String message = view.getText().toString();

					// aktualisiere die conversation (nur kurz sichtbar, da sofort die Rueckgabe
					// an Anna erfolgt)
					conversation.append("\nmyself> " + message);
					
					// Rueckgabe an Anna
					returnToAnna(message);

					return false;
				}
				return false;
			}

		});

		// lies das Message Argument aus dem Intent aus
		String msg = getIntent().getExtras().getString(MESSAGE_ARGUMENT_NAME);
		Log.i(logger, "onCreate(): message passed as argument is: " + msg);

		// aktualisiere die Conversation
		conversation.append("\nanna> " + msg);

	}

	/**
	 * setze die Nachricht als Rueckgabewert und beende die Activity
	 */
	protected void returnToAnna(String message) {

		// erzeuge einen Intent fuer das Ergebnis
		Intent returnIntent = new Intent();
		returnIntent.putExtra(MESSAGE_RETURN_VALUE_NAME, message);

		// setze den Intent als Ergebnis, zusammen mit einem Ergebniscode
		setResult(RESULT_CODE_DONE, returnIntent);

		// beende die Activity
		finish();
	}

}
