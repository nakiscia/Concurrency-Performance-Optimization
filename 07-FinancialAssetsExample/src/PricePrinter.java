public class PricePrinter  extends Thread{
    PriceContainer container;

    public PricePrinter(PriceContainer priceContainer) {
        this.container = priceContainer;
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        while(true){
            try{
                container.getLockObject().lock();
                    stringBuilder.append("BTC:"+container.getBitcoinPrice()+"\n");
                    stringBuilder.append("ETH:"+container.getEtherPrice()+"\n");
                    stringBuilder.append("BTC:"+container.getRipplePrice()+"\n");
                    stringBuilder.append("BTC:"+container.getLitecoinPrice()+"\n");
                    stringBuilder.append("---------------------------------------");

                    System.out.println(stringBuilder);

            }finally {
                container.getLockObject().unlock();
            }
        }


    }
}
