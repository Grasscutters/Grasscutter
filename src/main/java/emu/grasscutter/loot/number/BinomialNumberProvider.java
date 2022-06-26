package emu.grasscutter.loot.number;

public class BinomialNumberProvider implements NumberProvider {

    private NumberProvider n;
    private NumberProvider p;

    public BinomialNumberProvider(NumberProvider n, NumberProvider p) {
        this.n = n;
        this.p = p;
    }

    @Override
    public String getName() {
        return "binomial";
    }

    @Override
    public Number roll() {
        Number n = this.n.roll();
        Number p = this.p.roll();

        double log_q = Math.log(1.0 - p.doubleValue());
        int x = 0;
        double sum = 0;
        for(;;) {
            sum += Math.log(Math.random()) / (n.intValue() - x);
            if(sum < log_q) {
                return x;
            }
            x++;
        }
    }
}
