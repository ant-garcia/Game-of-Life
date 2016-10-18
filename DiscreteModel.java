public abstract class DiscreteModel<X>
{
	Network<X> parent;

	public abstract Network<X> isNetwork();
	public abstract Atomic<X> isAtomic();
	public Network<X> getParent(){return this.parent;}
	public void setParent(Network<X> parent){this.parent = parent;}
}