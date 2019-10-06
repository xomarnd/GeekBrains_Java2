package Lesson_1.obstracles;

import Lesson_1.Testsubjects.Competitor;

public class Wall implements Test {
    private final String title;
    private final int height;

    public Wall(String title, int height) {
        this.title = title;
        this.height = height;
    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean doRun(Competitor competitor) {
        return false;
    }

    @Override
    public boolean doJump(Competitor competitor){
        return competitor.getJump() >= height;
    }

    @Override
    public boolean isTrack(){
        return false;
    }


}
