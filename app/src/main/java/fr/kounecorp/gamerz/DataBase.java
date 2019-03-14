package fr.kounecorp.gamerz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "score.db";

    public final String SQL_CREATEJoueur = "CREATE TABLE Joueur (Pseudo VARCHAR(25) PRIMARY KEY)";
    public final String SQL_CREATEPartie = "CREATE TABLE Partie (Pseudo VARCHAR(25) NOT NULL," +
            "DateHeure DATETIME NOT NULL," +
            "tempsReactTime INT,"+
            "scoreFormClick INT, "+
            "tempsFormClick DECIMAL(15,3)," +
            "scoreFastClicker INT," +
            "FOREIGN KEY (Pseudo) REFERENCES Joueur(Pseudo),"+
            "PRIMARY KEY(Pseudo,DateHeure));";
    public final String SQL_DELETE = "DROP TABLE IF EXISTS Joueur";

    public DataBase(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATEJoueur);
        db.execSQL(SQL_CREATEPartie);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }

    public void addReactToDatabaseWherePseudo(String pseudo, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Partie SET tempsReactTime = " + score + " WHERE Pseudo = \'"+pseudo+"\'";
        db.execSQL(query);
    }

    public void addScoreFormToDatabaseWherePseudo(String pseudo, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Partie SET scoreFormClick = " + score + " WHERE Pseudo = \'"+pseudo+"\'";
        db.execSQL(query);
    }

    public void addTempsFormToDatabaseWherePseudo(String pseudo, double temps) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Partie SET tempsFormClick = " + temps + " WHERE Pseudo = \'"+pseudo+"\'";
        db.execSQL(query);
    }

    public void addScoreFastClickerToDatabaseWherePseudo(String pseudo, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Partie SET scoreFastClicker = " + score + " WHERE Pseudo = \'"+pseudo+"\'";
        db.execSQL(query);
    }

    public String getLastPseudo() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Pseudo FROM PARTIE ORDER BY DateHeure DESC LIMIT 1";
        Cursor data = db.rawQuery(query,null);
        String pseudo ="";
        if (data.moveToFirst()) {
            pseudo = data.getString(0);
        }
        data.close();
        return pseudo;

    }

    public int getLastScore(String nomJeu) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + nomJeu + " FROM PARTIE ORDER BY DateHeure DESC LIMIT 1";
        Cursor data = db.rawQuery(query,null);
        int score =-1;
        if (data.moveToFirst()) {
            score = data.getInt(0);
        }
        data.close();

        return score;
    }

    public double getLastTime() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT tempsFormClick FROM PARTIE ORDER BY DateHeure DESC LIMIT 1";
        Cursor data = db.rawQuery(query,null);
        double time =-1;
        if (data.moveToFirst()) {
            time = data.getDouble(0);
        }
        data.close();

        return time;
    }


}