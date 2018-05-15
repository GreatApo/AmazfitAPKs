package es.malvarez.mywatchfaces.widget;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTodayCaloriesView;

import java.util.Collections;
import java.util.List;

import com.dinodevs.greatfitwatchface.R;
import es.malvarez.mywatchfaces.data.DataType;
import es.malvarez.mywatchfaces.data.Calories;
import es.malvarez.mywatchfaces.resource.ResourceManager;



public class CaloriesWidget extends AbstractWidget {
    private Paint textPaint;
    private Calories calories;

    private float textTop = 58;
    private float textLeft = 195;


    @Override
    public void init(Service service) {
        this.textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.OSTRICH));
        this.textPaint.setTextSize(service.getResources().getDimension(R.dimen.malvarez_circles_font_size));
        this.textPaint.setColor(service.getResources().getColor(R.color.malvarez_time_colour));
        this.textPaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.CALORIES);
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        this.calories = (Calories) value;
    }

    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        //Calendar calendar = Calendar.getInstance();
        //calendar.get(Calendar.SECOND);
        String text = String.format("%s kcal", Math.round(calories.getCalories()));
        canvas.drawText(text, textLeft, textTop+Math.round(textPaint.getTextSize()*0.75), textPaint);
    }
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        SlptLinearLayout caloriesLayout = new SlptLinearLayout();
        SlptPictureView caloriesUnit = new SlptPictureView();
        caloriesUnit.setStringPicture(" kcal");
        caloriesLayout.add(new SlptTodayCaloriesView());
        caloriesLayout.add(caloriesUnit);
        caloriesLayout.setStart(
                (int) textLeft,
                (int) textTop
        );
        Typeface caloriesFont = ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.OSTRICH);

        caloriesLayout.setTextAttrForAll(
                26,
                -1,
                caloriesFont
        );
        //caloriesLayout.alignX = 2;
        //caloriesLayout.alignY = 0;
        //caloriesLayout.setRect(80,23);

        return Collections.<SlptViewComponent>singletonList(caloriesLayout);
    }
}
