package matrixops.maths;

import java.math.BigInteger;

/**
 * Fraction.java: Fraction structure using BigIntegers for denominators and numerators.
 * Automatically reduces to lowest possible denominator. Can perform basic arithmetic functions.
 */
public class Fraction {

    private final BigInteger numerator;
    private final BigInteger denominator;

    private BigInteger getNumerator() {
        return numerator;
    }

    private BigInteger getDenominator() {
        return denominator;
    }

    /*--------------------CONSTRUCTORS----------------------------------------*/

    /**
     * Constructs a Fraction from a String object. String must be composed of numbers and
     * (optionally) a forward slash.
     * @param fractionString String from which to construct Fraction.
     * @return New Fraction constructed from a String.
     */
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

    /**
     * Creates a new Fraction from two int values (converting them to BigIntegers). Reduces to lowest possible denominator.
     *
     * @param numerator Numerator of the new Fraction.
     * @param denominator Denominator of the new Fraction. Cannot be zero.
     * @throws IllegalArgumentException if denominator is zero.
     */
    private Fraction (int numerator, int denominator)
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

    /**
     * Creates a new Fraction from two BigInteger values. Reduces to lowest possible denominator.
     *
     * @param numerator Numerator of the new Fraction.
     * @param denominator Denominator of the new Fraction. Cannot be zero.
     * @throws IllegalArgumentException if denominator is zero.
     */
    private Fraction (BigInteger numerator, BigInteger denominator)
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

    /**
     * Creates a new Fraction from one int value as the numerator. Denominator is always 1 in this case.
     * @param numerator Numerator of the new Fraction.
     */
    Fraction (int numerator)
    {
        this.numerator = BigInteger.valueOf(numerator);
        this.denominator = BigInteger.valueOf(1);
    }

    /*-----------------------------------------------------------------------*/

    /**
     * Finds the greatest common denominator of the numerator and denominator using the Euclidean algorithm.
     * GCD is automatically set to 1 if numerator is 0.
     * @param numerator Numerator.
     * @param denominator Cannot be 0.
     * @return GCD of numerator and denominator.
     */
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

    /*--------------------ARITHMETIC FUNCTIONS----------------------------------------*/

    /**
     * Adds this Fraction to another.
     * @param b Second term in the addition.
     * @return Sum of this and b.
     */
    public Fraction add(Fraction b)
    {
        Fraction a = this;
        BigInteger newNumerator = a.getNumerator().multiply(b.getDenominator()).add(b.getNumerator().multiply(a.getDenominator()));
        BigInteger newDenominator = a.getDenominator().multiply(b.getDenominator());

        return new Fraction(newNumerator, newDenominator);
    }
    /**
     * Subtracts another Fraction from this.
     * @param b The subtrahend of the subtraction.
     * @return Difference between this and b.
     */
    public Fraction subtract(Fraction b)
    {
        Fraction a = this;
        BigInteger newNumerator = a.getNumerator().multiply(b.getDenominator()).subtract(b.getNumerator().multiply(a.getDenominator()));
        BigInteger newDenominator = a.getDenominator().multiply(b.getDenominator());

        return new Fraction(newNumerator, newDenominator);
    }

    /**
     * Multiplies this Fraction with another.
     * @param b Second factor in the multiplication.
     * @return Product of this and b.
     */
    public Fraction multiply(Fraction b)
    {
        Fraction a = this;
        BigInteger newNumerator = a.getNumerator().multiply(b.getNumerator());
        BigInteger newDenominator = a.getDenominator().multiply(b.getDenominator());
        return new Fraction(newNumerator, newDenominator);
    }

    /**
     * Divides this Fraction by another through multiplication with inverted Fraction.
     * @param b Divisor in the division.
     * @return Quotient of this and b.
     */
    public Fraction divide(Fraction b)
    {
        Fraction a = this;
        Fraction reverseFraction = new Fraction(b.getDenominator(), b.getNumerator());
        return a.multiply(reverseFraction);
    }

    /**
     * Gets the square of this Fraction by multiplying it by itself.
     * @return Square of this.
     */
    public Fraction square()
    {
        return this.multiply(this);
    }

    /**
     * Gets the negative of this Fraction by changing the sign of the numerator.
     * @return This Fraction, with its sign changed.
     */
    public Fraction negate()
    {
        return new Fraction(numerator.negate(), denominator);
    }

    /**
     * Checks if this Fraction equals 1.
     * @return True if this = 1, otherwise false.
     */
    public boolean equalsOne()
    {
        return (numerator.divide(denominator).equals(BigInteger.valueOf(1)));
    }

    /**
     * Checks if this Fraction equals 0.
     * @return True if this = 0, otherwise false.
     */
    public boolean equalsZero()
    {
        return numerator.equals(BigInteger.valueOf(0));
    }

    /**
     * Produces as String representation of this Fraction.
     * @return String form of numerator if denominator is 1, otherwise numerator / denominator.
     */
    @Override
    public String toString()
    {
        if (denominator.intValue() == 1) return numerator+"";
        else return numerator + "/" + denominator;
    }
}
