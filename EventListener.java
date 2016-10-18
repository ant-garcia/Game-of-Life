public abstract class EventListener<V>
{
	public abstract void lambdaEvent(Event<V> e, double t);
	public abstract void stateChange(Atomic<V> model, double t);
}