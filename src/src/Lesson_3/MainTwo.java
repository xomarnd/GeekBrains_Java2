package Lesson_3;

public class MainTwo {
    public static void main(String[] args){
        Phonebook phonebook = new Phonebook();
        System.out.println("\nПишем:");
        phonebook.add("Иванов", "+790512345");
        phonebook.add("Смирнов", "+790512346");
        phonebook.add("Смирнов", "+791612346");
        phonebook.add("Кузнецов", "+790512347");
        phonebook.add("Попов", "+790512348");
        phonebook.add("Васильев", "+790512349");

        System.out.println("\nЧитаем:");
        System.out.println("Иванов" + phonebook.get("Иванов"));
        System.out.println("Смирнов" + phonebook.get("Смирнов"));
        System.out.println("Кузнецов" + phonebook.get("Кузнецов"));
        System.out.println("Попов" + phonebook.get("Попов"));
        System.out.println("Васильев" + phonebook.get("Васильев"));

        System.out.println("\nНеудачный поиск:");
        System.out.println("Новиков" + phonebook.get("Новиков"));

        System.out.println("\nОтработка на дубликаты:");
        phonebook.add("Иванов", "+790512345");
        System.out.println("Иванов" + phonebook.get("Иванов"));
    }
}
