import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
class ScoreTrack{
    private String round;
    private int score;

    ArrayList<Object> mixedList = new ArrayList<>();

    public ScoreTrack()
    {
        round = "0";
        score=0;

    }

    public void setAll(String r , int sc)
    {
this.round = r;
this.score = sc;
        mixedList.add(r);
        mixedList.add(sc);


    }

    public void show()
    {
        for (Object element : mixedList) {
            if (element instanceof String) {
                String str = (String) element;
                System.out.print("Round: " + str+"\t\t");
            } else if (element instanceof Integer) {
                int intValue = (Integer) element;
                System.out.println("Score: " + intValue);
            }
        }
    }



}
class Main
{


    //    >>>>BASIC JAVA SYNTAX




    public static void main(String args[])
    {
        System.out.println("dsa");
ScoreTrack str = new ScoreTrack();

        var arrayList = new ArrayList<String>();

        int a,limit=1,maxLim=3,fnd=0,round=1;
        boolean nxtRun=true;
                        //	     >>LOOP
        while(nxtRun){
            Random r = new Random();
            int n = r.nextInt(10)+1;

            //System.out.println(n);
            System.out.println("\t\t\t\tWELCOME TO NUMBER GENERATOR GAME(-_-)\t\t\t\t(^-^)ROUND"+round);
            System.out.println(" ( ^__^ ) PREVIOUS ROUND SCORES ");
            str.show();


            System.out.println("\nEnter your Guess for number between 1 and 10.You have 3 limits");
            Scanner s = new Scanner(System.in);
            a = s.nextInt();

            //          >>>>SELECTION STATEMENTS
            if(a==n)
            {  System.out.println("\n\t\t\nCongratulations, now your guess is equal to actual number. You have guessed in first attempt.\n\t\t\t\tYOUR SCORE IS "+maxLim+"\n");
            }
            else{
                while(a!=n && limit!=3)
                {

                    //	     >>INPUT,LOGIC

                    if(a>n)
                        System.out.println("Your guess is larger than actual number.Please retry with Smaller one.You are left with "+(--maxLim)+" Limits \n");
                    else if(a<n)
                        System.out.println("Your guess is smaller than actual number.Please retry with Larger one.You are left with "+(--maxLim)+" Limits \n");

                    System.out.println("Enter your Guess again\n");
                    Scanner st = new Scanner(System.in);
                    a = st.nextInt();
                    limit++;
                    if(a==n)
                    {fnd = 1;break;}

                }
                if(fnd == 1)
                {
                    str.setAll(Integer.toString(round),maxLim);
                    System.out.println("You have won the game.\n\t\t\t\tYOUR SCORE IS "+maxLim+"\n");
                }
                else
                {
                    str.setAll(Integer.toString(round),0);
                    System.out.println("You have lost the game.\n\t\t\t\tYOUR SCORE IS 0 \n");
                }
            }

            //             >>DISPLAYING UI

            System.out.println("Do you wanna play game again. Press 1 to PLAY\t\t0 to STOP\n");
            Scanner stn = new Scanner(System.in);
            int abn = stn.nextInt();
            if(abn==0)
                nxtRun=false;
            else
            {round++;
                limit=1;maxLim=3;fnd=0;}

        }
        if(nxtRun == false)
            System.out.println("\t\t\t\tWe will meet(`_`) again Insha Allah");
    }


}

