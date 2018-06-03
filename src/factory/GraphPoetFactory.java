package factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MyException.Direandundire;
import MyException.Edgenumexception;
import MyException.Erroredgeexception;
import MyException.Rulesexception;
import MyException.Samelabelexception;
import MyException.Weightrong;
import MyException.Vertexception;
import edge.Edge;
import edge.WordNeighborhood;
import graph.Graph;
import graph.GraphPoet;
import vertex.Vertex;
import factory.WordVertexFactory;

public class GraphPoetFactory extends GraphFactory{

	private static String name = SocialNetworkFactory.class.getName();      
	private static Logger log = Logger.getLogger(name);
	@Override
	public Graph<Vertex, Edge> createGraph(String filePath){
		FileReader file;
		int count=0;
		Graph<Vertex, Edge> graph = new GraphPoet();
		int ff=0;
		try {
			//�����־
			File file1 = new File("src/logger/logger1.txt");
			file1.delete();
			file1.createNewFile();
			FileHandler fileHandler = new FileHandler("src/logger/logger1.txt"); 
            fileHandler.setLevel(Level.INFO); 
            fileHandler.setFormatter(new MyLogHander());
            log.addHandler(fileHandler); 
//			
			file = new FileReader(filePath);
			BufferedReader bf = new BufferedReader(file);
			String line = null;
			List<String> listname = new ArrayList<>();
			Pattern ppp=Pattern.compile("-[0-9]\\d*\\.*\\d*");
			Pattern pp=Pattern.compile("\\w+");
			Pattern p0=Pattern.compile("Vertex\\s*=.+");
			Pattern p00=Pattern.compile("Edge\\s*=.+");
			Pattern p000=Pattern.compile("HyperEdge\\s*=.+");
			Pattern p1=Pattern.compile("GraphName\\s*=.+");
			Pattern p2=Pattern.compile("Vertex\\s*=\\s*<\"\\w+\",\\s*\"Word\">");
			Pattern p3=
			Pattern.compile("Edge\\s*=\\s*<\"\\w+\",\\s*\"WordNeighborhood\",\\s*\"[0-9]\\d*\\.*\\d*\",\\s*\"\\w+\",\\s*\"\\w+\",\\s*\"Yes\">");
//			GraphPoet graph = new GraphPoet();
			while((line=bf.readLine())!=null) {
				if(line.isEmpty()) {
					continue;
				}
				if(p000.matcher(line).matches()) {
					log.info("Warning����ͼ�г����˳��ߣ�������������");
					continue;
				}
				Pattern p=Pattern.compile("\"(.*?)\"");
				if(p1.matcher(line).matches()) {
					List<String> li2 = new ArrayList<>();
					Matcher m2=p.matcher(line);
					while(m2.find()) {
						String b = m2.group(1);
						li2.add(b);
					}
					if(!pp.matcher(li2.get(0)).matches()) {
						handlerules();
					}
				}
				if(p0.matcher(line).matches()) {
					List<String> li1 = new ArrayList<>();
					Matcher m1=p.matcher(line);
					while(m1.find()) {
						String b = m1.group(1);
						li1.add(b);
					}
					Matcher m2=p2.matcher(line);
					if(!m2.matches()) {
						if(li1.size()!=2) {
							handlevertnum();
						}else if(!li1.get(1).equals("Word")) {
							handlevert();
						}else if(!pp.matcher(li1.get(0)).matches()){
							handlerules();
						}
					}else {
						if(listname.contains(li1.get(0))) {
							handlesamela();
						}
						List<String> li = new ArrayList<>();
						Matcher m=p.matcher(line);
						while(m.find()) {
							String b = m.group(1);
							li.add(b);
						}
						String[] ar =null;
						listname.add(li.get(0));
						Vertex per =new WordVertexFactory().createVertex(li.get(0),"Word",ar);
						log.info("��ӵ�"+li.get(0));
						graph.addVertex(per);
					}
				}
				if(p00.matcher(line).matches()) {
					Matcher m=p.matcher(line);
					List<String> lis = new ArrayList<>();
					while(m.find()) {
						String b = m.group(1);
						lis.add(b);
					}
					Matcher m3=p3.matcher(line);
					if(!m3.matches()) {
						if(lis.size()!=6) {
							handleedgenum();
						}else if(!lis.get(lis.size()-1).equals("Yes") && !lis.get(lis.size()-1).equals("No")) {
							handlelastny();
						}else if(!lis.get(1).equals("WordNeighborhood")) {
							handerroredge();
						}else if(lis.get(lis.size()-1).equals("No")) {
							handledireandundire();
						}else if(!pp.matcher(lis.get(0)).matches()) {
							handlerules();
						}else if(ppp.matcher(lis.get(2)).matches()) {
							handleweight();
						}
					}else {
						if(!listname.contains(lis.get(3))||!listname.contains(lis.get(4))) {
							handleednovert();
						}
						if(!graph.edges().isEmpty()) {
							int flag=0,ii=0;
							for(Edge ed:graph.edges()) {
								if(ed.getlabel().equals(lis.get(0))) {
									count++;
									ii++;
								}
								if(ed.vertices().get(0).getLabel().equals(lis.get(3))
										&&ed.vertices().get(1).getLabel().equals(lis.get(4))) {
									flag++;
								}
							}
							if(flag==1) {
								log.info("����ͼ�����˶��صıߣ��Ժ��Ժ���ı�");
//								handlemuch();
							}
							if(flag!=1&&ii==0) {
								Edge wordn = new WordNeighborhood(lis.get(0), Double.parseDouble(lis.get(2)));
								List<Vertex> list = new ArrayList<>();
								for(int j=3;j<5;j++) {
									for(Vertex ver : graph.vertices()) {
										if(ver.getLabel().equals(lis.get(j))) {
											list.add(ver);
										}
									}
								}
								wordn.addVertices(list);
								log.info("��ӱ�"+lis.get(0));
								graph.addEdge(wordn);
							}else if(ii!=0) {
								log.info("Warning�����г������ظ���label������������������ÿһ��label���涼��һ");
								Edge wordn = new WordNeighborhood(lis.get(0)+count, Double.parseDouble(lis.get(2)));
								List<Vertex> list = new ArrayList<>();
								for(int j=3;j<5;j++) {
									for(Vertex ver : graph.vertices()) {
										if(ver.getLabel().equals(lis.get(j))) {
											list.add(ver);
										}
									}
								}
								wordn.addVertices(list);
								log.info("��ӱ�"+lis.get(0));
								graph.addEdge(wordn);
							}
						}else {
							Edge wordn = new WordNeighborhood(lis.get(0), Double.parseDouble(lis.get(2)));
							List<Vertex> list = new ArrayList<>();
							for(int j=3;j<5;j++) {
								for(Vertex ver : graph.vertices()) {
									if(ver.getLabel().equals(lis.get(j))) {
										list.add(ver);
									}
								}
							}
							wordn.addVertices(list);
							log.info("��ӱ�"+lis.get(0));
							graph.addEdge(wordn);
						}
					}
				}
			}
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Vertexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedvert();
//			graph=g;
			ff=1;
		} catch (Edgenumexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g = e.fixededgenum();
//			graph=g;
			ff=1;
		} catch (Erroredgeexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedit();
//			graph=g;
			ff=1;
		} catch (Direandundire e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g = e.fixeddirandund();
//			graph=g;
			ff=1;
		} catch (Rulesexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedrules();
//			graph=g;
			ff=1;
		} catch (Weightrong e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedweight();
//			graph=g;
			ff=1;
		} catch (Samelabelexception e) {
			System.out.println(e.getMessage());
//			Graph<Vertex, Edge> g=e.fixedit();
//			graph=g;
			ff=1;
		} 

		if(ff==1) {
			return null;
		}
		return graph;
	}
	
	/**
	 * ��������ĺ���
	 * @param line
	 * @throws Vertexception
	 */
	public void handlevert() throws Vertexception{
//		log.info("Wrong:the Vertex type is not \"Word\"");
		throw new Vertexception("Wrong:the Vertex type is not \"Word\"");
	}
	
	public void handlevertnum() throws Vertexception{
//		log.info("Wrong:the vertex loss some messege");
		throw new Vertexception("Wrong:there loss some messege");
	}
	/**
	 * �������ȱ����Ϣ�����
	 * @throws Edgenumexception
	 */
	public void handleedgenum() throws Edgenumexception{
//		log.info("Wrong:the edge loss messege");
		throw new Edgenumexception("Wrong:there loss some messege");
	}
	
	/**
	 * ������е�һЩ�ַ������Ϲ�������
	 * @throws Edgenumexception
	 */
	public void handlelastny() throws Edgenumexception{
//		log.info("Wrong:the last messege is not \"Yes\"");
		throw new Edgenumexception("Wrong:the last messege is not \"Yes\"");
	}
	
	/**
	 * �������û�д����ĵ�����
	 */
	public void handleednovert() throws Edgenumexception{
//		log.info("Wrong:there graph has no such vertexs");
		throw new Edgenumexception("Wrong:there graph has no such vertexs");
	}
	
	/**
	 * ����ߵ����������������͵����
	 * @throws Erroredgeexception
	 */
	public void handerroredge() throws Erroredgeexception{
//		log.info("Wrong:the edge type is not WordNeighborhood");
		throw new Erroredgeexception("Wrong:the edge type is not WordNeighborhood");
	}
	
	/**
	 * ��������ͼ�е�����ߵ����
	 * @throws Direandundire
	 */
	public void handledireandundire() throws Direandundire{
//		log.info("��������ͼ�������������");
		throw new Direandundire("��������ͼ�������������");
	}
	
	/**
	 * ����label������������ʽ�����
	 * @throws Rulesexception
	 */
	public void handlerules() throws Rulesexception{
//		log.info("label������������ʽҪ��");
		throw new Rulesexception("label������������ʽҪ��");
	}
	
	public void handleweight() throws Weightrong{
//		log.info("ȨֵΪ���ˣ�����������");
		throw new Weightrong("ȨֵΪ���ˣ�����������");
	}
	
	public void handlesamela() throws Samelabelexception{
//		log.info("Wrong:���label�ظ�");
		throw new Samelabelexception("Wrong:���label�ظ�");
	}
	
//	public void handlemuch() throws Lesstomuchexception{
//		log.info("����ͼ�����˶��صıߣ��Ժ��Ժ���ı�");
//		throw new Lesstomuchexception("����ͼ�����˶��صıߣ��Ժ��Ժ���ı�");
//	}
//	public static void main(String[] args) throws IOException {
//		GraphFactory g = new GraphPoetFactory();
//		Graph<Vertex, Edge> gra = g.createGraph("src/txt/gp12.txt");
////		System.out.println(gra.toString());
//	}
}
