package RpPRO;

import java.util.Timer;
import java.util.TimerTask;


public class Main {
    public static void main(String[] args) {


        Task test = new Task();
        test.setUTC_time();
        test.setTO_time(19,11);
        test.setPre_range(5);      //Ввод дальности выхода на связь
        test.counting_down();
        test.TO_priority();         //Определение очередности взлет/посадка
        test.setFin_range(3);      //Повторный ввод дальности по истечении 3/4 времени полета ко второму
        System.out.println("Контрольный пакет изменений №1488");


    }
}
