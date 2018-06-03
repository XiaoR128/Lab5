package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import Strategy.BufferedoutputStream;
import Strategy.FileoutputStream;
import Strategy.Filewriter;
import Strategy.InmethodStrategy;
import Strategy.InputStream;
import Strategy.OutmethodStrategy;
import edge.Edge;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.SameMovieHyperEdge;
import factory.ActorVertexFactory;
import factory.DirectorVertexFactory;
import factory.MovieActorRelationFactory;
import factory.MovieDirectorRelationFactory;
import factory.MovieGraphFactory;
import factory.MovieVertexFactory;
import factory.MyLogHander;
import factory.SameMovieHyperEdgeFactory;
import factory.SocialNetworkFactory;
import graph.Graph;
import graph.MovieGraph;
import helper.Centrality;
import helper.GraphMetrics;
import helper.betweennessStrategy;
import helper.closenessStrategy;
import helper.degreeStrategy;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

public class MovieGraphApp {
	private Graph<Vertex, Edge> graph = new MovieGraph();
	private static String name = SocialNetworkFactory.class.getName();      
	private static Logger log = Logger.getLogger(name);
	/**
	 * 创建一张图
	 */
	public Graph<Vertex, Edge> creatgraph(String file,InmethodStrategy in){
		this.graph = new MovieGraphFactory().createGraph(file,in);
		return graph;
	}
	
	/**
	 * 根据规则添加相应的点
	 */
	public boolean addvert(Graph<Vertex, Edge>graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger4.txt");
	        fileHandler.setLevel(Level.INFO); 
	        fileHandler.setFormatter(new MyLogHander());
	        log.addHandler(fileHandler); 
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		System.out.println("请输入点的相关信息，以空格分开:如(FrankDarabont Director 59 M)");
		System.out.println("添加电影的话，按照以下样例：(Spiderman Movie 2010 USA 9.3)");
		Scanner sc = new Scanner(System.in);
		String a = sc.nextLine();
		String[] b = a.split("\\s+");
		String[] p = new String[3];
		for(int i=2,j=0;i<b.length;i++,j++) {
			p[j] = b[i];
		}
//		p[0] = b[2];
//		p[1] = b[3];
//		p[2] = b[4];
		Vertex ve=null;
		if(b[1].equals("Movie")) {
			ve = new  MovieVertexFactory().createVertex(b[0], b[1], p);
		}else if(b[1].equals("Actor")) {
			ve = new  ActorVertexFactory().createVertex(b[0], b[1], p);
		}else if(b[1].equals("Director")) {
			ve = new  DirectorVertexFactory().createVertex(b[0], b[1], p);
		}
		graph.addVertex(ve);
//		log.info("创建点"+b[0]);
		System.out.println("创建点"+b[0]+"成功！");
		return true;
	}
	
	/**
	 * 根据该规则删除相应的点
	 * @param graph
	 * @return
	 */
	public boolean removevert(Graph<Vertex, Edge> graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger4.txt");
	        fileHandler.setLevel(Level.INFO); 
	        fileHandler.setFormatter(new MyLogHander());
	        log.addHandler(fileHandler); 
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		System.out.println("请输入点的名字:例如(Vera)");
		Scanner sc1 = new Scanner(System.in);
		String a1 = sc1.nextLine();
		String[] b1 = a1.split("\\s+");
		for(Vertex v:graph.vertices()) {
			if(v.getLabel().equals(b1[0])) {
				graph.removeVertex(v);
			}
		}
//		log.info("删除点"+b1[0]);
		System.out.println("删除点成功！");
		return true;
	}
	
	/*
	 * 添加一条边，注意添加的时候点是原来存在的
	 * @param 一张图
	 */
	public boolean addedge(Graph<Vertex, Edge> graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger4.txt");
	        fileHandler.setLevel(Level.INFO); 
	        fileHandler.setFormatter(new MyLogHander());
	        log.addHandler(fileHandler); 
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		System.out.println("请输入边的信息，以空格分开");
		System.out.println("例如：label1 MovieDirectorRelation 1 name1 name2 No");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		String[] b1 = a1.split("\\s+");
		List<Vertex> vertice = new ArrayList<Vertex>();
		for(Vertex v:graph.vertices()) {
			if(v.getLabel().equals(b1[3])) {
				vertice.add(v);
			}
		}
		for(Vertex v:graph.vertices()) {
			if(v.getLabel().equals(b1[4])) {
				vertice.add(v);
			}
		}
		Edge ed=null;
		if(b1[1].equals("MovieDirectorRelation")) {
		  ed = new MovieDirectorRelationFactory().createEdge(b1[0], b1[1], Double.parseDouble(b1[2]), vertice);
		}else if(b1[1].equals("MovieActorRelation")) {
			ed = new MovieActorRelationFactory().createEdge(b1[0], b1[1], Double.parseDouble(b1[2]), vertice);
		}
		graph.addEdge(ed);
//		log.info("添加边"+b1[0]);
		System.out.println("添加成功！");
		return true;
	}
	
	
	public void addsame(Graph<Vertex, Edge> graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger4.txt");
	        fileHandler.setLevel(Level.INFO); 
	        fileHandler.setFormatter(new MyLogHander());
	        log.addHandler(fileHandler); 
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		System.out.println("请输入边的信息，以空格分开");
		System.out.println("例如：label SameMovieHyperEdge -1 TimRobbins MorganFreeman");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		String[] b1 = a1.split("\\s+");
		List<Vertex> vertice = new ArrayList<Vertex>();
		for(Vertex v:graph.vertices()) {
			for(int i=2;i<b1.length;i++) {
				if(v.getLabel().equals(b1[i])) {
					vertice.add(v);
				}
			}
		}
		Edge ed = new SameMovieHyperEdgeFactory().createEdge(b1[0], b1[1], Double.parseDouble(b1[2]), vertice);
//		log.info("添加边"+b1[0]);
		graph.addEdge(ed);
	}
	
	/**
	 * 删除图中的某一条边
	 * @param graph
	 * @return
	 */
	public boolean removeedge(Graph<Vertex, Edge> graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger4.txt");
	        fileHandler.setLevel(Level.INFO); 
	        fileHandler.setFormatter(new MyLogHander());
	        log.addHandler(fileHandler); 
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		System.out.println("请输入边的名字：例如(W1)");
		Scanner sc = new Scanner(System.in);
		String a = sc.nextLine();
		int flag = 1;
		for(Edge ed:graph.edges()) {
			if(ed.getlabel().equals(a)) {
				flag=0;
				graph.removeEdge(ed);
//				log.info("删除边"+a);
				System.out.println("删除成功！");
			}
		}
		if(flag==1) {
			System.out.println("删除失败！");
			return false;
		}
		return true;
	}
	
	/**
	 * 改变边的名字
	 * @param graph
	 */
	public void changeedge(Graph<Vertex, Edge> graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger4.txt");
	        fileHandler.setLevel(Level.INFO); 
	        fileHandler.setFormatter(new MyLogHander());
	        log.addHandler(fileHandler); 
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		System.out.println("请输入边的名称和新的名称,旧的在前面，新的在后边，用空格隔开:如(W1 W2)");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		String[] b1 = a1.split("\\s+");
		for(Edge ed:graph.edges()) {
			if(ed.getlabel().equals(b1[0])) {
				ed.setlabel(b1[1]);
			}
		}
//		log.info("修改边"+b1[0]+"为"+b1[1]);
		System.out.println("修改成功！");
	}
	
	/**
	 * 改变边的权值
	 * @param graph
	 */
	public void chaneweight(Graph<Vertex, Edge> graph) {
		System.out.println("请输入边的名字和新的权值，用空格隔开：如(label 1)");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		String[] b1 = a1.split("\\s+");
		for(Edge ed:graph.edges()) {
			if(ed.getlabel().equals(b1[0])) {
				ed.setweight(Double.parseDouble(b1[1]));
			}
		}
		System.out.println("修改成功！");
	}
	
	/**
	 * 改变边的方向
	 * @param graph
	 */
	public void changefor(Graph<Vertex, Edge> graph) {
		System.out.println("请输入边的名字:如(label)");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		for(Edge ed:graph.edges()) {
			if(ed.getlabel().equals(a1)) {
				ed.changefor();
			}
		}
		System.out.println("修改成功！");
	}
	
	/**
	 * 计算几种中心度
	 * @param graph
	 */
	public void calcucent(Graph<Vertex, Edge> graph) {
		System.out.println("请输入点的名称:如(Word1)");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		double a=0;
		double b=0;
		double c=0;
		for(Vertex ve:graph.vertices()) {
			if(ve.getLabel().equals(a1)) {
				new Centrality();
			    a=Centrality.calCentrality(new degreeStrategy(), graph, ve);
			    b=Centrality.calCentrality(new closenessStrategy(), graph, ve);
				c=Centrality.calCentrality(new betweennessStrategy(), graph, ve);
			}
		}
		System.out.println("degreeCentrality:"+a);
		System.out.println("closenessCentrality:"+b);
		System.out.println("betweennessCentrality:"+c);
	}
	
	/**
	 * 计算其他的几个值
	 * @param graph
	 */
	public void calcucrd(Graph<Vertex, Edge> graph) {
		new GraphMetrics();
		double a = GraphMetrics.degreeCentrality(graph);
		new GraphMetrics();
		double b = GraphMetrics.radius(graph);
		new GraphMetrics();
		double c = GraphMetrics.diameter(graph);
		System.out.println("degreeCentrality:"+a);
		System.out.println("radius:"+b);
		System.out.println("diameter:"+c);
	}
	
	/**
	 * 计算两个点的距离
	 * @param graph
	 */
	public void distance(Graph<Vertex, Edge> graph) {
		System.out.println("请输入两个点，以空格隔开，如(Word1 Word2)");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		String[] b1 = a1.split("\\s+");
		Vertex v1 = null;
		Vertex v2 = null;
		for(Vertex ve:graph.vertices()) {
			if(ve.getLabel().equals(b1[0])) {
				v1=ve;
			}
			if(ve.getLabel().equals(b1[1])) {
				v2=ve;
			}
		}
		new GraphMetrics();
		double a = GraphMetrics.distance(graph, v1, v2);
		System.out.println("distance:"+a);
	}
	
	/**
	 * 改变点的名字
	 * @param graph
	 * @return
	 */
	public boolean changevert(Graph<Vertex, Edge> graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger4.txt");
	        fileHandler.setLevel(Level.INFO); 
	        fileHandler.setFormatter(new MyLogHander());
	        log.addHandler(fileHandler); 
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		System.out.println("请输入原来点的名称和新的点的信息，以空格分开,第一个是原来点的名称，新的信息在后面，如(label1 label2)");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		String[] b1 = a1.split("\\s+");
		for(Vertex ve:graph.vertices()) {
			if(ve.getLabel().equals(b1[0])) {
				ve.changelabel(b1[1]);
			}
		}
		log.info("修改点"+b1[0]+"为"+b1[1]);
		return true;
	}
	
	/**
	 * 按照时间查询日志
	 * @param filename
	 * @throws IOException
	 */
	public void lookfortime(String filename) throws IOException {
		System.out.println("请输入查询的时间：格式（2018-12-12-00:00:00）");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		BufferedReader bf = new BufferedReader(new FileReader(filename));
		String line = new String();
		while((line=bf.readLine())!=null) {
			String[] str = line.split("\\s+");
			if(str[0].equals(a1)) {
				System.out.println(line);
			}
		}
		bf.close();
	}
	
	/**
	 * 按照方法类名查询日志
	 * @param filename
	 * @throws IOException
	 */
	public void lookformethod(String filename) throws IOException {
		System.out.println("请输入查询的方法类名：格式（factory.GraphPoetFactory.createGraph）");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		BufferedReader bf = new BufferedReader(new FileReader(filename));
		String line = new String();
		while((line=bf.readLine())!=null) {
			String[] str = line.split("\\s+");
			if(str[2].equals(a1)) {
				System.out.println(line);
			}
		}
		bf.close();
	}
	
	/**
	 * 将读入的图数据存储在result文件夹的MovieGraphResult.txt中
	 * @param graph
	 */
	public void writeback(Graph<Vertex, Edge> graph,OutmethodStrategy out) {
		List<String> li = new ArrayList<>();
		li.add("GraphType = \"MovieGraph\"" + "\n");
		li.add("VertexType = \"Movie\", \"Actor\", \"Director\"" + "\n");
		for (Vertex v : graph.vertices()) {
			if (v instanceof Movie) {
				Movie ve = (Movie) v;
				String la = ve.getLabel();
				li.add("Vertex = <\"" + la + "\", \"Movie\", <\"" + ve.getyear() + "\", \"" + ve.getcountry()
						+ "\", \"" + ve.getscore() + "\">>" + "\n");
			} else if (v instanceof Director) {
				Director ve = (Director) v;
				String la = ve.getLabel();
				li.add("Vertex = <\"" + la + "\", \"Director\", <\"" + ve.getage() + "\", \"" + ve.getgender()
						+ "\">>" + "\n");
			} else if (v instanceof Actor) {
				Actor ve = (Actor) v;
				String la = ve.getLabel();
				li.add("Vertex = <\"" + la + "\", \"Actor\", <\"" + ve.getage() + "\", \"" + ve.getgender()
						+ "\">>" + "\n");
			}
		}
		li.add("EdgeType = \"MovieActorRelation\", \"MovieDirectorRelation\",\"SameMovieHyperEdge\"" + "\n");
		for (Edge e : graph.edges()) {
			if (e instanceof MovieDirectorRelation) {
				MovieDirectorRelation ed = (MovieDirectorRelation) e;
				String la = ed.getlabel();
				li.add("Edge = <\"" + la + "\", \"MovieDirectorRelation\", \"" + ed.getweight() + "\",  \""
						+ ed.vertices().get(0).getLabel() + "\",\"" + ed.vertices().get(1).getLabel() + "\", \"No\">"
						+ "\n");
			} else if (e instanceof MovieActorRelation) {
				MovieActorRelation ed = (MovieActorRelation) e;
				String la = ed.getlabel();
				li.add("Edge = <\"" + la + "\", \"MovieActorRelation\", \"" + ed.getweight() + "\",  \""
						+ ed.vertices().get(0).getLabel() + "\",\"" + ed.vertices().get(1).getLabel() + "\", \"No\">"
						+ "\n");
			} else if (e instanceof SameMovieHyperEdge) {
				SameMovieHyperEdge ed = (SameMovieHyperEdge) e;
				String la = ed.getlabel();
				li.add("HyperEdge = <\"" + la + "\",\"SameMovieHyperEdge\",{\"");
				List<Vertex> li1 = ed.vertices();
				for (int i = 0; i < li1.size() - 1; i++) {
					li.add(li1.get(i).getLabel() + "\", \"");
				}
				li.add(li1.get(li1.size() - 1).getLabel() + "\"}>" + "\n");
			}
		}

		File file1 = new File("src/result/MovieGraphResult.txt");
		file1.delete();
		try {
			file1.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		long start = System.currentTimeMillis();
		out.output("src/result/MovieGraphResult.txt", li);
		long end = System.currentTimeMillis();
		System.out.println("写回时间:" + (end - start)+"ms");
	}
	
	/*
	 * 在此应用中，用户根据提示，输入每条指令对应的数字，执行相应的操作，建议首先输入1构建出一张图出来，
	 * 然后再根据对应的情况，按照提示的信息输入相应规格的数据，注意一定要按照提示所给的顺序输入。
	 */
	public static void main(String[] args) throws IOException {
		int flag = 1;
		MovieGraphApp app = new MovieGraphApp();
		int c = 0;
		while(flag==1) {
		    Scanner scan = new Scanner(System.in);
			System.out.println("请输入命令：");
			System.out.println("(1:构建图)(2:添加点)(3:删除点)(4:修改点的信息)(5:删除边)(6:添加边)");
			System.out.println("(7:修改边的label)(8:修改权值)(9:改变方向)(10:计算中心度)");
			System.out.print("(11:计算 centrality radius和 diameter)");
			System.out.println("(12:计算两个点的距离)(13:添加超边)");
			System.out.println("(14:按时间查询日志)(15:按名称查询日志)(16:写回图数据到文件)");
			System.out.println("(17:退出)");
			c = scan.nextInt();
			if(c==1) {
				Graph<Vertex, Edge> gg = app.creatgraph("src/Lab5_txt/file2.txt",new InputStream());
				while(gg==null) {
					System.out.println("请选择(输入)一个其他的文本文件(的路径):");
					Scanner scan1 = new Scanner(System.in);
					String s = scan1.nextLine();
					scan1.close();
					gg = app.creatgraph(s,new InputStream());
				}
				System.out.println("图构建成功！");
				List<String> li = new ArrayList<>();
				for(Vertex v : app.graph.vertices()) {
					li.add(v.toString());
					li.add("\n");
				}
				for(Edge e :app.graph.edges()) {
					li.add(e.toString());
					li.add("\n");
				}
				System.out.println(li);
			}
			else if(c==2) {
				app.addvert(app.graph);
//				System.out.println(app.graph.toString());
			}
			else if(c==3) {
				app.removevert(app.graph);
//				System.out.println(app.graph.toString());
			}
			else if(c==4) {
				app.changevert(app.graph);
//				System.out.println(app.graph.toString());
			}
			else if(c==5) {
				app.removeedge(app.graph);
//				System.out.println(app.graph.toString());
			}
			else if(c==6) {
				app.addedge(app.graph);
//				System.out.println(app.graph.toString());
			}
			else if(c==7) {
				app.changeedge(app.graph);
//				System.out.println(app.graph.toString());
			}
			else if(c==8) {
				app.chaneweight(app.graph);
//				System.out.println(app.graph.toString());
			}
			else if(c==9) {
				app.changefor(app.graph);
//				System.out.println(app.graph.toString());
			}
			else if(c==10) {
				app.calcucent(app.graph);
			}
			else if(c==11) {
				app.calcucrd(app.graph);
			}
			else if(c==12) {
				app.distance(app.graph);
			}
			else if(c==13) {
				app.addsame(app.graph);
//				System.out.println(app.graph.toString());
			}
			else if(c==14){
				app.lookfortime("src/logger/logger4.txt");
			}
			else if(c==15) {
				app.lookformethod("src/logger/logger4.txt");
			}else if(c==16) {
				app.writeback(app.graph,new BufferedoutputStream());
			}else if(c==17) {
				flag=0;
			}
		}
	}
}
