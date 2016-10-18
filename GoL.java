public class GoL{
	public static void main(String[] args){
		int upscale = 20;
		CellNetwork cn = new CellNetwork(50, 50, 0.3);
		Simulator s = new Simulator(cn);
		CellListener cl = new CellListener(cn);
		GoLUI.initGui(cn.getWidth() * upscale, cn.getHeight() * upscale, cl.getState(), upscale);
		s.addEventListener(cl); 
		System.out.println("START");
		while(s.nextEventTime() <= 100.0){
			s.executeNextEvent();
			GoLUI.update();
			try{
				Thread.sleep(20);
			}catch(InterruptedException ie){}
		}
		System.out.println("END");
		System.exit(0);
	}
}