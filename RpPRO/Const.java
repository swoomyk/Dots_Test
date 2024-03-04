package RpPRO;

public class Const {

    /*
    Абстрактный класс для констант.
    Время в секундах. Измерено эмпирически для Су-34.
    Расстояния в километрах.
     */

    private static int time_second_turn=80;                  //время выполнения второго разворота
    private static int time_on_GP=180;                      //время снижения по глиссаде с удаления 20
    private static int range_one_min_by_GP=7;               //дистанция на ПК, проходимая за 1 минуту
    private static int range_first_turn=7;                  //удаление первого разворота
    private static int range_second_turn=21;                //удаление второго разворота
    private static int range_GP=20;                         //дистанция глиссады снижения
    private static int time_beetween_TO_LND=80;             //интервал времени между взлетом и посадкой



    public static int time_second_turn() {
        return time_second_turn;
    }
    public static int time_on_GP() {
        return time_on_GP;
    }
    public static int range_one_min_by_GP() {
        return range_one_min_by_GP;
    }
    public static int range_first_turn() {
        return range_first_turn;
    }
    public static int range_second_turn() {
        return range_second_turn;
    }
    public static int range_GP() {
        return range_GP;
    }
    public static int time_beetween_TO_LND(){
        return time_beetween_TO_LND;
    }
}
