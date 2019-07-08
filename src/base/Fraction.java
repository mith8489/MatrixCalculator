package base;

import java.math.BigInteger;

public class Fraction {
    private final BigInteger numerator;
    private final BigInteger denominator;

    private BigInteger getNumerator() {
        return this.numerator;
    }

    private BigInteger getDenominator() {
        return this.denominator;
    }

    public static Fraction fromString(String fractionString) {
        if (fractionString.contains("/")) {
            String[] splitString = fractionString.split("/");
            return new Fraction(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]));
        } else {
            return new Fraction(Integer.parseInt(fractionString));
        }
    }

    private Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Division by 0 not defined!");
        } else {
            if (numerator == 0) {
                denominator = 1;
            } else if (denominator < 0) {
                numerator = -numerator;
                denominator = -denominator;
            }

            BigInteger gcd = this.euclideanGCD(BigInteger.valueOf((long)numerator), BigInteger.valueOf((long)denominator));
            this.numerator = BigInteger.valueOf((long)numerator).divide(gcd);
            this.denominator = BigInteger.valueOf((long)denominator).divide(gcd);
        }
    }

    private Fraction(BigInteger numerator, BigInteger denominator) {
        if (denominator.intValue() == 0) {
            throw new IllegalArgumentException("Division by 0 not defined!");
        } else {
            if (numerator.equals(BigInteger.valueOf(0L))) {
                numerator = BigInteger.valueOf(0L);
                denominator = BigInteger.valueOf(1L);
            } else if (denominator.compareTo(BigInteger.valueOf(0L)) < 0) {
                numerator = numerator.negate();
                denominator = denominator.negate();
            }

            BigInteger gcd = this.euclideanGCD(numerator, denominator);
            this.numerator = numerator.divide(gcd);
            this.denominator = denominator.divide(gcd);
        }
    }

    public Fraction(int numerator) {
        this.numerator = BigInteger.valueOf((long)numerator);
        this.denominator = BigInteger.valueOf(1L);
    }

    private BigInteger euclideanGCD(BigInteger numerator, BigInteger denominator) {
        if (numerator.equals(BigInteger.valueOf(0L))) {
            return BigInteger.valueOf(1L);
        } else {
            BigInteger a = numerator.abs();
            BigInteger b = denominator.abs();

            while(!a.equals(b)) {
                int largestVal = a.compareTo(b);
                switch(largestVal) {
                    case -1:
                        b = b.subtract(a);
                        break;
                    case 1:
                        a = a.subtract(b);
                }
            }

            return a;
        }
    }

    public Fraction add(Fraction b) {
        BigInteger newNumerator = this.getNumerator().multiply(b.getDenominator()).add(b.getNumerator().multiply(this.getDenominator()));
        BigInteger newDenominator = this.getDenominator().multiply(b.getDenominator());
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction subtract(Fraction b) {
        BigInteger newNumerator = this.getNumerator().multiply(b.getDenominator()).subtract(b.getNumerator().multiply(this.getDenominator()));
        BigInteger newDenominator = this.getDenominator().multiply(b.getDenominator());
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction multiply(Fraction b) {
        BigInteger newNumerator = this.getNumerator().multiply(b.getNumerator());
        BigInteger newDenominator = this.getDenominator().multiply(b.getDenominator());
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction divide(Fraction b) {
        Fraction reverseFraction = new Fraction(b.getDenominator(), b.getNumerator());
        return this.multiply(reverseFraction);
    }

    public Fraction square() {
        return this.multiply(this);
    }

    public Fraction negate() {
        return new Fraction(this.numerator.negate(), this.denominator);
    }

    public boolean equalsOne() {
        return this.numerator.divide(this.denominator).equals(BigInteger.valueOf(1L));
    }

    public boolean equalsZero() {
        return this.numerator.equals(BigInteger.valueOf(0L));
    }

    public String toString() {
        return this.denominator.intValue() == 1 ? this.numerator + "" : this.numerator + "/" + this.denominator;
    }
}
