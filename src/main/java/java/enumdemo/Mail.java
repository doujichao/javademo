package java.enumdemo;



import java.util.Iterator;
import java.util.Random;


public class Mail {
    enum GeneralDelivery {YES,NO1,NO2,NO3,NO4,NO5}
    enum Scannability{UNSCANNABLE,YES1,YES2,YES3,YES4,YES5}
    enum Readability{ILLEGIBLE,YES1,YES2,YES3,YES4,YES5}
    enum Address{IMCPRRECT,OK1,OK2,OK3,OK4,OK5}
    enum ReturnAddress{MISSING,OK1,OK3,OK2,OK4,OK5}
    GeneralDelivery generalDelivery;
    Scannability scannability;
    Readability readability;
    Address address;
    ReturnAddress returnAddress;

    static long counter=0;
    long id=counter++;

    @Override
    public String toString() {
        return "Mail "+id;
    }

    public String details(){
        return toString() +
                ", General Delivery : "+generalDelivery+
                ", Address Scalability : "+ scannability +
                ", Address Readability : "+ readability +
                ", Address Address : "+ address +
                "Return address:"+returnAddress
                ;
    }


   static class PostOffice{
        enum MailHalder {
            GENERAL_DELIVERY{
                boolean handle(Mail m){
                    switch (m.generalDelivery){
                        case YES:
                            print("Using general delivery for" + m);
                            return true;
                        default: return  false;
                    }
                }
            },
            MACHINE_SCAN {
                boolean handle(Mail m){
                    switch (m.scannability){
                        case UNSCANNABLE: return false;
                        default:
                            switch (m.address){
                                case IMCPRRECT: return false;
                                default:
                                    print("Delivering "+m + " automatically");
                                    return true;
                            }
                    }
                }
            },VISUAL_INSPECTION{
                boolean handle(Mail m){
                    switch (m.readability){
                        case ILLEGIBLE: return false;
                        default:
                            switch (m.address){
                                case IMCPRRECT: return false;
                                default:
                                    print("Delivering "+m + " normally");
                                    return true;
                            }
                    }
                }
            },RETURN_TO_SENDER{
                boolean handle(Mail m){
                    switch (m.returnAddress){
                        case MISSING: return false;
                        default:
                            print("Returning "+ m + " to sender");
                            return true;
                    }
                }
            };

            abstract  boolean handle (Mail m);
        }

    }

    public static void print(String msg){
        System.out.println(msg);
    }

    public static void main(String[] args){
        for (Mail mail: Mail.generator(10)){
            print(mail.details());
            handle(mail);
            print("*****");
        }
    }

    private static Iterable<Mail> generator(int i) {
        return new Iterable<Mail>() {
            int n=i;
            @Override
            public Iterator<Mail> iterator() {
                return new Iterator<Mail>() {
                    @Override
                    public boolean hasNext() {
                        return n-- > 0;
                    }

                    @Override
                    public Mail next() {
                        return randomMail();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    static class Enums{

        public static<T> T random(Class<T> tClass) {
            if (tClass.isEnum()){
                T[] enumConstants = tClass.getEnumConstants();
                Random random=new Random();
                return enumConstants[random.nextInt(enumConstants.length)];
            }else {
                return null;
            }
        }
    }

    public static Mail randomMail(){
        Mail m=new Mail();
        m.generalDelivery= Enums.random(GeneralDelivery.class);
        m.scannability=Enums.random(Scannability.class);
        m.readability=Enums.random(Readability.class);
        m.address=Enums.random(Address.class);
        m.returnAddress=Enums.random(ReturnAddress.class);
        return m;
    }

    static void handle(Mail m){
        for (PostOffice.MailHalder halder: PostOffice.MailHalder.values()){
            if (halder.handle(m)){
                return;
            }
            print(m+" is a dead letter");
        }
    }
}

