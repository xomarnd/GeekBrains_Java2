package Lesson_3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Phonebook {

    private HashMap<String, List<String>> book;

    public Phonebook(){
        this.book = new HashMap<>();
    }

    public void add(String name, String number){
        if(book.containsKey(name)){
            List<String> numbers = book.get(name);
            if(numbers.contains(number)){
                System.err.println(String.format("Уже существует контакт %s с номером %s", name, number));

            }else{
                numbers.add(number);
                System.out.println(String.format("Создан контакт %s с номером %s", name, number));
            }
        }else{
            book.put(name, new ArrayList<>(Arrays.asList(number)));
            System.out.println(String.format("Создан контакт %s с номером %s", name, number));
        }
    }

    public List<String> get(String name){
        if(book.containsKey(name)){
            return book.get(name);
        }else{
            System.err.println(String.format("В телефонной книге отсутствуют данные о %s", name));
            return new ArrayList<>();
        }
    }
}
