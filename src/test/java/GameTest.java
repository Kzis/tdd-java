
import org.junit.jupiter.api.*;
import static org.junit.Assert.*;

public class GameTest {

    public class Game {
        int[] rolls = new int[20];
        int rollIndex = 0;

        private void roll(int pinsKnockedDown) {
            rolls[rollIndex++] = pinsKnockedDown;
        }

        public int score() {
            int sum = 0;
            int rollsIndex = 0;

            for (int frame =0; frame<10; frame++){
                if(isStrike(rollsIndex)){
                    sum += bonusForStrike(rollsIndex);
                    rollsIndex+= 1;
                }
                else if(isSapre(rollsIndex)){
                    sum += bonusForSpare(rollsIndex);
                    rollsIndex+= 2;
                } else {
                    sum += rolls[rollsIndex] + rolls[rollsIndex+1];
                    rollsIndex+= 2;
                }
            }
            return sum;
        }

        public void rollMultipleTimes(int pinsKnockedDown,int noOfTimes){
            for (int i=1; i <= noOfTimes; i++){
                this.roll(pinsKnockedDown);
            }
        }

        private boolean isSapre(int rollsIndex){
            return rolls[rollsIndex] + rolls[rollsIndex+1] == 10;
        }

        private  boolean isStrike(int rollsIndex){
            return rolls[rollsIndex] == 10;
        }


        private int bonusForSpare(int rollsIndex){
            return 10 + rolls[rollsIndex+2];
        }

        private int bonusForStrike(int rollsIndex){
            return 10 + rolls[rollsIndex+1] + rolls[rollsIndex+2];
        }


    }

    Game game = new Game();

    @Test
    void rule1(){
        game.rollMultipleTimes(0,20);
        assertEquals(0,game.score());
    }

    @Test
    void rule2(){
        game.rollMultipleTimes(1,20);
        assertEquals(20,game.score());
    }

    @Test
    void spare1(){
        // 5,5 | 1,1 | 1,1 | 1,1 | 1,1 | 1,1 | 1,1 | 1,1 | 1,1 | 1,1
        game.rollMultipleTimes(5,2);
        game.rollMultipleTimes(1,18);
        assertEquals(29,game.score());
    }

    @Test
    void strike1(){
        // 10 | 1,1 | 1,1 | 1,1 | 1,1 | 1,1 | 1,1 | 1,1 | 1,1 | 1,1
        game.roll(10);
        game.rollMultipleTimes(1,18);
        assertEquals(30,game.score());
    }

}
