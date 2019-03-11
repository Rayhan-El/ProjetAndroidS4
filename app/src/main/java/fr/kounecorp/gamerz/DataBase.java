package fr.kounecorp.gamerz;

import android.content.Context;
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
}