package es.malvarez.mywatchfaces.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

import com.huami.watch.watchface.util.Util;
import com.huami.watch.watchface.util.WatchFaceConfig;
import com.ingenic.iwds.slpt.view.core.SlptAbsoluteLayout;
import com.ingenic.iwds.slpt.view.core.SlptLayout;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptNumView;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.digital.SlptDayHView;
import com.ingenic.iwds.slpt.view.digital.SlptDayLView;
import com.ingenic.iwds.slpt.view.digital.SlptHourHView;
import com.ingenic.iwds.slpt.view.digital.SlptHourLView;
import com.ingenic.iwds.slpt.view.digital.SlptMinuteHView;
import com.ingenic.iwds.slpt.view.digital.SlptMinuteLView;
import com.ingenic.iwds.slpt.view.digital.SlptMonthHView;
import com.ingenic.iwds.slpt.view.digital.SlptMonthLView;
import com.ingenic.iwds.slpt.view.digital.SlptWeekView;
import com.ingenic.iwds.slpt.view.digital.SlptYear0View;
import com.ingenic.iwds.slpt.view.sport.SlptPowerNumView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


import com.dinodevs.greatfitwatchface.APsettings;
import com.dinodevs.greatfitwatchface.R;
import com.ravenliquid.watchfaces.Utility;

import es.malvarez.mywatchfaces.resource.ResourceManager;

/**
 * Clock
 */
public class MalvarezClock extends DigitalClockWidget {

    private TextPaint hourFont;
    private TextPaint timeFont;
    private TextPaint dateFont;
    private TextPaint ampmFont;

    private Drawable background;
    private int backgroundColor;
    private float sizeHour = 192;
    private float sizeMinute = 130;
    private float leftHour = 60;
    private float topHour = 87;
    private float leftMinute = 174;
    private float topMinute = 93;
    private float leftDate;
    private float topDate;
    private float leftAMPM = 256;
    private float topAMPM = 164;

    private String[] digitalNums =  {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static String[] colors = {"#ff0000", "#00ffff","#00ff00","#ff00ff","#ffffff","#ffff00"};
    public int color = 3;
    public int language = 0;
    // Load settings
    public APsettings settings;

    //private int language = 0;
    // Languages
    public static String[] codes = {
            "English", "中文", "Czech", "Français", "Deutsch", "Ελληνικά", "עברית", "Magyar", "Italiano", "日本語", "Polski", "Português", "Русский", "Slovenčina", "Español"//, "Türkçe",
    };

    private static String[][] days = {
        //{"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"},
        {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"},
        {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"},
        { "NE", "PO", "ÚT", "ST", "ČT", "PÁ", "SO"},
        {"DIM", "LUN", "MAR", "MER", "JEU", "VEN", "SAM"},
        {"SON", "MON", "DIE", "MIT", "DON", "FRE", "SAM"},
        {"ΚΥΡ", "ΔΕΥ", "ΤΡΙ", "ΤΕΤ", "ΠΕΜ", "ΠΑΡ", "ΣΑΒ"},
        {"ש'","ו'","ה'","ד'","ג'","ב'","א'"},
        {"VAS", "HÉT", "KED", "SZE", "CSÜ", "PÉN", "SZO"},
        {"DOM", "LUN", "MAR", "MER", "GIO", "VEN", "SAB"},
        {"日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"},
        {"NIE", "PON", "WTO", "ŚRO", "CZW", "PIĄ", "SOB"},
        {"DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB"},
        {"ВОС", "ПОН", "ВТО", "СРЕ", "ЧЕТ", "ПЯТ", "СУБ"},
        {"NED", "PON", "UTO", "STR", "ŠTV", "PIA", "SOB"},
        {"DOM", "LUN", "MAR", "MIÉ", "JUE", "VIE", "SÁB"}//,
        //{"PAZAR", "PAZARTESI", "SALI", "ÇARŞAMBA", "PERŞEMBE", "CUMA", "CUMARTESI"},
    };

    private static String[][] months = {
        //{"DECEMBER", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"},
        {"DEC", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV"},
        {"十二月", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月"},
        {"PRO", "LED", "ÚNO", "BŘE", "DUB", "KVĚ", "ČER", "ČER", "SRP", "ZÁŘ", "ŘÍJ", "LIS"},
        {"DÉC", "JAN", "FÉV", "MAR", "AVR", "MAI", "JUI", "JUI", "AOÛ", "SEP", "OCT", "NOV"},
        {"DEZ", "JAN", "FEB", "MÄR", "APR", "MAI", "JUN", "JUL", "AUG", "SEP", "OKT", "NOV"},
        {"ΔΕΚ", "ΙΑΝ", "ΦΕΒ", "ΜΑΡ", "ΑΠΡ", "ΜΑΙ", "ΙΟΥΝ", "ΙΟΥΛ", "ΑΥΓ", "ΣΕΠ", "ΟΚΤ", "ΝΟΕ"},
        {"דצמ", "ינו", "פבר", "מרץ", "אפר", "מאי", "יונ", "יול", "אוג", "ספט", "אוק", "נוב"},
        {"DEC", "JAN", "FEB", "MÁR", "ÁPR", "MÁJ", "JÚN", "JÚL", "AUG", "SZE", "OKT", "NOV"},
        {"DIC", "GEN", "FEB", "MAR", "APR", "MAG", "GIU", "LUG", "AGO", "SET", "OTT", "NOV"},
        {"12月", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月"},
        {"GRU", "STY", "LUT", "MAR", "KWI", "MAJ", "CZE", "LIP", "SIE", "WRZ", "PAŹ", "LIS"},
        {"DEZ", "JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV"},
        {"ДЕК", "ЯНВ", "ФЕВ", "МАР", "АПР", "МАЙ", "ИЮН", "ИЮЛ", "АВГ", "СЕН", "ОКТ", "НОЯ"},
        {"DEC", "JAN", "FEB", "MAR", "APR", "MÁJ", "JÚN", "JÚL", "AUG", "SEP", "OKT", "NOV"},
        {"DIC", "ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV"}//,
        //{"ARALIK", "OCAK", "ŞUBAT", "MART", "NISAN", "MAYIS", "HAZIRAN", "TEMMUZ", "AĞUSTOS", "EYLÜL", "EKIM", "KASIM"},
    };

    @Override
    public void init(Service service) {
        this.settings = new APsettings(MalvarezClock.class.getName(), service);
        this.language = this.settings.get("lang", this.language) % this.codes.length;
        this.color = this.settings.getInt("color",this.color);

        this.backgroundColor = service.getResources().getColor(R.color.malvarez_background);
        this.background = service.getResources().getDrawable(R.drawable.background, null);
        this.background.setBounds(0, 0, 320, 300);

        //this.leftHour = service.getResources().getDimension(R.dimen.malvarez_time_hour_left);
        //this.topHour = service.getResources().getDimension(R.dimen.malvarez_time_hour_top);
        //this.leftMinute = service.getResources().getDimension(R.dimen.malvarez_time_minute_left);
        //this.topMinute = service.getResources().getDimension(R.dimen.malvarez_time_minute_top);
        this.leftDate = service.getResources().getDimension(R.dimen.malvarez_date_left);
        this.topDate = service.getResources().getDimension(R.dimen.malvarez_date_top);

        this.hourFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.hourFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.OSTRICH));
        this.hourFont.setTextSize(sizeHour);//service.getResources().getDimension(R.dimen.malvarez_time_font_size)
        this.hourFont.setColor(Color.parseColor(colors[this.color]));//Color.parseColor("#ba0dfb")
        this.hourFont.setTextAlign(Paint.Align.LEFT);

        this.timeFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.timeFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.OSTRICH));
        this.timeFont.setTextSize(sizeMinute);//service.getResources().getDimension(R.dimen.malvarez_time_font_size)
        this.timeFont.setColor(Color.parseColor("#FFFFFF"));
        this.timeFont.setTextAlign(Paint.Align.LEFT);

        this.dateFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.dateFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.OSTRICH));
        this.dateFont.setTextSize(service.getResources().getDimension(R.dimen.malvarez_date_font_size));
        this.dateFont.setColor(Color.parseColor("#FFFFFF"));//service.getResources().getColor(R.color.malvarez_time_colour)
        this.dateFont.setTextAlign(Paint.Align.LEFT);

        this.ampmFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.ampmFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.OSTRICH));
        this.ampmFont.setTextSize(32);
        this.ampmFont.setColor(Color.parseColor(colors[this.color]));//Color.parseColor("#ba0dfb")
        this.ampmFont.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void onDrawDigital(Canvas canvas, float width, float height, float centerX, float centerY, int seconds, int minutes, int hours, int year, int month, int day, int week, int ampm) {
        canvas.drawColor(backgroundColor);
        this.background.draw(canvas);

        canvas.drawText( Util.formatTime(hours), leftHour, topHour+Math.round(this.hourFont.getTextSize()*0.75), this.hourFont);

        canvas.drawText(Util.formatTime(minutes), leftMinute, topMinute+Math.round(this.timeFont.getTextSize()*0.75), this.timeFont);

        Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.DAY_OF_WEEK, week);
        //String date = String.format("%02d / %02d  %s", day, month, new SimpleDateFormat("EE").format(calendar.getTime()));
        //canvas.drawText(date, leftDate, topDate, this.dateFont);

        int weekday_num = calendar.get(Calendar.DAY_OF_WEEK)-1; // Starts from Monday
        int month_num = (calendar.get(Calendar.MONTH)+1)%12;

        //monthLayout
        canvas.drawText(
                //new SimpleDateFormat("MMM").format(calendar.getTime())
                ((months[language][month_num].length()>3) ? months[language][month_num].substring(0, 3) : months[language][month_num])
                , 190, 210+Math.round(this.dateFont.getTextSize()*0.75), this.dateFont);
        // 190
        // 210

        // weekView
        canvas.drawText(
                //new SimpleDateFormat("EE").format(calendar.getTime())
                ((days[language][weekday_num].length()>3) ? days[language][weekday_num].substring(0, 3) : days[language][weekday_num]) + " "+new SimpleDateFormat("dd").format(calendar.getTime())
                , 190, 235+Math.round(this.dateFont.getTextSize()*0.75), this.dateFont);
        // 190
        // 235

        // AM/PM
        canvas.drawText(
                new SimpleDateFormat("a").format(calendar.getTime())
               , leftAMPM, topAMPM+Math.round(this.ampmFont.getTextSize()*0.75), this.ampmFont);
    }

    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        this.settings = new APsettings(MalvarezClock.class.getName(), service);
        this.language = this.settings.get("lang", this.language) % this.codes.length;
        this.color = this.settings.getInt("color",this.color);

        SlptPictureView background = new SlptPictureView();
        background.setImagePicture(SimpleFile.readFileFromAssets(service.getApplicationContext(), "new_background_splt.png"));

        SlptLinearLayout hourLayout = new SlptLinearLayout();
        hourLayout.add(new SlptHourHView());
        hourLayout.add(new SlptHourLView());
        Utility.setStringPictureArrayForAll(hourLayout, this.digitalNums);

        SlptLinearLayout minuteLayout = new SlptLinearLayout();
        minuteLayout.add(new SlptMinuteHView());
        minuteLayout.add(new SlptMinuteLView());
        Utility.setStringPictureArrayForAll(minuteLayout, this.digitalNums);

        Typeface timeTypeFace = ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.OSTRICH);

        hourLayout.setTextAttrForAll(
                sizeHour,//service.getResources().getDimension(R.dimen.malvarez_time_font_size),
                Color.parseColor(colors[this.color]),//Color.parseColor("#ff00ff")
                timeTypeFace);
        minuteLayout.setTextAttrForAll(
                sizeMinute,//service.getResources().getDimension(R.dimen.malvarez_time_font_size),
                -1,//-65536,
                timeTypeFace);
        hourLayout.setStart(
                (int) leftHour,//(int) service.getResources().getDimension(R.dimen.malvarez_time_hour_left_slpt),
                (int) topHour);//(int) service.getResources().getDimension(R.dimen.malvarez_time_hour_top_slpt));
        minuteLayout.setStart(
                (int) leftMinute,//(int) service.getResources().getDimension(R.dimen.malvarez_time_minute_left_slpt),
                (int) topMinute);//(int) service.getResources().getDimension(R.dimen.malvarez_time_minute_top_slpt));


        Calendar calendar = Calendar.getInstance();
        int weekday_num = calendar.get(Calendar.DAY_OF_WEEK); // Starts from Monday
        int month_num = calendar.get(Calendar.MONTH); // Starts from Monday

        // Set Day (String _ Number)
        SlptLinearLayout dayLayout = new SlptLinearLayout();
        dayLayout.add(new SlptWeekView());
        dayLayout.setStringPictureArrayForAll(days[language]);
        // set space
        SlptPictureView dayTextLayout = new SlptPictureView();
        dayTextLayout.setStringPicture(" ");
        dayLayout.add(dayTextLayout);
        // set day in month
        dayLayout.add(new SlptDayHView());
        dayLayout.add(new SlptDayLView());

        //SlptPictureView dayTextLayout = new SlptPictureView();
        //dayTextLayout.setStringPicture((days[language][weekday_num].length()>3) ? days[language][weekday_num].substring(0, 3)+" "+new SimpleDateFormat("dd").format(calendar.getTime()) : days[language][weekday_num]+" "+new SimpleDateFormat("dd").format(calendar.getTime()));
        //dayLayout.add(dayTextLayout);

        // Set Month string
        SlptLinearLayout monthLayout = new SlptLinearLayout();
        monthLayout.add(new SlptMonthLView());
        monthLayout.setStringPictureArrayForAll(months[language]);
        //monthLayout.add(new SlptMonthHView());
        //monthLayout.add(new SlptMonthLView());
        /*
        SlptPictureView monthTextLayout = new SlptPictureView();
        monthTextLayout.setStringPicture((months[language][month_num].length()>3) ? months[language][month_num].substring(0, 3) : months[language][month_num]);
        monthLayout.add(monthTextLayout);
        */

        /*
        SlptLinearLayout ampmView = new SlptLinearLayout();
        SlptPictureView ampmTextLayout = new SlptPictureView();
        String am_pm = new SimpleDateFormat("a").format(calendar.getTime());
        ampmTextLayout.setStringPicture(am_pm);
        ampmView.add(ampmTextLayout);

        */

        for (SlptLayout component : Arrays.asList(monthLayout, dayLayout)) {//, ampmView
            component.setTextAttrForAll(
                    service.getResources().getDimension(R.dimen.malvarez_date_font_size),
                    -1,
                    timeTypeFace
            );
        }
        /*
        ampmView.setTextAttrForAll(
                32,
                this.color,//Color.parseColor("#ff00ff")
                timeTypeFace
        );
        */
        monthLayout.setStart(
                190,//(int) service.getResources().getDimension(R.dimen.malvarez_month_left_slpt),
                210);//(int) service.getResources().getDimension(R.dimen.malvarez_month_top_slpt));

        dayLayout.setStart(
                190,//(int) service.getResources().getDimension(R.dimen.malvarez_date_left_slpt),
                235);//(int) service.getResources().getDimension(R.dimen.malvarez_date_top_slpt));
        /*
        ampmView.setStart(
                (int) leftAMPM,//(int) service.getResources().getDimension(R.dimen.malvarez_week_left_slpt),
                (int) topAMPM);//(int) service.getResources().getDimension(R.dimen.malvarez_week_top_slpt));
        */
        return Arrays.asList(background, hourLayout, minuteLayout, dayLayout, monthLayout);//, ampmView
    }
}
