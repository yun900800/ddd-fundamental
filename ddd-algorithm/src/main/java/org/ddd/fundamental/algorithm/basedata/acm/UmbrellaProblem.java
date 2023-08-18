package org.ddd.fundamental.algorithm.basedata.acm;

public class UmbrellaProblem {

    public static void main(String[] args) {
        UmbrellaProblem umbrellaProblem = new UmbrellaProblem();
        int ret = umbrellaProblem.umbrellaProblem(11,2,1);
        System.out.println(ret);
    }

    public int umbrellaProblem(int personCount,
                               int smallUmbrella, int bigUmbrella) {
        if (personCount<=2) {
           return 1;
        }
        int initPerson = 2;
        smallUmbrella = smallUmbrella+1;
        int step = 1;
        while (initPerson * 2 <= (smallUmbrella+bigUmbrella)) {
            int tempUmbrella = initPerson*2;
            UmbrellaPosition umbrellaPosition = null;
            if (bigUmbrella >= tempUmbrella) {
                umbrellaPosition = new UmbrellaPosition();
                umbrellaPosition.bigUmbrella = tempUmbrella;
                umbrellaPosition.smallUmbrella = 0;
            } else if (bigUmbrella < tempUmbrella) {
                umbrellaPosition = new UmbrellaPosition();
                umbrellaPosition.bigUmbrella = bigUmbrella;
                int leftPerson = tempUmbrella - bigUmbrella;
                if (leftPerson <= smallUmbrella) {//说明雨伞足够
                    umbrellaPosition.smallUmbrella = leftPerson;
                } else {
                    umbrellaPosition.smallUmbrella = smallUmbrella;
                }
            }
            //计算带的雨伞能够接多少人
            initPerson = calculatePersonByUmbrella(umbrellaPosition);
            step+=2;
            if (initPerson >= personCount) {
                return step;
            }
        }
        //计算每次能带多少人
        UmbrellaPosition umbrellaPosition = new UmbrellaPosition();
        umbrellaPosition.bigUmbrella = bigUmbrella;
        umbrellaPosition.smallUmbrella = smallUmbrella;
        int carryPerson = calculatePersonByUmbrella(umbrellaPosition) - (smallUmbrella+ bigUmbrella)/2;
        int count = personCount/carryPerson;
        step+=count*2;
        return step;
    }

    public int calculatePersonByUmbrella(UmbrellaPosition umbrellaPosition) {
        return umbrellaPosition.bigUmbrella * 3 + umbrellaPosition.smallUmbrella *2;
    }
}

class UmbrellaPosition {
    public int bigUmbrella = 0;
    public int smallUmbrella = 0;
}
