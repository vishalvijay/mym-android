package com.matrix.mym.controller.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.matrix.mym.model.Quiz;

public class QuizDB {
	public static final String TABLE_NAME = "quiz";
	public static final String COL_ID = "_id";
	public static final String COL_QUESTION = "question";
	public static final String COL_OPTION1 = "option1";
	public static final String COL_OPTION2 = "option2";
	public static final String COL_OPTION3 = "option3";
	public static final String COL_OPTION4 = "option4";
	public static final String COL_ANSWER = "answer";
	public static final String COL_TYPE = "type";

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY NOT NULL, "
			+ COL_QUESTION + " VARCHAR(255) NOT NULL, " + COL_OPTION1
			+ " VARCHAR(50), " + COL_OPTION2 + " VARCHAR(50), " + COL_OPTION3
			+ " VARCHAR(50), " + COL_OPTION4 + " VARCHAR(50), " + COL_ANSWER
			+ " INTEGER NOT NULL, " + COL_TYPE + " INTEGER NOT NULL " + ");";

	private DatabaseHelper mDatabaseHelper;

	public QuizDB(DatabaseHelper databaseHelper) {
		mDatabaseHelper = databaseHelper;
	}

	synchronized public Quiz getQuizById(long id) {
		Quiz quiz = null;
		try {
			SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, new String[] { COL_ID,
					COL_QUESTION, COL_OPTION1, COL_OPTION2, COL_OPTION3,
					COL_OPTION4, COL_ANSWER, COL_TYPE }, COL_ID + "=? ",
					new String[] { id + "" }, null, null, null);
			if (cursor.moveToNext()) {
				String question = cursor.getString(cursor
						.getColumnIndex(COL_QUESTION));
				ArrayList<String> options = new ArrayList<String>();
				options.add(cursor.getString(cursor.getColumnIndex(COL_OPTION1)));
				options.add(cursor.getString(cursor.getColumnIndex(COL_OPTION2)));
				options.add(cursor.getString(cursor.getColumnIndex(COL_OPTION3)));
				options.add(cursor.getString(cursor.getColumnIndex(COL_OPTION4)));
				int answer = cursor.getInt(cursor.getColumnIndex(COL_ANSWER));
				int type = cursor.getInt(cursor.getColumnIndex(COL_TYPE));
				quiz = new Quiz(id, question, options, answer, type);
			}
			cursor.close();
			db.close();
		} catch (IllegalStateException ex) {
		}
		return quiz;
	}

	public static void setUpTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
		inserQuiz(
				db,
				0,
				"Which are the two stock exchanges where most of the trading in Indian stock market takes place?",
				"BSE and NSE", "CSE and MSE", "BSE and MSE", "NSE and CSE", 0,
				Quiz.TYPE_4_OPTIONS);
		inserQuiz(
				db,
				1,
				"Which statutory body is responsible for the overall development, regulation and supervision of the stock market?",
				"RBI", "IRS", "SEBI", "NISM", 2, Quiz.TYPE_4_OPTIONS);
		inserQuiz(db, 2, "Where is the Bombay Stock Exchange located at?",
				"Lady Jamshetjee Road", "Dalal Street", "Lamington Road",
				"Marine Drive", 1, Quiz.TYPE_4_OPTIONS);
		inserQuiz(
				db,
				3,
				"Which term most accurately describes selling shares at a higher price than the price at which they were bought?",
				"Loss", "Asset", "Dividend", "Profit", 3, Quiz.TYPE_4_OPTIONS);
		inserQuiz(
				db,
				4,
				"Which term is used to describe a payout made to shareholders representing their share of a corporation’s profit?",
				"Jackpot", "Coupon", "Dividend", "Asset", 2,
				Quiz.TYPE_4_OPTIONS);
		inserQuiz(
				db,
				5,
				"Dematerialisation is the process of :",
				"Converting physical shares into electronic shares",
				"Selling electronic shares",
				"Converting electronic shares into physical shares",
				"Transferring electronic shares from one account to another account",
				0, Quiz.TYPE_4_OPTIONS);
		inserQuiz(db, 6,
				"The depository account that an investor opens is called as:",
				"Broker account", "Intermediary account",
				"Beneficiary account", "Clearing member account", 2,
				Quiz.TYPE_4_OPTIONS);
		inserQuiz(
				db,
				7,
				"Which of the following documents is not a valid proof of address (POA) for opening demat account?",
				"Pan Card", "Passport", "Voter Identity Card", "Bank Passbook",
				0, Quiz.TYPE_4_OPTIONS);
		inserQuiz(
				db,
				8,
				"Which of the following places does not have a stock exchange?",
				"Delhi", "Mumbai", "Cuttack", "Guwahati", 2,
				Quiz.TYPE_4_OPTIONS);
		inserQuiz(
				db,
				9,
				"\"Shareholder wealth\" in a firm is represented by:",
				"The number of people employed in the firm",
				"The book value of the firm's assets less the book value of its liabilities",
				"The amount of salary paid to its employees",
				"The market price per share of the firm's common stock", 3,
				Quiz.TYPE_4_OPTIONS);
		inserQuiz(
				db,
				10,
				"If you are bullish about a stock, you would do which of the following call options on the stock?",
				"Sell", "Buy", "Short", "Write", 1, Quiz.TYPE_4_OPTIONS);
		inserQuiz(
				db,
				11,
				"If you are bearish about a stock, you would do which of the following put options on the stock?",
				"Sell", "Buy", "Short", "Write", 0, Quiz.TYPE_4_OPTIONS);
		inserQuiz(db, 12,
				"The operations of private mutual funds are regulated by?",
				"SEBI", "Ministry of Finance", "AMFI", "NSCCL", 0,
				Quiz.TYPE_4_OPTIONS);
		inserQuiz(db, 13,
				"The registered owner of securities held in demat form is?",
				"The Depository participant", "The Depository", "SEBI",
				"The R&T Agent", 1, Quiz.TYPE_4_OPTIONS);
		inserQuiz(
				db,
				14,
				"SEBI, the capital market regulator is",
				"A division of the Ministry of Finance",
				"A self-regulatory body like AMFI,AMBI,stock exchanges etc. To police the capital market",
				"An autonomous body set-up under an act of parliament",
				"Set up by the Government of India to meet the requirements of IOSCO",
				2, Quiz.TYPE_4_OPTIONS);
		inserQuiz(db, 15, "ISIN stands for ______",
				"International Securities Identification number",
				"International Securities Identity number",
				"Indian Securities Identity number",
				"Indian Securities Identification number", 0,
				Quiz.TYPE_4_OPTIONS);

		inserQuiz(
				db,
				16,
				"Can shares that are not registered in the name of the account holder be dematerialised?",
				"Yes, at the discretion of the Issuer/ its R and T Agent",
				"No, shares have to be registered in the name of the concerned investor",
				"Yes, at the discretion of the DP",
				"Yes, provided a duly filled transfer deed is attached", 0,
				Quiz.TYPE_4_OPTIONS);
		inserQuiz(db, 17, "A mutual fund is a collection of?",
				"Stocks and cash", "Stocks, bonds ,and other securities",
				"Bonds", "Interests accrued on a stock", 1, Quiz.TYPE_4_OPTIONS);
	}

	private static void inserQuiz(SQLiteDatabase db, long id, String question,
			String option1, String option2, String option3, String option4,
			int answer, int type) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(COL_ID, id);
		contentValues.put(COL_QUESTION, question);
		contentValues.put(COL_OPTION1, option1);
		contentValues.put(COL_OPTION2, option2);
		contentValues.put(COL_OPTION3, option3);
		contentValues.put(COL_OPTION4, option4);
		contentValues.put(COL_ANSWER, answer);
		contentValues.put(COL_TYPE, type);
		db.insert(TABLE_NAME, COL_ID, contentValues);
	}

}
