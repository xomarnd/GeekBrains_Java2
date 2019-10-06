package Lesson_1.Testsubjects;

public class Cat implements Competitor {
    private final String name;
    private final int stamina;
    private final int maxJumpHeight;

//MainTask1
    public Cat() {
        this.name = null;
        this.stamina = 0;
        this.maxJumpHeight = 0;
    }

//MainTask2-3
    public Cat(String name, int stamina, int maxJumpHeight) {
        this.name = name;
        this.stamina = stamina;
        this.maxJumpHeight = maxJumpHeight;
    }


    @Override
    public int getRun() {
        return stamina;
    }

    @Override
    public int getJump() {
        return maxJumpHeight;
    }

    @Override
    public String getName() {
        return name;
    }

//MainTask1
    @Override
    public void run() {
        System.out.println("Кот бежит");
    }
    @Override
    public void jump() {
        System.out.println("Кот прыгает");
    }
}
