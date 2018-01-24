package maths;

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

    private BigInteger euclideanGCD(BigInteger numerator, BigInteger denominator)
    {
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

    public static Fraction add(Fraction a, Fraction b)
    {
        BigInteger newNumerator = a.getNumerator().multiply(b.getDenominator()).add(b.getNumerator().multiply(a.getDenominator()));
        BigInteger newDenominator = a.getDenominator().multiply(b.getDenominator());

        return new Fraction(newNumerator, newDenominator);
    }

    public static Fraction subtract(Fraction a, Fraction b)
    {
        BigInteger newNumerator = a.getNumerator().multiply(b.getDenominator()).subtract(b.getNumerator().multiply(a.getDenominator()));
        BigInteger newDenominator = a.getDenominator().multiply(b.getDenominator());

        return new Fraction(newNumerator, newDenominator);
    }

    public static Fraction multiply(Fraction a, Fraction b)
    {
        BigInteger newNumerator = a.getNumerator().multiply(b.getNumerator());
        BigInteger newDenominator = a.getDenominator().multiply(b.getDenominator());

        return new Fraction(newNumerator, newDenominator);
    }

    public static Fraction divide(Fraction a, Fraction b)
    {
        BigInteger newNumerator = a.getNumerator().divide(b.getNumerator());
        BigInteger newDenominator = a.getDenominator().divide(b.getDenominator());

        return new Fraction(newNumerator, newDenominator);
    }

    @Override
    public String toString()
    {
        if (denominator.intValue() == 1) return numerator+"";
        else return numerator + "/" + denominator;
    }
}
