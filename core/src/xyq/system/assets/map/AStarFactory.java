package xyq.system.assets.map;

/**
 * @modify wpaul 2016-2-19
 */
public class AStarFactory {
	
	public static PathFinder createAstar(byte[] cells,int w,int h,int type){
		PathFinder o = null;
		switch (type){
			//case 1:
			//	return new Astar();
			//	break;
			//case 2:
			//	return new Astar2();
			//	break;
			case 3:
				o =  new AStar3(cells,w,h);
				break;
			//default:
			//	return new Astar();
		}
		return o;
	}
	
	/*public static Searcher createAstar(byte[] cells,int w,int h,int type){
		Searcher o = null;
		switch (type){
			case 3:
				o =  new AStar();
				o.init(w, h, cells);
				break;
		}
		return o;
	}*/

}
