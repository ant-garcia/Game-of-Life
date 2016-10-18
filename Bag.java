import java.util.ArrayList;

public class Bag<E>
{
	public ArrayList<E> list = new ArrayList();

    public void addIfNotFound(E e)
    {
        if(!list.contains((e)))
            list.add(e);
        else
            list.set(list.indexOf(e), e);
    }
}