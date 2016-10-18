public abstract class Atomic<X> extends DiscreteModel<X>
{
    Bag<X> in;
    Bag<X> out;
    public double tL;
	public double priority;
	public boolean active;
	public abstract void deltaInt();
	public abstract void deltaExt(double t, Bag<X> b);
	public abstract void deltaConf(Bag<X> b);
	public abstract void lambda(Bag<X> b);
	public abstract double timeAdv();
	public Atomic<X> isAtomic(){return this;}
	public Network<X> isNetwork(){return null;}
}