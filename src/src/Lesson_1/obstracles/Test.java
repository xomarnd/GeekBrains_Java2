package Lesson_1.obstracles;

import Lesson_1.Testsubjects.Competitor;

public interface Test {
    boolean doRun(Competitor competitor);
    boolean doJump(Competitor competitor);
    boolean isTrack();
    String getTitle();
}
