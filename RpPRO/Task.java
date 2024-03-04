package RpPRO;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Task extends Const{

    private int UTC_hours;                                  //Время текущее
    private int UTC_minute;
    private int TO_hours;                                   //Время взлета
    private int TO_minute;
    private int pre_range;                                  //Удаление от второго разворота
    private int fin_range;
    public void setUTC_time(){                              //Получения текущего времени
        Date time = new Date();
        UTC_hours = 19;//time.getHours();
        UTC_minute = 9;//time.getMinutes();
        System.out.println(UTC_hours+":"+UTC_minute+"");    //Для тестов
    }
    public void setTO_time(int hours, int minute){
        TO_hours = hours;
        TO_minute = minute;
    }
    public void setPre_range(int distance){
        pre_range=distance;
    }
    public int getPre_range() {
        return pre_range;
    }
    public void setFin_range(int distance){
        fin_range=distance;
    }
    public int getFin_range() {
        return fin_range;
    }

    //Время полета с текущей дальности до посадки со средней скоростью 600 км/ч (167 м/с). Проверен.
    private int arrival_time(){
        int arr_time = (getPre_range()*1000)/(167)+Const.time_second_turn()+Const.time_on_GP();
        return arr_time;
    }
    //Время, оставшееся до взлета очередного. Возвращает время в секундах. Проверен.
    private int departure_time(){
        int dep_time = 0;
        if (TO_minute>UTC_minute){
            dep_time = (TO_minute*60)-(UTC_minute*60);
        } else if (TO_minute<UTC_minute){
            dep_time = (((TO_minute*60)-(UTC_minute*60))+(60*60));
        }
        return dep_time;
    }
    //Очередность взлет/посадка. Проверен.
    public boolean TO_priority(){
        boolean on_RW = false;
        if ((arrival_time()+30)<departure_time()){
            on_RW = false;
            System.out.println("Сначала посадка, потом на взлетную");
        } else if ((arrival_time()+30)>=departure_time()) {
            on_RW = true;
            System.out.println("Сначала взлет, потом посадка " +
                    "предварительное удаление второго " + pre_range_second() + "");
        }
        System.out.println("Время до посадки " + arrival_time() + "\n" + "Время до взлета " + departure_time());
        return on_RW;
    }
    //Предварительное удаление второго разворота. Возвращает конкретное значение удаления второго.
    public int pre_range_second(){
        int pre_correction = 0;
        if((departure_time()-(arrival_time()-Const.time_on_GP()-Const.time_second_turn()))<=
                ((Const.time_on_GP()-Const.time_beetween_TO_LND())+Const.time_second_turn())){
            pre_correction = 20;
        } else
            pre_correction = 20+(((arrival_time()-departure_time())+Const.time_beetween_TO_LND())/20);
        return pre_correction;
    }
    //Окончательное удаление второго разворота. Пересчитывается по истечении 3/4 времени полета до второго разворота.
    public void fin_range_second(){
        int fin_correction = 0;
        int calc_range = getPre_range()-((int)((((arrival_time()-Const.time_second_turn()-Const.time_on_GP())*0.75)*167)/1000));
        System.out.println("Расчетное удаление до второго разворота " + calc_range + "");
        if (getFin_range()<calc_range){
            fin_correction = (calc_range-getFin_range())/2;
            System.out.println("Требуется погасить " + fin_correction + "");
            System.out.println("Удаление второго разворота " + (pre_range_second() + fin_correction) + "");
        } else if (getFin_range()>calc_range) {
            fin_correction = (getFin_range()-calc_range)/2;
            if((pre_range_second() - fin_correction)>=Const.range_second_turn()) {
                System.out.println("Удаление второго разворота " + (pre_range_second() - fin_correction) + "");
            } else if ((pre_range_second() - fin_correction)<Const.range_second_turn()) {
                System.out.println("Второй на удалении 20");
            }
        }

    }
    //Таймер отсчета трех четвертей пути. Проверен.
    public void counting_down(){

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int countdown = (int)((arrival_time()-Const.time_second_turn()-Const.time_on_GP())*0.75);
            @Override
            public void run() {
                System.out.println(countdown);
                if (countdown <= 0) {
                    fin_range_second();
                    timer.cancel();
                }
                countdown--;
            }
        }, 0, 1000);
    }
}

