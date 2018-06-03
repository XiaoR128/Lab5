package application;

import graph.Graph;
import graph.GraphPoet;
import helper.Centrality;
import helper.GraphMetrics;
import helper.betweennessStrategy;
import helper.closenessStrategy;
import helper.degreeStrategy;
import vertex.Vertex;
import vertex.Word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import edge.Edge;
import factory.GraphPoetFactory;
import factory.MyLogHander;
import factory.SocialNetworkFactory;
import factory.WordNeighborhoodFactory;
import factory.WordVertexFactory;
 /**
 * ����һ�����������Ӧ��ģ�ͣ���Ҫ�û��ӿ���̨������Ӧ��ָ����ʵ�ֲ�ͬ�Ĺ���
 */
public class GraphPoetApp {
	private Graph<Vertex, Edge> graph = new GraphPoet();
	private static String name = SocialNetworkFactory.class.getName();      
	private static Logger log = Logger.getLogger(name);
	/**
	 * ����һ��ͼ
	 */
	public Graph<Vertex, Edge> creatgraph(String file){
		this.graph = new GraphPoetFactory().createGraph(file);
		return graph;
	}
	
	/**
	 * ���ݹ���������Ӧ�ĵ�
	 */
	public boolean addvert(Graph<Vertex, Edge>graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger1.txt");
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
		System.out.println("�������������Ϣ���Կո�ֿ���ǰ���ǵ�����֣������ǵ������:��(Word1 Word)");
		Scanner sc = new Scanner(System.in);
		String a = sc.nextLine();
		String[] b = a.split("\\s+");
		Vertex ve = new  WordVertexFactory().createVertex(b[0], b[1], null);
		graph.addVertex(ve);
		log.info("������"+b[0]);
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
			fileHandler = new FileHandler("src/logger/logger1.txt");
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
		System.out.println("������������:����(Word1)");
		Scanner sc1 = new Scanner(System.in);
		String a1 = sc1.nextLine();
		String[] b1 = a1.split("\\s+");
		for(Vertex v:graph.vertices()) {
			if(v.getLabel().equals(b1[0])) {
				graph.removeVertex(v);
			}
		}
		log.info("ɾ����"+b1[0]);
		System.out.println("ɾ����ɹ���");
//		sc1.close();
		return true;
	}
	
	/*
	 * ����һ���ߣ�ע�����ӵ�ʱ�����ԭ�����ڵ�
	 * @param һ��ͼ
	 */
	public boolean addedge(Graph<Vertex, Edge> graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger1.txt");
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
		System.out.println("���磺Label WordNeighborhood 1 Word1 Word2 Yes");
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
		Edge ed = new WordNeighborhoodFactory().createEdge(b1[0], b1[1], Double.parseDouble(b1[2]), vertice);
		graph.addEdge(ed);
		log.info("���ӱ�"+b1[0]);
		System.out.println("���ӳɹ���");
		return true;
	}
	
	/**
	 * ɾ��ͼ�е�ĳһ����
	 * @param graph
	 * @return
	 */
	public boolean removeedge(Graph<Vertex, Edge> graph) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/logger/logger1.txt");
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
				log.info("ɾ����"+a);
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
			fileHandler = new FileHandler("src/logger/logger1.txt");
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
		log.info("�޸ı�"+b1[0]+"Ϊ"+b1[1]);
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
			fileHandler = new FileHandler("src/logger/logger1.txt");
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
		System.out.println("������ԭ��������ƺ��µĵ����Ϣ���Կո�ֿ����ɵ���ǰ���µ��ں���(Word1 Word10)");
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
	 * �������ͼ���ݴ洢��result�ļ��е�GraphPoetResult.txt��
	 * @param graph
	 */
	public void writeback(Graph<Vertex, Edge> graph) {
		File docFile = new File("src/result/GraphPoetResult.txt");
		try {
 			File file2 = new File("src/result/GraphPoetResult.txt");
 			file2.delete();
 			file2.createNewFile();
			FileOutputStream txtfile = new FileOutputStream(docFile);
			PrintStream p = new PrintStream(txtfile);
			String line=new String();
			line = line+"GraphType = \"GraphPoet\""+"\n";
			line = line+"VertexType = \"Word\""+"\n";
			for(Vertex v : graph.vertices()) {
				String la = v.getLabel();
				line = line+"Vertex = <\""+la+"\", \"Word\">"+"\n";
			}
			line = line+"EdgeType = \"WordNeighborhood\""+"\n";
			for(Edge e:graph.edges()) {
				String la = e.getlabel();
				line = line+"Edge = <\""+la+"\", "+"\"WordNeighborhood\", \""+e.getweight()+"\", \""+
				e.vertices().get(0).getLabel()+"\",\""+e.vertices().get(1).getLabel()+"\", \"Yes\">"+"\n";
			}
			p.println(line);
			System.out.println("д�سɹ���");
			txtfile.close();
			p.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * �ڴ�Ӧ���У��û�������ʾ������ÿ��ָ���Ӧ�����֣�ִ����Ӧ�Ĳ�����������������1������һ��ͼ������
	 * Ȼ���ٸ��ݶ�Ӧ�������������ʾ����Ϣ������Ӧ�������ݣ�ע��һ��Ҫ������ʾ������˳�����롣
	 */
	public static void main(String[] args) throws IOException {
		int flag = 1;
		GraphPoetApp app = new GraphPoetApp();
		int c = 0;
		System.out.println("��ȷ��ͼ�бߵ�Ȩֵ���ܵ��ڵ�n��ֵ��n>1��:");
		Scanner scc = new Scanner(System.in);
		int n = scc.nextInt();
//		scc.close();
		while(flag==1) {
		    Scanner scan = new Scanner(System.in);
			System.out.println("���������");
			System.out.println("(1:����ͼ)(2:���ӵ�)(3:ɾ����)(4:�޸ĵ����Ϣ)(5:ɾ����)(6:���ӱ�)");
			System.out.println("(7:�޸ıߵ�label)(8:�޸�Ȩֵ)(9:�ı䷽��)(10:�������Ķ�)");
			System.out.print("(11:���� centrality radius�� diameter)");
			System.out.println("(12:����������ľ���)");
			System.out.println("(13:��ʱ���ѯ��־)(14:�����Ʋ�ѯ��־)(15:д���ݻص��ļ�)");
			System.out.println("(16:�˳�)");
			c = scan.nextInt();
			if(c==1) {
				Graph<Vertex, Edge> gg = app.creatgraph("src/txt/GraphPoet.txt");
				while(gg==null) {
					System.out.println("��ѡ��(����)һ���������ı��ļ�(��·��):");
					Scanner scan1 = new Scanner(System.in);
					String s = scan1.nextLine();
					scan1.close();
					gg = app.creatgraph(s);
				}
				System.out.println("ͼ�����ɹ���");
				GraphPoet g = (GraphPoet)app.graph;
				g.removeedgen(n);
				System.out.println(app.graph.toString());
			}
			else if(c==2) {
				app.addvert(app.graph);
				System.out.println(app.graph.toString());
			}
			else if(c==3) {
				app.removevert(app.graph);
				System.out.println(app.graph.toString());
			}
			else if(c==4) {
				app.changevert(app.graph);
				System.out.println(app.graph.toString());
			}
			else if(c==5) {
				app.removeedge(app.graph);
				System.out.println(app.graph.toString());
			}
			else if(c==6) {
				app.addedge(app.graph);
				System.out.println(app.graph.toString());
			}
			else if(c==7) {
				app.changeedge(app.graph);
				System.out.println(app.graph.toString());
			}
			else if(c==8) {
				app.chaneweight(app.graph);
				System.out.println(app.graph.toString());
			}
			else if(c==9) {
				app.changefor(app.graph);
				System.out.println(app.graph.toString());
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
			else if(c==13){
				app.lookfortime("src/logger/logger1.txt");
			}
			else if(c==14) {
				app.lookformethod("src/logger/logger1.txt");
			}else if(c==15) {
				app.writeback(app.graph);
			}else if(c==16) {
				flag=0;
			}
		}
	}
}