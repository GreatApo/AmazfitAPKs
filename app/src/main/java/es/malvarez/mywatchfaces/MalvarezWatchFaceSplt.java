package es.malvarez.mywatchfaces;

import es.malvarez.mywatchfaces.widget.CirclesWidget;
import es.malvarez.mywatchfaces.widget.CaloriesWidget;
import es.malvarez.mywatchfaces.widget.MalvarezClock;

/**
 * Splt version of the watch.
 */

public class MalvarezWatchFaceSplt extends AbstractWatchFaceSlpt {

    public MalvarezWatchFaceSplt() {
        super(
                new MalvarezClock(),
                new CirclesWidget(),
                new CaloriesWidget()
        );
    }

//    @Override
    protected void initWatchFaceConfig() {

    }

}
