package Lesson_1.obstracles;

import Lesson_1.Testsubjects.Competitor;

public class Treadmill implements Test {
    private final String title;
    private final int length;

    public Treadmill(String title, int length) {
        this.title = title;
        this.length = length;
    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean doRun(Competitor competitor){
        return competitor.getRun() >= length;
    }

    @Override
    public boolean doJump(Competitor competitor) {
        return false;
    }

    @Override
    public boolean isTrack(){
        return true;
    }

}
