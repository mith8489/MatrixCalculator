package base;

public class ComplexNum {

    private double re;
    private double im;

    public double getRe() {
        return re;
    }
    public double getIm() {
        return im;
    }



    ComplexNum(double re, double im)
    {
        this.re = re;
        this.im = im;
    }

    public static ComplexNum sqrt(double val)
    {
        if (val >= 0) return new ComplexNum(Math.sqrt(val), 0.0);
        else          return new ComplexNum(0.0, Math.sqrt(-val));
    }



    /*--------------------ARITHMETIC FUNCTIONS----------------------------------------*/

    /**
     *
     * @param b
     * @return
     */
    public ComplexNum add(ComplexNum b)
    {
        ComplexNum a = this;

        double newRe = a.getRe() + b.getRe();
        double newIm = a.getIm() + b.getIm();

        return new ComplexNum(newRe, newIm);
    }


    /**
     *
     * @param b
     * @return
     */
    public ComplexNum subtract(ComplexNum b)
    {
        ComplexNum a = this;

        double newRe = a.getRe() - b.getRe();
        double newIm = a.getIm() - b.getIm();

        return new ComplexNum(newRe, newIm);
    }

    /**
     *
     * @param x
     * @return
     */
    public ComplexNum multiply(ComplexNum x)
    {
        double a = this.getRe();
        double b = this.getIm();
        double c = x.getRe();
        double d = x.getIm();

        double newRe = a*c - b*d;
        double newIm = b*c + a*d;

        return new ComplexNum(newRe, newIm);
    }

    /**
     *
     * @param x
     * @return
     */
    public ComplexNum divide(ComplexNum x)
    {
        double a = this.getRe();
        double b = this.getIm();
        double c = x.getRe();
        double d = x.getIm();


        double newRe = (a*c + b*d) / (Math.pow(c, 2) + (Math.pow(d, 2)));
        double newIm = (b*c - a*d) / (Math.pow(c, 2) + (Math.pow(d, 2)));

        return new ComplexNum(newRe, newIm);
    }

    /*--------------------------------------------------------------------------------*/

    /**
     *
     * @return
     */
    public String toString()
    {
        if (im == 0.0 && re == 0.0) return 0 + "";
        if (re == 0.0) return im + "i";
        if (im == 0.0) return re + "";
        else return re + " + " + im + "i";
    }

}
