import java.util.ArrayList;
import java.util.Comparator;

public class Simulator<T>{
    double tP;
    Scheduler<Atomic<T>> mScheduler;
	Bag<Event<T>> empty;
	Bag<Atomic<T>> b, act;
	Bag<EventListener<T>> bel;
	public Comparator<Atomic<T>> cmp = new Comparator<Atomic<T>>(){
        @Override
        public int compare(Atomic<T> a, Atomic<T> b){
           return Double.compare(a.priority, b.priority);
        }
    };


	public Simulator(DiscreteModel<T> dm){
		tP = 0.0;
		b = new Bag();
		act = new Bag();
		bel = new Bag();
		empty = new Bag();
		mScheduler = new Scheduler(10, cmp);
		if(dm.isAtomic() != null)
			schedule(dm.isAtomic(), 0.0);
		else{
			ArrayList<Atomic<T>> list = new ArrayList();
			getAllChildren(dm.isNetwork(), list);
			for(Atomic<T> a : list)
				schedule(a, 0.0);
		}
	}

	private void schedule(Atomic<T> m, double t){
		m.tL = t;
		double tA = m.timeAdv();
		mScheduler.remove(m);
		if(tA == Double.POSITIVE_INFINITY){	
			m.priority = tA;
			mScheduler.update(m);
		}
		else{
			m.priority = t + tA;
			mScheduler.update(m);
		}
	}

	public void executeNextEvent(){
		executeNextOutput();
		executeNextState(mScheduler.getPriority(), empty);
	}

	public void executeNextState(double t, Bag<Event<T>> in){
		if(t < mScheduler.getPriority() && !b.list.isEmpty())
			return;
		else if(t >= mScheduler.getPriority() && b.list.isEmpty())
			executeNextOutput();

		for(Event<T> e : in.list){
			Atomic<T> am = e.model.isAtomic();
			if(am != null)
				addEvent(am, e.val);
			else
				route(e.model.isNetwork(), e.model, e.val);
		}
		for(Atomic<T> a : b.list){
			if(a.in == null)
				a.deltaInt();
			else
				a.deltaConf(a.in);

			notifyStateListeners(a, t);
			schedule(a, t);
		}
        for(Atomic<T> a : act.list){
            a.deltaExt(t-a.tL, a.in);
            notifyStateListeners(a, t);
            schedule(a, t);
        }
		for(Atomic<T> a : b.list)
			destroyBag(a);
		for(Atomic<T> a : act.list)
			destroyBag(a);

		b.list.clear();
		act.list.clear();
	}

	public void executeNextOutput(){
		if(!b.list.isEmpty())
			return;

		mScheduler.getMinParts(b);

		for(Atomic<T> m : b.list)
            if (m.out == null){
            	m.out = new Bag<T>();
                m.lambda(m.out);
                for(T t : m.out.list)
                	route(m.getParent(), m, t);
            }
	}

	

	private void getAllChildren(Network<T> m, ArrayList<Atomic<T>> l){
		ArrayList<DiscreteModel<T>> al = new ArrayList();
		m.getComponents(al);
		for(DiscreteModel<T> dm : al){
			if(dm.isNetwork() != null)
				getAllChildren(dm.isNetwork(), l);
			else
				l.add(dm.isAtomic());
		}
	}


	private void route(Network<T> parent, DiscreteModel<T> src, T val){
		if(parent != src)
			notifyOutputListeners(src, val, mScheduler.getPriority());
		if(parent == null)
			return;

		Bag<Event<T>> tb = new Bag();
		parent.route(val, src, tb);

		Atomic<T> tm = null;
		for(Event<T> e : tb.list){
			tm = e.model.isAtomic();
			if(tm != null)
				addEvent(tm, e.val);
			else if(e.model == parent)
				route(parent.getParent(), parent, e.val);
			else
				route(e.model.isNetwork(),e.model, e.val);
		}

		tb.list.clear();
	}

	private void addEvent(Atomic<T> m, T val){
		if(!m.active)
			act.list.add(m);
        if(m.in == null)
            m.in = new Bag<T>();
		m.active = true;
		m.in.list.add(val);
	}

	private void notifyOutputListeners(DiscreteModel<T> dm, T val, double t){
		Event<T> e = new Event(dm, val);
		for(EventListener el : bel.list)
			el.lambdaEvent(e, t);
	}

	private void notifyStateListeners(Atomic<T> m, double t){
		for(EventListener el : bel.list)
			el.stateChange(m, t);	
	}

	private void destroyBag(Atomic<T> m){
		m.active = false;
		if(m.in != null)
			m.in = null;
		if(m.out != null)
			m.out = null;
	}

    void addEventListener(EventListener listener){
    	bel.list.add(listener);
    }

	public double nextEventTime(){
		return mScheduler.getPriority();
	}
}