package Lesson_1;

import Lesson_1.Testsubjects.*;
import Lesson_1.obstracles.*;

/*
Task1:
Создайте три класса Человек, Кот, Робот, которые не наследуются от одного класса. Эти классы должны уметь бегать и
прыгать (методы просто выводят информацию о действии в консоль).
Task2:
Создайте два класса: беговая дорожка и стена, при прохождении через которые, участники должны выполнять
соответствующие действия (бежать или прыгать), результат выполнения печатаем в консоль (успешно пробежал, не смог
пробежать и т.д.). У препятствий есть длина (для дорожки) или высота (для стены), а участников ограничения на бег и прыжки.
Task3:
Создайте два массива: с участниками и препятствиями, и заставьте всех участников пройти этот набор препятствий.
Если участник не смог пройти одно из препятствий, то дальше по списку он препятствий не идет.
*/
public class Main {

    public static void main(String[] args) {

//------------Task1-----------------------
        System.out.println("Тренировка перед испытанием.\n");
        Competitor cat = new Cat();
        Competitor robot = new Robot();
        Competitor man = new Human();


        man.run();
        man.jump();
        cat.run();
        cat.jump();
        robot.run();
        robot.jump();

//------------Task2-----------------------
        System.out.println("\nРазминка перед стартом.\n");


        Competitor catV2 = new Cat("Нибблер", 50, 3);
        Competitor robotV2 = new Robot("Бендер", 10, 1);
        Competitor manV2 = new Human("Фрай", 100, 2);

        //создаем дорожку
        Treadmill track = new Treadmill("Дорожка", 100);

        if(track.doRun(catV2)) System.out.println(catV2.getName() + " " + "пробежал стометровку.");
        else System.out.println(catV2.getName() + " " + "не смог пробежать стометровку.");
        if(track.doRun(robotV2)) System.out.println(robotV2.getName() + " " + "смог пробежать стометровку.");
        else System.out.println(robotV2.getName() + " " + "не смог пробежать стометровку.");
        if(track.doRun(manV2)) System.out.println(manV2.getName() + " " + "смог пробежать стометровку.");
        else System.out.println(manV2.getName() + " " + "не смог пробежать стометровку.");

        //создаем стену
        Wall wall = new Wall("Стена", 3);

        if(wall.doJump(catV2)) System.out.println(catV2.getName() + " смог перепрыгнуть");
        else System.out.println(catV2.getName() + " не смог перепрыгнуть");
        if(wall.doJump(robotV2)) System.out.println(robotV2.getName() + " смог перепрыгнуть");
        else System.out.println(robotV2.getName() + " не смог перепрыгнуть");
        if(wall.doJump(manV2)) System.out.println(manV2.getName() + " смог перепрыгнуть");
        else System.out.println(manV2.getName() + " не смог перепрыгнуть");


//------------Task3-----------------------
        System.out.println("\nСостязания.\n");
//Испытуемые
        Competitor[] competitors = new Competitor[6];
        competitors[0] = new Human("Фрай", 350, 2);
        competitors[1] = new Cat("Нибблер", 400, 1);
        competitors[2] = new Robot("Бендер", 2000, 40);

//Препятствия
        Test[] obstacles = new Test[3];
        obstacles[0] = new Treadmill("Трек длиной 100", 100);
        obstacles[1] = new Treadmill("Трек длиной 500", 500);
        obstacles[2] = new Wall("Стена 1м", 1);


        for (int i = 0; i < competitors.length; i++) {
            int welldone = 0; //счетчик проденных препрятствий
            System.out.println("Участник: " + competitors[i].getName());
            for (int j = 0; j < obstacles.length; j++) {
//treadmill
                if (obstacles[j].isTrack()){
                    if(obstacles[j].doRun(competitors[i])){
                        System.out.println(obstacles[j].getTitle() + " " + "- смог пробежать.");
                        welldone++;
                    }else {
                        System.out.println(obstacles[j].getTitle() + " " + "- не смог пробежать.");
                        break;
                    }
//wall
                }else{
                    if (obstacles[j].doJump(competitors[i])){
                        System.out.println(obstacles[j].getTitle() + " " + "- смог перепрыгнуть.");
                        welldone++;
                    }else{
                        System.out.println(obstacles[j].getTitle() + " " + "- не смог перепрыгнуть.");
                        break;
                    }
                }
            }
//итоги
            if (welldone == obstacles.length) System.out.println(competitors[i].getName() + " " + "выходит из лабиринта живым!\n");
            else System.out.println(competitors[i].getName() + " не смог...\n");
        }
    }
}
