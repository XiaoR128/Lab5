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
	 * ����һ��ͼ
	 */
	public Graph<Vertex, Edge> creatgraph(String file,InmethodStrategy in){
		this.graph = new MovieGraphFactory().createGraph(file,in);
		return graph;
	}
	
	/**
	 * ���ݹ��������Ӧ�ĵ�
	 */
	public boolean addvert(Graph<Vertex, Edge>graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger4.txt");
	        fileHandler.setLevel(Level.INFO); 
	        fileHandler.setFormatter(new MyLogHander());
	        log.addHandler(fileHandler); 
		} catch (SecurityException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
		System.out.println("�������������Ϣ���Կո�ֿ�:��(FrankDarabont Director 59 M)");
		System.out.println("��ӵ�Ӱ�Ļ�����������������(Spiderman Movie 2010 USA 9.3)");
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
//		log.info("������"+b[0]);
		System.out.println("������"+b[0]+"�ɹ���");
		return true;
	}
	
	/**
	 * ���ݸù���ɾ����Ӧ�ĵ�
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
		System.out.println("������������:����(Vera)");
		Scanner sc1 = new Scanner(System.in);
		String a1 = sc1.nextLine();
		String[] b1 = a1.split("\\s+");
		for(Vertex v:graph.vertices()) {
			if(v.getLabel().equals(b1[0])) {
				graph.removeVertex(v);
			}
		}
//		log.info("ɾ����"+b1[0]);
		System.out.println("ɾ����ɹ���");
		return true;
	}
	
	/*
	 * ���һ���ߣ�ע����ӵ�ʱ�����ԭ�����ڵ�
	 * @param һ��ͼ
	 */
	public boolean addedge(Graph<Vertex, Edge> graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger4.txt");
	        fileHandler.setLevel(Level.INFO); 
	        fileHandler.setFormatter(new MyLogHander());
	        log.addHandler(fileHandler); 
		} catch (SecurityException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
		System.out.println("������ߵ���Ϣ���Կո�ֿ�");
		System.out.println("���磺label1 MovieDirectorRelation 1 name1 name2 No");
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
//		log.info("��ӱ�"+b1[0]);
		System.out.println("��ӳɹ���");
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
		System.out.println("������ߵ���Ϣ���Կո�ֿ�");
		System.out.println("���磺label SameMovieHyperEdge -1 TimRobbins MorganFreeman");
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
//		log.info("��ӱ�"+b1[0]);
		graph.addEdge(ed);
	}
	
	/**
	 * ɾ��ͼ�е�ĳһ����
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
		System.out.println("������ߵ����֣�����(W1)");
		Scanner sc = new Scanner(System.in);
		String a = sc.nextLine();
		int flag = 1;
		for(Edge ed:graph.edges()) {
			if(ed.getlabel().equals(a)) {
				flag=0;
				graph.removeEdge(ed);
//				log.info("ɾ����"+a);
				System.out.println("ɾ���ɹ���");
			}
		}
		if(flag==1) {
			System.out.println("ɾ��ʧ�ܣ�");
			return false;
		}
		return true;
	}
	
	/**
	 * �ı�ߵ�����
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
		System.out.println("������ߵ����ƺ��µ�����,�ɵ���ǰ�棬�µ��ں�ߣ��ÿո����:��(W1 W2)");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		String[] b1 = a1.split("\\s+");
		for(Edge ed:graph.edges()) {
			if(ed.getlabel().equals(b1[0])) {
				ed.setlabel(b1[1]);
			}
		}
//		log.info("�޸ı�"+b1[0]+"Ϊ"+b1[1]);
		System.out.println("�޸ĳɹ���");
	}
	
	/**
	 * �ı�ߵ�Ȩֵ
	 * @param graph
	 */
	public void chaneweight(Graph<Vertex, Edge> graph) {
		System.out.println("������ߵ����ֺ��µ�Ȩֵ���ÿո��������(label 1)");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		String[] b1 = a1.split("\\s+");
		for(Edge ed:graph.edges()) {
			if(ed.getlabel().equals(b1[0])) {
				ed.setweight(Double.parseDouble(b1[1]));
			}
		}
		System.out.println("�޸ĳɹ���");
	}
	
	/**
	 * �ı�ߵķ���
	 * @param graph
	 */
	public void changefor(Graph<Vertex, Edge> graph) {
		System.out.println("������ߵ�����:��(label)");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		for(Edge ed:graph.edges()) {
			if(ed.getlabel().equals(a1)) {
				ed.changefor();
			}
		}
		System.out.println("�޸ĳɹ���");
	}
	
	/**
	 * ���㼸�����Ķ�
	 * @param graph
	 */
	public void calcucent(Graph<Vertex, Edge> graph) {
		System.out.println("������������:��(Word1)");
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
	 * ���������ļ���ֵ
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
	 * ����������ľ���
	 * @param graph
	 */
	public void distance(Graph<Vertex, Edge> graph) {
		System.out.println("�����������㣬�Կո��������(Word1 Word2)");
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
	 * �ı�������
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
		System.out.println("������ԭ��������ƺ��µĵ����Ϣ���Կո�ֿ�,��һ����ԭ��������ƣ��µ���Ϣ�ں��棬��(label1 label2)");
		Scanner sc = new Scanner(System.in);
		String a1 = sc.nextLine();
		String[] b1 = a1.split("\\s+");
		for(Vertex ve:graph.vertices()) {
			if(ve.getLabel().equals(b1[0])) {
				ve.changelabel(b1[1]);
			}
		}
		log.info("�޸ĵ�"+b1[0]+"Ϊ"+b1[1]);
		return true;
	}
	
	/**
	 * ����ʱ���ѯ��־
	 * @param filename
	 * @throws IOException
	 */
	public void lookfortime(String filename) throws IOException {
		System.out.println("�������ѯ��ʱ�䣺��ʽ��2018-12-12-00:00:00��");
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
	 * ���շ���������ѯ��־
	 * @param filename
	 * @throws IOException
	 */
	public void lookformethod(String filename) throws IOException {
		System.out.println("�������ѯ�ķ�����������ʽ��factory.GraphPoetFactory.createGraph��");
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
	 * �������ͼ���ݴ洢��result�ļ��е�MovieGraphResult.txt��
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
		System.out.println("д��ʱ��:" + (end - start)+"ms");
	}
	
	/*
	 * �ڴ�Ӧ���У��û�������ʾ������ÿ��ָ���Ӧ�����֣�ִ����Ӧ�Ĳ�����������������1������һ��ͼ������
	 * Ȼ���ٸ��ݶ�Ӧ�������������ʾ����Ϣ������Ӧ�������ݣ�ע��һ��Ҫ������ʾ������˳�����롣
	 */
	public static void main(String[] args) throws IOException {
		int flag = 1;
		MovieGraphApp app = new MovieGraphApp();
		int c = 0;
		while(flag==1) {
		    Scanner scan = new Scanner(System.in);
			System.out.println("���������");
			System.out.println("(1:����ͼ)(2:��ӵ�)(3:ɾ����)(4:�޸ĵ����Ϣ)(5:ɾ����)(6:��ӱ�)");
			System.out.println("(7:�޸ıߵ�label)(8:�޸�Ȩֵ)(9:�ı䷽��)(10:�������Ķ�)");
			System.out.print("(11:���� centrality radius�� diameter)");
			System.out.println("(12:����������ľ���)(13:��ӳ���)");
			System.out.println("(14:��ʱ���ѯ��־)(15:�����Ʋ�ѯ��־)(16:д��ͼ���ݵ��ļ�)");
			System.out.println("(17:�˳�)");
			c = scan.nextInt();
			if(c==1) {
				Graph<Vertex, Edge> gg = app.creatgraph("src/Lab5_txt/file2.txt",new InputStream());
				while(gg==null) {
					System.out.println("��ѡ��(����)һ���������ı��ļ�(��·��):");
					Scanner scan1 = new Scanner(System.in);
					String s = scan1.nextLine();
					scan1.close();
					gg = app.creatgraph(s,new InputStream());
				}
				System.out.println("ͼ�����ɹ���");
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
