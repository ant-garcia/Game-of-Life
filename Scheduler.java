import java.util.Iterator;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Scheduler<E>{
	PriorityQueue<E> pq;
	
	public Scheduler(int cap, Comparator<E> c){
		pq = new PriorityQueue(cap, c);
	}

	public double getPriority(){
		return ((Atomic<E>)pq.peek()).priority;
	}

	public void update(E e){
		pq.add(e);
	}

	public void remove(E e){
		if(pq.contains(e))
			pq.remove(e);
	}

    public void getMinParts(Bag<E> b){
        Iterator<E> it = pq.iterator();
        while(it.hasNext()){
            E tmp = it.next();
            if(getPriority() < ((Atomic<E>)tmp).priority)
                return;
            ((Atomic<E>)tmp).active = true;
            b.addIfNotFound(tmp);
        }
    }
}