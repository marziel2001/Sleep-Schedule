import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.util.concurrent.TimeUnit.MINUTES;

public class Solution {

    public static enum DaysEnum
    {
        Mon, Tue, Wed, Thu,
        Fri, Sat, Sun;
    }

        private static ArrayList<LocalTime[]>[] getData(String data)
        {
            ArrayList<LocalTime[]>[] days = new ArrayList[7];
            String[] lines = data.split("\\n");

            for(int i = 0; i<DaysEnum.values().length; i++)
            {
                days[i] = new ArrayList<LocalTime[]>();
            }

            // now let's split the strings to get the actual data
            for (String line:lines)
            {
                String[] day = line.split("\\s+");

                String start = day[1].split("[-]")[0];
                String end = day[1].split("[-]")[1];

                if(start.equals("24:00")) end="23:59";
                if(end.equals("24:00")) end="23:59";

                LocalTime startTime = LocalTime.parse(start+":00");
                LocalTime endTime = LocalTime.parse(end+":00");

                LocalTime[] period = {startTime, endTime};
                days[DaysEnum.valueOf(day[0]).ordinal()].add(period);
            }


            // sorting start and finish hours together
            for (int i = 0; i<7; i++)
            {
                Collections.sort(days[i], new Comparator<LocalTime[]>()
                {
                    @Override
                    public int compare(LocalTime[] o1, LocalTime[] o2) {
                        return Integer.compare(o1[0].compareTo(o2[0]), 0);
                    }
                });
            }
            return days;
        }

        private static int solution(String data)
        {
            // I am going to create fixed array of size 7 (days of week) [its type is Arraylist]
            // Then every cell has Arraylist for meetings
            // Every meeting is a fixed array of size 2 that consists
            // of starting hour of the meeting
            // and finishing hour of the meeting

            ArrayList<LocalTime[]>[] days = getData(data);

            // now searching the biggest gap between meetings

            int currentStreak = 0;
            int lastDayLeft = 0;
            int record = 0;

            LocalTime lastMeeting = LocalTime.parse("00:00:00");
            LocalTime endOfDay =  LocalTime.parse("23:59:00");

            for(int i = 0; i < 7; i++)
            {
                currentStreak += lastDayLeft;
                lastMeeting = LocalTime.parse("00:00:00");
                endOfDay = LocalTime.parse("23:59:00");

                for (LocalTime[] meeting:days[i])
                // start of the day
                {
                    currentStreak += (int)lastMeeting.until(meeting[0], ChronoUnit.MINUTES);
                    lastMeeting = meeting[1];
                    if(currentStreak > record) record = currentStreak;
                    currentStreak = 0;
                }
                // end of the day

                lastDayLeft = (int)lastMeeting.until(endOfDay, ChronoUnit.MINUTES);
                if(endOfDay.equals(LocalTime.parse("23:59:00"))) lastDayLeft += 1;
                if(currentStreak > record) record  = currentStreak;
                if(lastDayLeft > record) record  = lastDayLeft;
            }
            lastDayLeft = (int)lastMeeting.until(endOfDay, ChronoUnit.MINUTES);
            if(endOfDay.equals(LocalTime.parse("23:59:00"))) lastDayLeft += 1;

            if(currentStreak > record) record  = currentStreak;

            return record;
        }


        public static void main(String[] args)
        {

            String in =
                    "Mon 01:00-23:00\n" +
                            "Tue 01:00-23:00\n" +
                            "Wed 01:00-23:00\n" +
                            "Thu 01:00-23:00\n" +
                            "Fri 01:00-23:00\n" +
                            "Sat 01:00-23:00\n" +
                            "Sun 01:00-21:00";


            Integer out = solution(in);
            System.out.printf(out.toString());
        }

}
