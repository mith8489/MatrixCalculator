package matrixops.maths;

import java.math.BigInteger;

public class Fraction {

    private final BigInteger numerator;
    private final BigInteger denominator;

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    /**--------------------CONSTRUCTORS----------------------------------------*/

    public static Fraction fromString (String fractionString)
    {
        if (fractionString.contains("/"))
        {
            String[] splitString = fractionString.split("/");
            return new Fraction(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]));
        }
        else
        {
            return new Fraction((Integer.parseInt(fractionString)));
        }

    }

    public Fraction (int numerator, int denominator)
    {
        if (denominator == 0) throw new IllegalArgumentException("Division by 0 not defined!");

        if (numerator == 0)
        {
            numerator = 0;
            denominator = 1;
        }
        else if (denominator < 0)
        {
            numerator = -numerator;
            denominator = -denominator;
        }

        BigInteger gcd = euclideanGCD(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
        this.numerator = BigInteger.valueOf(numerator).divide(gcd);
        this.denominator = BigInteger.valueOf(denominator).divide(gcd);
    }

    public Fraction (BigInteger numerator, BigInteger denominator)
    {
        if (denominator.intValue() == 0) throw new IllegalArgumentException("Division by 0 not defined!");

        if (numerator.equals(BigInteger.valueOf(0)))
        {
            numerator = BigInteger.valueOf(0);
            denominator = BigInteger.valueOf(1);
        }

        else if (denominator.compareTo(BigInteger.valueOf(0)) < 0)
        {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
            BigInteger gcd = euclideanGCD(numerator, denominator);
            this.numerator = numerator.divide(gcd);
            this.denominator = denominator.divide(gcd);
    }

    public Fraction (int numerator)
    {
        this.numerator = BigInteger.valueOf(numerator);
        this.denominator = BigInteger.valueOf(1);
    }

    private BigInteger euclideanGCD(BigInteger numerator, BigInteger denominator)
    {
        if (numerator.equals(BigInteger.valueOf(0))) return BigInteger.valueOf(1);
        BigInteger a = numerator.abs();
        BigInteger b = denominator.abs();

        while (!a.equals(b))
        {
            int largestVal = a.compareTo(b);
            switch (largestVal)
            {
                case -1: b = b.subtract(a);
                break;

                case 1: a = a.subtract(b);
                break;
            }
        }

        return a;
    }

    /**--------------------ARITHMETIC FUNCTIONS----------------------------------------*/

    public Fraction add(Fraction b)
    {
        Fraction a = this;
        BigInteger newNumerator = a.getNumerator().multiply(b.getDenominator()).add(b.getNumerator().multiply(a.getDenominator()));
        BigInteger newDenominator = a.getDenominator().multiply(b.getDenominator());

        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction subtract(Fraction b)
    {
        Fraction a = this;
        BigInteger newNumerator = a.getNumerator().multiply(b.getDenominator()).subtract(b.getNumerator().multiply(a.getDenominator()));
        BigInteger newDenominator = a.getDenominator().multiply(b.getDenominator());

        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction multiply(Fraction b)
    {
        Fraction a = this;
        BigInteger newNumerator = a.getNumerator().multiply(b.getNumerator());
        BigInteger newDenominator = a.getDenominator().multiply(b.getDenominator());
        Fraction newFraction = new Fraction(newNumerator, newDenominator);
        return newFraction;
    }

    public Fraction divide(Fraction b)
    {
        Fraction a = this;
        Fraction reverseFraction = new Fraction(b.getDenominator(), b.getNumerator());
        Fraction newFraction = a.multiply(reverseFraction);
        return newFraction;
    }

    public Fraction square()
    {
        return this.multiply(this);
    }

    public Fraction negate()
    {
        return new Fraction(numerator.negate(), denominator);
    }

    public boolean equalsOne()
    {
        return (numerator.divide(denominator).equals(BigInteger.valueOf(1)));
    }

    public boolean equalsZero()
    {
        return numerator.equals(BigInteger.valueOf(0));
    }

    @Override
    public String toString()
    {
        if (denominator.intValue() == 1) return numerator+"";
        else return numerator + "/" + denominator;
    }
}
