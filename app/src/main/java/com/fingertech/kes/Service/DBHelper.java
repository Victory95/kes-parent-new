package com.fingertech.kes.Service;


import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fingertech.kes.Activity.Model.Data;
import com.fingertech.kes.Rest.BookmarkTabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper{

    private static final String db_name ="school";
    private static final int db_version=2;
    public static final String TABLE_SQLite = "sqlite";
    private static final String TABLE_NAME = "myTable";
    private static final String UID="_id";     // Column I (Primary Key)
    private static final String NAME = "Name";// Table Name
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255));";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static Context onAttach(Context context) {
        String lang = getPersistedData(context, Locale.getDefault().getLanguage());
        return setLocale(context, lang);
    }
    public static Context onAttach(Context context,String defaultLanguage) {
        String lang = getPersistedData(context, defaultLanguage);
        return setLocale(context, lang);
    }

    public static Context setLocale(Context context, String lang) {
        persist(context,lang);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return upadateResource(context,lang);
        return updateResourceLegacy(context,lang);
    }


    @TargetApi(Build.VERSION_CODES.N)
    private static Context upadateResource(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourceLegacy (Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration= resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());

        return context;
    }

    private static void persist(Context context, String lang) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(SELECTED_LANGUAGE,lang);
        editor.apply();
    }


    private static String getPersistedData(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE,language);

    }


    public DBHelper(Context context) {
        super(context, db_name, null, db_version);
        // Auto generated
    }

    //mengeksekusi perintah SQL di atas untuk membuat tabel database baru
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "create table country( _id integer primary key, code varchar not null, negara varchar not null);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO country (code, negara) VALUES ('AD', 'Andorra'), \n" +
                " ('AE', 'United Arab Emirates'), \n" +
                " ('AF', 'Afghanistan'), \n" +
                " ('AG', 'Antigua and Barbuda'), \n" +
                " ('AI', 'Anguilla'), \n" +
                " ('AL', 'Albania'), \n" +
                " ('AM', 'Armenia'), \n" +
                " ('AN', 'Netherlands Antilles'), \n" +
                " ('AO', 'Angola'), \n" +
                " ('AQ', 'Antarctica'), \n" +
                " ('AR', 'Argentina'), \n" +
                " ('AS', 'American Samoa'), \n" +
                " ('AT', 'Austria'), \n" +
                " ('AU', 'Australia'), \n" +
                " ('AW', 'Aruba'), \n" +
                " ('AX', 'Aland Islands'), \n" +
                " ('AZ', 'Azerbaijan'), \n" +
                " ('BA', 'Bosnia and Herzegovina'), \n" +
                " ('BB', 'Barbados'), \n" +
                " ('BD', 'Bangladesh'), \n" +
                " ('BE', 'Belgium'), \n" +
                " ('BF', 'Burkina Faso'), \n" +
                " ('BG', 'Bulgaria'), \n" +
                " ('BH', 'Bahrain'), \n" +
                " ('BI', 'Burundi'), \n" +
                " ('BJ', 'Benin'), \n" +
                " ('BL', 'Saint Barthelemy'), \n" +
                " ('BM', 'Bermuda'), \n" +
                " ('BN', 'Brunei'), \n" +
                " ('BO', 'Bolivia'), \n" +
                " ('BQ', 'Bonaire, Saint Eustatius and Saba '), \n" +
                " ('BR', 'Brazil'), \n" +
                " ('BS', 'Bahamas'), \n" +
                " ('BT', 'Bhutan'), \n" +
                " ('BV', 'Bouvet Island'), \n" +
                " ('BW', 'Botswana'), \n" +
                " ('BY', 'Belarus'), \n" +
                " ('BZ', 'Belize'), \n" +
                " ('CA', 'Canada'), \n" +
                " ('CC', 'Cocos Islands'), \n" +
                " ('CD', 'Democratic Republic of the Congo'), \n" +
                " ('CF', 'Central African Republic'), \n" +
                " ('CG', 'Republic of the Congo'), \n" +
                " ('CH', 'Switzerland'), \n" +
                " ('CI', 'Ivory Coast'), \n" +
                " ('CK', 'Cook Islands'), \n" +
                " ('CL', 'Chile'), \n" +
                " ('CM', 'Cameroon'), \n" +
                " ('CN', 'China'), \n" +
                " ('CO', 'Colombia'), \n" +
                " ('CR', 'Costa Rica'), \n" +
                " ('CS', 'Serbia and Montenegro'), \n" +
                " ('CU', 'Cuba'), \n" +
                " ('CV', 'Cape Verde'), \n" +
                " ('CW', 'Curacao'), \n" +
                " ('CX', 'Christmas Island'), \n" +
                " ('CY', 'Cyprus'), \n" +
                " ('CZ', 'Czech Republic'), \n" +
                " ('DE', 'Germany'), \n" +
                " ('DJ', 'Djibouti'), \n" +
                " ('DK', 'Denmark'), \n" +
                " ('DM', 'Dominica'), \n" +
                " ('DO', 'Dominican Republic'), \n" +
                " ('DZ', 'Algeria'), \n" +
                " ('EC', 'Ecuador'), \n" +
                " ('EE', 'Estonia'), \n" +
                " ('EG', 'Egypt'), \n" +
                " ('EH', 'Western Sahara'), \n" +
                " ('ER', 'Eritrea'), \n" +
                " ('ES', 'Spain'), \n" +
                " ('ET', 'Ethiopia'), \n" +
                " ('FI', 'Finland'), \n" +
                " ('FJ', 'Fiji'), \n" +
                " ('FK', 'Falkland Islands'), \n" +
                " ('FM', 'Micronesia'), \n" +
                " ('FO', 'Faroe Islands'), \n" +
                " ('FR', 'France'), \n" +
                " ('GA', 'Gabon'), \n" +
                " ('GB', 'United Kingdom'), \n" +
                " ('GD', 'Grenada'), \n" +
                " ('GE', 'Georgia'), \n" +
                " ('GF', 'French Guiana'), \n" +
                " ('GG', 'Guernsey'), \n" +
                " ('GH', 'Ghana'), \n" +
                " ('GI', 'Gibraltar'), \n" +
                " ('GL', 'Greenland'), \n" +
                " ('GM', 'Gambia'), \n" +
                " ('GN', 'Guinea'), \n" +
                " ('GP', 'Guadeloupe'), \n" +
                " ('GQ', 'Equatorial Guinea'), \n" +
                " ('GR', 'Greece'), \n" +
                " ('GS', 'South Georgia and the South Sandwich Islands'), \n" +
                " ('GT', 'Guatemala'), \n" +
                " ('GU', 'Guam'), \n" +
                " ('GW', 'Guinea-Bissau'), \n" +
                " ('GY', 'Guyana'), \n" +
                " ('HK', 'Hong Kong'), \n" +
                " ('HM', 'Heard Island and McDonald Islands'), \n" +
                " ('HN', 'Honduras'), \n" +
                " ('HR', 'Croatia'), \n" +
                " ('HT', 'Haiti'), \n" +
                " ('HU', 'Hungary'), \n" +
                " ('ID', 'Indonesia'), \n" +
                " ('IE', 'Ireland'), \n" +
                " ('IL', 'Israel'), \n" +
                " ('IM', 'Isle of Man'), \n" +
                " ('IN', 'India'), \n" +
                " ('IO', 'British Indian Ocean Territory'), \n" +
                " ('IQ', 'Iraq'), \n" +
                " ('IR', 'Iran'), \n" +
                " ('IS', 'Iceland'), \n" +
                " ('IT', 'Italy'), \n" +
                " ('JE', 'Jersey'), \n" +
                " ('JM', 'Jamaica'), \n" +
                " ('JO', 'Jordan'), \n" +
                " ('JP', 'Japan'), \n" +
                " ('KE', 'Kenya'), \n" +
                " ('KG', 'Kyrgyzstan'), \n" +
                " ('KH', 'Cambodia'), \n" +
                " ('KI', 'Kiribati'), \n" +
                " ('KM', 'Comoros'), \n" +
                " ('KN', 'Saint Kitts and Nevis'), \n" +
                " ('KP', 'North Korea'), \n" +
                " ('KR', 'South Korea'), \n" +
                " ('KW', 'Kuwait'), \n" +
                " ('KY', 'Cayman Islands'), \n" +
                " ('KZ', 'Kazakhstan'), \n" +
                " ('LA', 'Laos'), \n" +
                " ('LB', 'Lebanon'), \n" +
                " ('LC', 'Saint Lucia'), \n" +
                " ('LI', 'Liechtenstein'), \n" +
                " ('LK', 'Sri Lanka'), \n" +
                " ('LR', 'Liberia'), \n" +
                " ('LS', 'Lesotho'), \n" +
                " ('LT', 'Lithuania'), \n" +
                " ('LU', 'Luxembourg'), \n" +
                " ('LV', 'Latvia'), \n" +
                " ('LY', 'Libya'), \n" +
                " ('MA', 'Morocco'), \n" +
                " ('MC', 'Monaco'), \n" +
                " ('MD', 'Moldova'), \n" +
                " ('ME', 'Montenegro'), \n" +
                " ('MF', 'Saint Martin'), \n" +
                " ('MG', 'Madagascar'), \n" +
                " ('MH', 'Marshall Islands'), \n" +
                " ('MK', 'Macedonia'), \n" +
                " ('ML', 'Mali'), \n" +
                " ('MM', 'Myanmar'), \n" +
                " ('MN', 'Mongolia'), \n" +
                " ('MO', 'Macao'), \n" +
                " ('MP', 'Northern Mariana Islands'), \n" +
                " ('MQ', 'Martinique'), \n" +
                " ('MR', 'Mauritania'), \n" +
                " ('MS', 'Montserrat'), \n" +
                " ('MT', 'Malta'), \n" +
                " ('MU', 'Mauritius'), \n" +
                " ('MV', 'Maldives'), \n" +
                " ('MW', 'Malawi'), \n" +
                " ('MX', 'Mexico'), \n" +
                " ('MY', 'Malaysia'), \n" +
                " ('MZ', 'Mozambique'), \n" +
                " ('NA', 'Namibia'), \n" +
                " ('NC', 'New Caledonia'), \n" +
                " ('NE', 'Niger'), \n" +
                " ('NF', 'Norfolk Island'), \n" +
                " ('NG', 'Nigeria'), \n" +
                " ('NI', 'Nicaragua'), \n" +
                " ('NL', 'Netherlands'), \n" +
                " ('NO', 'Norway'), \n" +
                " ('NP', 'Nepal'),\n" +
                " ('NR', 'Nauru'), \n" +
                " ('NU', 'Niue'), \n" +
                " ('NZ', 'New Zealand'), \n" +
                " ('OM', 'Oman'), \n" +
                " ('PA', 'Panama'), \n" +
                " ('PE', 'Peru'), \n" +
                " ('PF', 'French Polynesia'), \n" +
                " ('PG', 'Papua New Guinea'), \n" +
                " ('PH', 'Philippines'), \n" +
                " ('PK', 'Pakistan'), \n" +
                " ('PL', 'Poland'), \n" +
                " ('PM', 'Saint Pierre and Miquelon'), \n" +
                " ('PN', 'Pitcairn'), \n" +
                " ('PR', 'Puerto Rico'), \n" +
                " ('PS', 'Palestinian Territory'), \n" +
                " ('PT', 'Portugal'), \n" +
                " ('PW', 'Palau'), \n" +
                " ('PY', 'Paraguay'), \n" +
                " ('QA', 'Qatar'), \n" +
                " ('RE', 'Reunion'), \n" +
                " ('RO', 'Romania'), \n" +
                " ('RS', 'Serbia'), \n" +
                " ('RU', 'Russia'), \n" +
                " ('RW', 'Rwanda'), \n" +
                " ('SA', 'Saudi Arabia'), \n" +
                " ('SB', 'Solomon Islands'), \n" +
                " ('SC', 'Seychelles'), \n" +
                " ('SD', 'Sudan'), \n" +
                " ('SE', 'Sweden'), \n" +
                " ('SG', 'Singapore'), \n" +
                " ('SH', 'Saint Helena'), \n" +
                " ('SI', 'Slovenia'), \n" +
                " ('SJ', 'Svalbard and Jan Mayen'), \n" +
                " ('SK', 'Slovakia'), \n" +
                " ('SL', 'Sierra Leone'), \n" +
                " ('SM', 'San Marino'), \n" +
                " ('SN', 'Senegal'), \n" +
                " ('SO', 'Somalia'), \n" +
                " ('SR', 'Suriname'), \n" +
                " ('SS', 'South Sudan'), \n" +
                " ('ST', 'Sao Tome and Principe'), \n" +
                " ('SV', 'El Salvador'), \n" +
                " ('SX', 'Sint Maarten'), \n" +
                " ('SY', 'Syria'), \n" +
                " ('SZ', 'Swaziland'), \n" +
                " ('TC', 'Turks and Caicos Islands'), \n" +
                " ('TD', 'Chad'), \n" +
                " ('TF', 'French Southern Territories'), \n" +
                " ('TG', 'Togo'), \n" +
                " ('TH', 'Thailand'), \n" +
                " ('TJ', 'Tajikistan'), \n" +
                " ('TK', 'Tokelau'), \n" +
                " ('TL', 'East Timor'), \n" +
                " ('TM', 'Turkmenistan'), \n" +
                " ('TN', 'Tunisia'), \n" +
                " ('TO', 'Tonga'), \n" +
                " ('TR', 'Turkey'), \n" +
                " ('TT', 'Trinidad and Tobago'), \n" +
                " ('TV', 'Tuvalu'), \n" +
                " ('TW', 'Taiwan'), \n" +
                " ('TZ', 'Tanzania'), \n" +
                " ('UA', 'Ukraine'), \n" +
                " ('UG', 'Uganda'), \n" +
                " ('UM', 'United States Minor Outlying Islands'), \n" +
                " ('US', 'United States'), \n" +
                " ('UY', 'Uruguay'), \n" +
                " ('UZ', 'Uzbekistan'), \n" +
                " ('VA', 'Vatican'), \n" +
                " ('VC', 'Saint Vincent and the Grenadines'), \n" +
                " ('VE', 'Venezuela'), \n" +
                " ('VG', 'British Virgin Islands'), \n" +
                " ('VI', 'U.S. Virgin Islands'), \n" +
                " ('VN', 'Vietnam'), \n" +
                " ('VU', 'Vanuatu'), \n" +
                " ('WF', 'Wallis and Futuna'), \n" +
                " ('WS', 'Samoa'), \n" +
                " ('XK', 'Kosovo'), \n" +
                " ('YE', 'Yemen'), \n" +
                " ('YT', 'Mayotte'), \n" +
                " ('ZA', 'South Africa'), \n" +
                " ('ZM', 'Zambia'), \n" +
                " ('ZW', 'Zimbabwe');";

        db.execSQL(BookmarkTabel.createTable());
        db.execSQL(sql);
        String position = "create table kodetable( _id integer primary key, position varchar not null);";
        db.execSQL(position);
        position = "INSERT INTO kodetable(position) VALUES ('0')";
        db.execSQL(position);
    }

    // dijalankan apabila ingin mengupgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE);
        onCreate(db);
    }
    public String getData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {DBHelper.UID,"position"};
        Cursor cursor =db.query("kodetable",columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(DBHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex("position"));
            buffer.append(name);
        }
        return buffer.toString();
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("position",newName);
        String[] whereArgs= {oldName};
        int count =db.update("kodetable",contentValues, "position"+" = ?",whereArgs );
        return count;
    }

    // Select All Data
    public Cursor SelectAllData() {
        // TODO Auto-generated method stub

        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String selectQuery = "SELECT  * FROM kodetable" ;
            Cursor cursor = db.rawQuery(selectQuery, null);

            return cursor;

        } catch (Exception e) {
            return null;
        }
        }
    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM country ORDER BY negara ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public List<String> getposisi(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM kodetable";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
//        cursor.close();
//        db.close();

        // returning lables
        return labels;
    }
}
